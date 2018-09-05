package org.xhtmlrenderer.script;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.objects.NativeArray;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
//import org.xhtmlrenderer.script.impl.DOMStringImpl;
//import org.xhtmlrenderer.script.impl.USVStringImpl;
import org.xhtmlrenderer.script.web_idl.*;
import org.xhtmlrenderer.script.web_idl.Iterable;
import org.xhtmlrenderer.script.web_idl.Nullable;
import org.xhtmlrenderer.script.web_idl.impl.SequenceImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;
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

    T target;
    ScriptContext js;
    HashMap<String, Object> members = new HashMap<>();


    private WebIDLAdapter(ScriptContext js, T target) {
        this.target = target;
        this.js = js;
        processTarget();
    }
    
    public T getTarget(){
        return target;
    }

    public static WebIDLAdapter obtain(ScriptContext js, Object target) {
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

    private void processTarget() {

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
                    }


                    if (m.isAnnotationPresent(ReadonlyAttribute.class)) {
                        members.put(m.getName(), readonlyAttributeMark);
                        return;
                    }

                    // Function member

                    members.put(m.getName(), new Function<>(js, (ctx, args) -> {
                        final Object res;

                        try {
                            res = MethodUtils.invokeMethod(target, m.getName(), prepareArguments(m, args), m.getParameterTypes());
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException e) {
                            throw new RuntimeException(e);
                        }
                        return wrapIfNeeded(res);

                    }, m.getName()));
                });

        members.put("toString", new Function<>(js, (ctx, arg) -> WebIDLAdapter.this.toString() + " " + target.toString(), "toString"));
        members.put("equals", new Function<>(js, (ctx, arg) ->
                WebIDLAdapter.this.equals(arg[0]), "equals"));
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
//        log.trace("Getting member {} of {}", s, target);
        val member = members.get(s);
        if (member instanceof WebIDLAdapter.AttributeLink) {
            try {
                return wrapIfNeeded((((AttributeLink) member).attribute).get());
            } catch (Exception e){
                System.err.println();
                throw e;
            }
        } else if (readonlyAttributeMark.equals(member)) {
            try {
                return wrapIfNeeded(MethodUtils.invokeMethod(target, s));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else if (target instanceof LegacyUnenumerableNamedProperties){
            return wrapIfNeeded(((LegacyUnenumerableNamedProperties) target).namedItem(s));
        } else {
            return member;
        }
    }

    @Override
    public Object getSlot(int i) {
        if (target instanceof Indexed) {
            return wrapIfNeeded(((Indexed) target).elementAtIndex(i));
        } else if (target instanceof LegacyUnenumerableNamedProperties) {
            return wrapIfNeeded(((LegacyUnenumerableNamedProperties) target).item(i));
        } else if (target instanceof Iterable) {
            return wrapIfNeeded(((Iterable) target).item(i));
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
                val unwrapped = autoCast(o);
                val adapted = autoCast(unwrapped, ((AttributeLink) member).attributeClass);

                att.set(adapted);
            } catch (Exception e) {
                log.error("setMember Attribute {}", s, e);
//                members.remove(s);
//                setMember(s, o);
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


    public Object wrapIfNeeded(Object res) {

        if (res instanceof JSObject) {
            return res;
        }
        

        if (res == null || ClassUtils.isPrimitiveOrWrapper(res.getClass())) {
            return res;
        }

//        if (res instanceof DOMString || res instanceof USVString) {
//            return res.toString();
//        }

        if (res.getClass().getPackage().getName().startsWith("org.xhtmlrenderer.script")) {
            return WebIDLAdapter.obtain(js, res);
        } else {
            return res;
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

                if (!hasAnnotation(parameter, org.xhtmlrenderer.script.web_idl.Optional.class)) {
                    log.warn("Absent required argument {} for method {}", parameter, method);
                }

                arg = getDefaultValue(parameter).orElse(null);

            } else {
                rawArg = rawArgs[i];

                if (rawArg != null) {

                    arg = autoCast(rawArg, parameterType, genericType);

                } else {
                    // null parameter

                    if(hasAnnotation(parameter, TreatNullAs.class) && parameter.getAnnotation(TreatNullAs.class).value() == NullTreat.EmptyString){
                        arg = "";
                    } else {
                        if (!hasAnnotation(parameter, Nullable.class) && hasAnnotation(parameter, org.xhtmlrenderer.script.web_idl.Optional.class)) {
                            val optionalDefaultValue = getDefaultValue(parameter);
                            if (optionalDefaultValue.isPresent()) {
                                arg = optionalDefaultValue.get();
                            } else {
                                arg = null;
                                if (!isDefaultNull(parameter)) {
                                    log.warn("Non-nullable non-optional parameter {} of method {} received null", parameter, method);
                                }
                            }
                        } else {
                            log.warn("Absent required parameter {} for method {}", parameter, method);
                            arg = null;
                        }
                    }
                }
            }

            result[i] = autoCast(arg, parameterType);

        }
        
        if (varargFromIndex > -1) {
            List<Object> varArgs = new ArrayList<>();
            val varArgsArrayType = method.getParameterTypes()[method.getParameterCount() - 1];
            val varArgsElementType = varArgsArrayType.getComponentType();
            for (int i = varargFromIndex; i < rawArgs.length; i++) {
                varArgs.add(autoCast(rawArgs[i], varArgsElementType));
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
//            arg = new SequenceImpl<>(Stream.of(objectsArray).map(obj -> autoCast(obj, (Class)sequenceComponentType)).collect(Collectors.toList()));
//        } else if (parameterType.equals(Sequence.class) && rawArg instanceof ScriptObjectMirror) {
//            val sequenceComponentType = getTypeOfInterfaceGeneric(parameterType);
//            val mirror = (ScriptObjectMirror) rawArg;
//            
//            val objects = new ArrayList<>();
//            for (int i = 0; i < mirror.size(); i++) {
//                objects.add(autoCast(mirror.getSlot(i), sequenceComponentType));
//            }
//            
//            arg = new SequenceImpl<>(Stream.of(objects).map(obj -> autoCast(obj, sequenceComponentType), sequenceComponentType)).collect(Collectors.toList()));
//        } else {
//            arg = rawArg;
//        }
//
//        // other special conversion should be here
//        return arg;
//    }



    private Object autoCast(Object object) {
        return autoCast(object, null, null);
    }

    private Object autoCast(Object object, Class target) {
        return autoCast(object, target, null);
    }

        private Object autoCast(Object object, Class target, @Nullable Class targetClassGeneric) {
        Object result;
        if (object instanceof WebIDLAdapter) {
                object = ((WebIDLAdapter) object).target; 
        }
        
        if(target != null) {
            if (target.isEnum() && (object instanceof String /*|| object instanceof DOMString || object instanceof USVString*/)) {
                result = Enum.valueOf((Class<Enum>) target, object.toString());
            } else if (object instanceof ScriptObjectMirror && target.equals(Sequence.class)) {
                val scriptObjectMirror = ((ScriptObjectMirror) object);
                val size = scriptObjectMirror.size();
                List<Object> items = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    items.add(autoCast(scriptObjectMirror.getSlot(i), targetClassGeneric == null ? Object.class : targetClassGeneric));
                }
                return new SequenceImpl<>(items);
            } else {
                try {
                    result = ScriptUtils.convert(object, target);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } else {
            result = object;
        }
        return result;
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
            } catch (IllegalArgumentException e){
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
