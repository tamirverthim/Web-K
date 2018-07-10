package org.xhtmlrenderer.js;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.xhtmlrenderer.js.impl.DOMStringImpl;
import org.xhtmlrenderer.js.web_idl.*;
import org.xhtmlrenderer.js.web_idl.Nullable;
import org.xhtmlrenderer.js.whatwg_dom.Attr;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@SuppressWarnings("unchecked")
@Slf4j
public class WebIDLAdapter<T> implements JSObject {

    private T target;
    private JS js;
    private HashMap<String, Object> members = new HashMap<>();

    public WebIDLAdapter(JS js, T target) {
        this.target = target;
        this.js = js;
        processTarget();
    }
    
    Object readonlyAttributeMark = new Object();
    
    @Getter
    @AllArgsConstructor
    class AttributeLink {
        Attribute attribute;
        Class attributeClass;
    }

    private void processTarget() {

        Stream.of(target.getClass().getInterfaces()).flatMap(i -> Stream.of(i.getMethods())).forEach(m -> {
            // Attribute member

            try {
                if (m.getReturnType().equals(Attribute.class)) {
                    members.put(m.getName(), new AttributeLink(
                            (Attribute<?>) m.invoke(target), 
                            (Class<?>)((ParameterizedType)m.getGenericReturnType()).getActualTypeArguments()[0])
                    );
                    return;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            
            
            if(m.isAnnotationPresent(ReadonlyAttribute.class)) {
                members.put(m.getName(), readonlyAttributeMark);
                return;
            }

            // Function member

            members.put(m.getName(), new Function<>((ctx, args) -> {
                final Object res;

                try {
                    res = MethodUtils.invokeMethod(target, m.getName(), prepareArguments(m, args), m.getParameterTypes());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
                return wrapIfNeeded(res);

            }, m.getName()));
        });
        
        members.put("toString", new Function<>((ctx, arg) -> target.toString(), "toString"));
    }

    @Override
    public Object call(Object o, Object... objects) {
        // todo
        return null;
    }

    @Override
    public Object newObject(Object... objects) {
        return new WebIDLAdapter(js, ReflectionHelper.create(target.getClass()));
    }

    @Override
    public Object eval(String s) {
        return js.eval(s);
    }

    @Override
    public Object getMember(String s) {
        val member = members.get(s);
        if (member instanceof WebIDLAdapter.AttributeLink) {
            return wrapIfNeeded(((Attribute) ((AttributeLink)member).attribute).get());
        } else if (readonlyAttributeMark.equals(member)) {
            try {
                return wrapIfNeeded(MethodUtils.invokeMethod(target, s));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            return member;
        }
    }

    @Override
    public Object getSlot(int i) {
        if (target instanceof Indexed) {
            return wrapIfNeeded(((Indexed) target).elementAtIndex(i));
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
        return false;
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
                val att = ((AttributeLink)member).attribute;
                val unwrapped = unwrapIfNeeded(o);
                val adapted = autoCast(unwrapped, ((AttributeLink)member).attributeClass);
                
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


    public static Object wrapIfNeeded(Object res) {
        if (res == null || ClassUtils.isPrimitiveOrWrapper(res.getClass())) {
            return res;
        } 
        
        String[] packages = new String[]{"org.xhtmlrenderer.js.impl"};
        if (ArrayUtils.contains(packages, res.getClass().getPackage().getName())) {
            return new WebIDLAdapter<>(JS.getInstance(), res);
        } else {
            return res;
        }
    }

    public static Object unwrapIfNeeded(Object object) {
        if(object instanceof String) {
            return DOMStringImpl.of((String) object);
        }
        if (object instanceof WebIDLAdapter) {
            return ((WebIDLAdapter) object).target;
        }
        return object;
    }
    
    private Object[] prepareArguments(Method method, Object[] rawArgs){
        Object[] result = new Object[method.getParameterTypes().length];
        if(log.isDebugEnabled()){
            if(result.length < rawArgs.length) {
                log.debug("Too many params from JS call to {}", method.toString());
            }
        }
        
        for (int i = 0; i < result.length; i++) {
            Object arg;
            Object rawArg;
            val parameter = method.getParameters()[i];
            val parameterType = method.getParameterTypes()[i];

            if(rawArgs.length < i + 1){
                // absent parameter
                
                if(!hasAnnotation(parameter, org.xhtmlrenderer.js.web_idl.Optional.class)){
                    log.warn("Absent required argument {} for method {}", parameter, method);
                }
                
                arg = getDefaultValue(parameter).orElse(null);
                
            } else {
                rawArg = rawArgs[i];
                
                if(rawArg != null) {

                    // auto DOMString support

                    if (parameterType.equals(DOMString.class)) {
                        if (rawArg instanceof String) {
                            arg = DOMStringImpl.of((String) rawArg);
                        } else {
                            log.warn("Auto toString for JS call to parameter {} of {}", i, method);
                            arg = rawArg.toString();
                        }
                    } else {
                        arg = rawArg;
                    }
                    
                    // other special conversion shoul be here
                    
                } else {
                    // null parameter
                    
                    if(!hasAnnotation(parameter, Nullable.class) && hasAnnotation(parameter, org.xhtmlrenderer.js.web_idl.Optional.class)){
                        val optionalDefaultValue = getDefaultValue(parameter);
                        if(optionalDefaultValue.isPresent()){
                            arg = optionalDefaultValue.get();
                        } else {
                            arg = null;
                            if(!isDefaultNull(parameter)) {
                                log.warn("Non-nullable non-optional parameter {} of method {} received null", parameter, method);
                            }
                        }
                    } else {
                        log.warn("Absent required parameter {} for method {}", parameter, method);
                        arg = null;
                    }
                }
            }
            
            result[i] = autoCast(arg, parameterType);
          
        }
        
        return result;
    }
    
    private Object autoCast(Object object, Class<?> target){
        Object result;
        if(target.isEnum() && object instanceof String) {
            result = Enum.valueOf((Class<Enum>) target, (String)object);
        } else {
            result = ScriptUtils.convert(object, target);
        }
        return result;
    }
    
    private boolean hasAnnotation(Parameter parameter, Class<? extends Annotation> annotation){
        return parameter.isAnnotationPresent(annotation);
    }
    
    private boolean isDefaultNull(Parameter parameter){
        return parameter.isAnnotationPresent(DefaultNull.class);
    }
    
    private Optional<Object> getDefaultValue(Parameter parameter){
    
        final Object result;
        
        if(parameter.isAnnotationPresent(DefaultString.class)){
            result = parameter.getAnnotation(DefaultString.class).value();
        }    
        
        else if(parameter.isAnnotationPresent(DefaultBoolean.class)){
            result = parameter.getAnnotation(DefaultBoolean.class).value();
        }
        
        else if(parameter.isAnnotationPresent(DefaultDouble.class)){
            result = parameter.getAnnotation(DefaultDouble.class).value();
        }    
        
        else if(parameter.isAnnotationPresent(DefaultLong.class)){
            result = parameter.getAnnotation(DefaultLong.class).value();
        } else {
            result = null;
        }
        
        return Optional.ofNullable(result);
    }
}
