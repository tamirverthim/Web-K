package com.earnix.webk.script.web_idl.impl;

import com.earnix.webk.script.FunctionAdapter;
import com.earnix.webk.script.ReflectionHelper;
import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DefaultBoolean;
import com.earnix.webk.script.web_idl.DefaultDouble;
import com.earnix.webk.script.web_idl.DefaultLong;
import com.earnix.webk.script.web_idl.DefaultNull;
import com.earnix.webk.script.web_idl.DefaultString;
import com.earnix.webk.script.web_idl.Function;
import com.earnix.webk.script.web_idl.Indexed;
import com.earnix.webk.script.web_idl.Iterable;
import com.earnix.webk.script.web_idl.LegacyUnenumerableNamedProperties;
import com.earnix.webk.script.web_idl.NullTreat;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Sequence;
import com.earnix.webk.script.web_idl.TreatNullAs;
import com.earnix.webk.script.web_idl.Typedef;
import com.earnix.webk.script.web_idl.service.PropertyGetter;
import com.earnix.webk.script.web_idl.service.PropertySetter;
import com.earnix.webk.util.AssertHelper;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Stream;


/**
 * @author Taras Maslov
 * 5/31/2018
 */
@SuppressWarnings("unchecked")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebIDLAdapter<T> implements JSObject {

    private static WeakHashMap<Object, WebIDLAdapter> all = new WeakHashMap<>();

    /**
     * Indicates that named item was set to null during runtime
     */
    private static final Object NULL = new Object();
    private static final Object UNDEFINED = new Object();

    T target;
    ScriptContext js;
    HashMap<String, Object> members = new HashMap<>();

    Method propertyGetter;
    Method propertySetter;


    private WebIDLAdapter(ScriptContext js, T target) {
        this.target = target;
        this.js = js;
        processTarget();
    }

    public T getTarget() {
        return target;
    }

    public static <T> WebIDLAdapter<T> obtain(ScriptContext js, T target) {
        WebIDLAdapter result = all.get(target);
        if (result == null) {
            result = new WebIDLAdapter(js, target);
            all.put(target, result);
        }
        return result;
    }

    Object readonlyAttributeMark = new Object();

    @Getter
    @AllArgsConstructor
    class AttributeLink {
        Attribute attribute;
        Class attributeClass;
    }


    class MultiArgFunctionCallback implements Function<T> {
        private HashMap<Integer, Function> callbacks = new HashMap<>();

        public T call(Object ctx, Object... args) {
            // calling callback with closest but larger args count
            // todo thing about NPE
            val callback = get(args.length);
            if (callback != null) {
                return (T) callback.call(ctx, args);
            } else {
                log.error("No matching function to call with given args count");
                throw new RuntimeException();
            }
        }

        MultiArgFunctionCallback add(Function callback, int argsCount) {
            callbacks.put(argsCount, callback);
            return this;
        }

        @javax.annotation.Nullable
        Function get(Integer argsCount) {
            var callback = callbacks.get(argsCount);
            if (callback == null) {
                // finding firs with larger args count
                @SuppressWarnings("ConstantConditions")
                int approximateCount = callbacks.keySet().stream().filter(i -> i > argsCount).mapToInt(i -> i).min().getAsInt();
                callback = callbacks.get(approximateCount);
            }

            // only functions with less number of args...
            return callback;
        }
    }

    private void processTarget() {

        try {

            ClassUtils.getAllInterfaces(target.getClass())
                    .stream()
                    .flatMap(i -> Stream.of(i.getMethods()))
                    .forEach(m -> {


                        // Attribute member
                        try {
                            if (m.getReturnType().equals(Attribute.class)) {
                                members.put(m.getName(), new AttributeLink(
                                        (Attribute<?>) m.invoke(target),
                                        (Class<?>) ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments()[0])
                                );
                                return;
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (RuntimeException e) {
                            throw e; // todo rem
                        }


                        if (m.isAnnotationPresent(ReadonlyAttribute.class)) {
                            members.put(m.getName(), readonlyAttributeMark);
                            return;
                        }

                        // Function member

                        @SuppressWarnings("RedundantCast")
                        val callback = (Function) (ctx, args) -> {
                            final Object res;

                            try {
                                res = MethodUtils.invokeMethod(target, m.getName(), prepareArguments(m, args), m.getParameterTypes());
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException e) {
                                throw new RuntimeException(e);
                            }
                            return convertToScript(res);
                        };

                        if (m.getAnnotation(PropertyGetter.class) != null) {
                            AssertHelper.assertState(propertyGetter == null);
                            propertyGetter = m;
                        }


                        if (m.getAnnotation(PropertySetter.class) != null) {
                            AssertHelper.assertState(propertySetter == null);
                            propertySetter = m;
                        }
                        
//                        val function = new Function<>(js, 
//
//                        }, m.getName());
                        // multiple functions are supported for different number of arguments
                        val currentMember = members.get(m.getName());
                        if (currentMember != null) {
                            if (currentMember.getClass().equals(FunctionAdapter.class)) {
                                val function = (FunctionAdapter) currentMember;
                                if (function.getCallback() instanceof WebIDLAdapter.MultiArgFunctionCallback) {
                                    val multiArgCallback = (MultiArgFunctionCallback) function.getCallback();
                                    multiArgCallback.add(callback, m.getParameterCount());
                                } else {
                                    val multiArgCallback = new MultiArgFunctionCallback();
                                    multiArgCallback.add(function.getCallback(), function.getArgsCount()).add(callback, m.getParameterCount());
                                    function.setCallback(multiArgCallback);
                                    function.setArgsCount(null);
                                }
                                return;
                            } else {
                                log.error("Overriding member {} of {}", m.getName(), m.getDeclaringClass().getName());
                            }
                        }
                        members.put(m.getName(), new FunctionAdapter<>(js, callback, m.getParameterCount(), m.getName()));
                    });


            members.put("toString", new FunctionAdapter<>(js, (ctx, arg) -> WebIDLAdapter.this.toString() + " " + target.toString(), 0, "toString"));

            members.put("equals", new FunctionAdapter<>(js, (ctx, arg) ->
                    WebIDLAdapter.this.equals(arg[0]), 1, "equals"));

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        
        AssertHelper.assertState(propertyGetter != null ^ propertySetter == null, target.toString());
    }

    @Override
    public Object call(Object o, Object... objects) {
        // todo
        return null;
    }

    @Override
    public Object newObject(Object... objects) {
        return WebIDLAdapter.obtain(js, ReflectionHelper.create(target.getClass()));
    }

    @Override
    public Object eval(String s) {
        return js.eval(s);
    }

    @Override
    public Object getMember(String s) {
        log.trace("Getting member {} of {}", s, target);
        try {
            val member = members.get(s);
            Object namedItem = null;


            if (member instanceof WebIDLAdapter.AttributeLink) {
                return convertToScript((((AttributeLink) member).attribute).get());
            } else if (readonlyAttributeMark.equals(member)) {
                
                try {
                    return convertToScript(MethodUtils.invokeMethod(target, s));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                
            } else if (target instanceof LegacyUnenumerableNamedProperties) {
                namedItem = ((LegacyUnenumerableNamedProperties) target).namedItem(s);
            }

            // it is NULL if was overriden with null during runtime,
            // actually LegacyUnenumerableNamedProperties are not set-able, so this should be used for similar cases
            if (namedItem == NULL || namedItem != null) {
                return convertToScript(namedItem);
            }
//
            if (member == null && propertyGetter != null) {
                return convertToScript(ReflectionHelper.relaxedInvoke(target, propertyGetter, s));
            }

            // function or custom value set by js runtime
            return member;

        } catch (RuntimeException e) {
            log.error("Error getting member {} of {}", s, target, e);
            return null;
        }
    }

    @Override
    public Object getSlot(int i) {
        if (target instanceof Indexed) {
            return convertToScript(((Indexed) target).elementAtIndex(i));
        } else if (target instanceof LegacyUnenumerableNamedProperties) {
            return convertToScript(((LegacyUnenumerableNamedProperties) target).item(i));
        } else if (target instanceof Iterable) {
            return convertToScript(((Iterable) target).item(i));
        } else {
            return null;
        }
    }

    @Override
    public boolean hasMember(String s) {
        return members.containsKey(s);
    }

    @Override
    public boolean hasSlot(int i) {

        return isIterable() && iterableLength() >= i;
    }

    private boolean isIterable() {
        return target instanceof Iterable;
    }

    private int iterableLength() {
        return ((Iterable) target).length();
    }

    @Override
    public void removeMember(String s) {
        members.remove(s);
    }

    @Override
    public void setMember(String s, Object o) {
        val member = members.get(s);
        if (member instanceof WebIDLAdapter.AttributeLink) {
            try {
                val att = ((AttributeLink) member).attribute;
                val unwrapped = toJavaPresentation(o);
                val adapted = toJavaPresentation(unwrapped, ((AttributeLink) member).attributeClass);

                att.set(adapted);
            } catch (Exception e) {
                log.error("setMember Attribute {}", s, e);
//                members.remove(s);
//                setMember(s, o);
            }

        } else if (target instanceof LegacyUnenumerableNamedProperties) {
            members.put(s, o);
        } else if (propertySetter != null) {
            try {

                ReflectionHelper.relaxedInvoke(target, propertySetter, s, o);
            } catch (IllegalArgumentException e) {
                members.put(s, o);
            }
        } else {
            members.put(s, o);
        }
        
    }

    @Override
    public void setSlot(int i, Object o) {
        log.warn("setSlot");
    }

    @Override
    public Set<String> keySet() {
        return members.keySet();
    }

    @Override
    public Collection<Object> values() {
        return members.values();
    }

    @Override
    public boolean isInstance(Object o) {
        if (o instanceof WebIDLAdapter) {
            val adapter = (WebIDLAdapter) o;
            return target.getClass().isAssignableFrom(adapter.target.getClass());
        } else {
            return false;
        }
    }

    @Override
    public boolean isInstanceOf(Object o) {
        // todo check diff from prev
        if (o instanceof WebIDLAdapter) {
            val adapter = (WebIDLAdapter) o;
            return target.getClass().isAssignableFrom(adapter.target.getClass());
        } else {
            return false;
        }
    }

    @Override
    public String getClassName() {
        return target.getClass().getSimpleName();
    }

    @Override
    public boolean isFunction() {
        return false;
    }

    @Override
    public boolean isStrictFunction() {
        return false;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public double toNumber() {
        return 0;
    }


    public Object convertToScript(Object source) {

        if (source instanceof JSObject) {
            return source;
        }


        if (source == null || ClassUtils.isPrimitiveOrWrapper(source.getClass())) {
            return source;
        }

//        if (res instanceof DOMString || res instanceof USVString) {
//            return res.toString();
//        }

        if (source.getClass().getPackage().getName().startsWith("com.earnix.webk.script")) {
            return WebIDLAdapter.obtain(js, source);
        } else {
            return source;
        }
    }

    private Object[] prepareArguments(Method method, Object[] rawArgs) {
        Object[] result = new Object[method.getParameterTypes().length];
        if (log.isDebugEnabled()) {
            if (result.length < rawArgs.length && !method.isVarArgs()) {
                log.debug("Too many params from JS call to {}", method.toString());
            }
        }

        int varargFromIndex = -1;

        for (int i = 0; i < result.length; i++) {
            Object arg;
            Object rawArg;
            val parameter = method.getParameters()[i];
            val parameterType = method.getParameterTypes()[i];
            val genericParameterType = method.getGenericParameterTypes()[i];
            val genericType = (Class) (genericParameterType instanceof ParameterizedType ? ((ParameterizedType) genericParameterType).getActualTypeArguments()[0] : null);

            if (i == method.getParameterCount() - 1 && method.isVarArgs()) {
                // all next args are targeted to vararg parameter
                varargFromIndex = i;
                break;
            }

            if (rawArgs.length < i + 1) {
                // absent parameter

                if (!hasAnnotation(parameter, com.earnix.webk.script.web_idl.Optional.class)) {
                    log.warn("Absent required argument {} for method {}", parameter, method);
                }

                arg = getDefaultValue(parameter).orElse(null);

            } else {
                rawArg = rawArgs[i];

                if (rawArg != null) {

                    arg = toJavaPresentation(rawArg, parameterType, genericType);

                } else {
                    // null parameter

                    if (hasAnnotation(parameter, TreatNullAs.class) && parameter.getAnnotation(TreatNullAs.class).value() == NullTreat.EmptyString) {
                        arg = "";
                    } else {
                        if (!hasAnnotation(parameter, Nullable.class) && hasAnnotation(parameter, com.earnix.webk.script.web_idl.Optional.class)) {
                            val optionalDefaultValue = getDefaultValue(parameter);
                            if (optionalDefaultValue.isPresent()) {
                                arg = optionalDefaultValue.get();
                            } else {
                                arg = null;
                                if (!isDefaultNull(parameter)) {
                                    log.warn("Non-nullable non-optional parameter {} of method {} received null", parameter, method);
                                }
                            }
                        } else if (!hasAnnotation(parameter, Nullable.class)) {
                            log.warn("Absent required parameter {} for method {}", parameter, method);
                            arg = null;
                        } else {
                            arg = null;
                        }
                    }
                }
            }

            result[i] = toJavaPresentation(arg, parameterType);

        }

        if (varargFromIndex > -1) {
            List<Object> varArgs = new ArrayList<>();
            val varArgsArrayType = method.getParameterTypes()[method.getParameterCount() - 1];
            val varArgsElementType = varArgsArrayType.getComponentType();
            for (int i = varargFromIndex; i < rawArgs.length; i++) {
                varArgs.add(toJavaPresentation(rawArgs[i], varArgsElementType));
            }
            result[varargFromIndex] = varArgs.toArray((Object[]) Array.newInstance(varArgsElementType, rawArgs.length - varargFromIndex));
        }

        return result;
    }

//    Object convertToJava(Class parameterType, Object rawArg) {
//
//        Object arg;
//
//        // auto DOMString support
//
////        if (parameterType.equals(DOMString.class)) {
////            if (rawArg instanceof String) {
////                arg = DOMStringImpl.of((String) rawArg);
////            } else {
////                arg = rawArg.toString();
////            }
////        } else if (parameterType.equals(USVString.class)) {
////            if (rawArg instanceof String) {
////                arg = USVStringImpl.of((String) rawArg);
////            } else {
////                arg = rawArg.toString();
////            }
////        } else 
//            
//        if (parameterType.equals(Sequence.class) && rawArg instanceof NativeArray) {
//            val sequenceComponentType = getTypeOfInterfaceGeneric(parameterType);
//            val array = (NativeArray) rawArg;
//            val objectsArray = array.asObjectArray();
//            arg = new SequenceImpl<>(Stream.of(objectsArray).map(obj -> toJavaPresentation(obj, (Class)sequenceComponentType)).collect(Collectors.toList()));
//        } else if (parameterType.equals(Sequence.class) && rawArg instanceof ScriptObjectMirror) {
//            val sequenceComponentType = getTypeOfInterfaceGeneric(parameterType);
//            val mirror = (ScriptObjectMirror) rawArg;
//            
//            val objects = new ArrayList<>();
//            for (int i = 0; i < mirror.size(); i++) {
//                objects.add(toJavaPresentation(mirror.getSlot(i), sequenceComponentType));
//            }
//            
//            arg = new SequenceImpl<>(Stream.of(objects).map(obj -> toJavaPresentation(obj, sequenceComponentType), sequenceComponentType)).collect(Collectors.toList()));
//        } else {
//            arg = rawArg;
//        }
//
//        // other special conversion should be here
//        return arg;
//    }


    private Object toJavaPresentation(Object object) {
        return toJavaPresentation(object, null, null);
    }

    private Object toJavaPresentation(Object object, Class target) {
        return toJavaPresentation(object, target, null);
    }

    private Object toJavaPresentation(Object object, Class target, @Nullable Class targetClassGeneric) {
        
        if (object == null) {
            return null;
        }
        
        if (object instanceof WebIDLAdapter) {
            object = ((WebIDLAdapter) object).target;
        }

        if (object instanceof FunctionAdapter) {
            object = ((FunctionAdapter) object).getCallback();
        }

        if (target != null) {

            if (target.isAssignableFrom(object.getClass())) {
                return object;
            }

            if (target.isEnum() && (object instanceof String /*|| object instanceof DOMString || object instanceof USVString*/)) {
                return Enum.valueOf((Class<Enum>) target, object.toString());
            } else if (object instanceof ScriptObjectMirror && target.equals(Sequence.class)) {
                val scriptObjectMirror = ((ScriptObjectMirror) object);
                val size = scriptObjectMirror.size();
                List<Object> items = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    items.add(toJavaPresentation(scriptObjectMirror.getSlot(i), targetClassGeneric == null ? Object.class : targetClassGeneric));
                }
                return new SequenceImpl<>(items);
            } else if (object instanceof ScriptObjectMirror && target.equals(Function.class)) {
                return (Function<Object>) ((ScriptObjectMirror) object)::call;
            } else if (object instanceof ScriptObjectMirror && target.equals(String.class)) {
                // we do not want to expose js entities as code strings for now
                return null;
            } else if (MultiTypedef.class.isAssignableFrom(target)) {
                Typedef typedefAnn = (Typedef) target.getAnnotation(Typedef.class);
                val classes = typedefAnn.value();
                Object casted = null;

                for (int i = 0; i < classes.length; i++) {
                    casted = toJavaPresentation(object, classes[i]);
                    if (casted != null) {
                        break;
                    }
                }

                if (casted == null) {
                    return null;
                } else {
                    try {
                        Object result = target.newInstance();
                        ((MultiTypedef) result).set(casted);
                        return result;
                    } catch (InstantiationException | IllegalAccessException e) {
                        return null;
                    }
                }

            } else {
                try {
                    return ScriptUtils.convert(object, target);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } else {
            return object;
        }
    }

    private boolean hasAnnotation(Parameter parameter, Class<? extends Annotation> annotation) {
        return parameter.isAnnotationPresent(annotation);
    }

    private boolean isDefaultNull(Parameter parameter) {
        return parameter.isAnnotationPresent(DefaultNull.class);
    }

    private java.util.Optional<Object> getDefaultValue(Parameter parameter) {

        final Object result;

        if (parameter.isAnnotationPresent(DefaultString.class)) {
            result = parameter.getAnnotation(DefaultString.class).value();
        } else if (parameter.isAnnotationPresent(DefaultBoolean.class)) {
            result = parameter.getAnnotation(DefaultBoolean.class).value();
        } else if (parameter.isAnnotationPresent(DefaultDouble.class)) {
            result = parameter.getAnnotation(DefaultDouble.class).value();
        } else if (parameter.isAnnotationPresent(DefaultLong.class)) {
            result = parameter.getAnnotation(DefaultLong.class).value();
        } else {
            result = null;
        }

        if (parameter.getType().isEnum()) {
            try {
                return Optional.of(Enum.valueOf((Class) parameter.getType(), String.valueOf(result)));
            } catch (IllegalArgumentException e) {
                return Optional.ofNullable(result);
            }
        }

        return java.util.Optional.ofNullable(result);
    }

    @Override
    public String toString() {
        return target.toString();
    }
}
