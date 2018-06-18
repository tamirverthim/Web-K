package org.xhtmlrenderer.js;

import jdk.nashorn.api.scripting.JSObject;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.reflections.ReflectionUtils;
import org.xhtmlrenderer.js.web_idl.DOMString;
import org.xhtmlrenderer.js.dom.impl.DOMStringImpl;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.Indexed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

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

    private void processTarget() {
        ReflectionUtils.getAllMethods(target.getClass(), (method -> !Modifier.isStatic(method.getModifiers()))).forEach(m -> {
            
            // Attribute member

            try {
                if (m.getReturnType().equals(Attribute.class)) {
                    members.put(m.getName(), m.invoke(target));
                    return;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            // Function member

            members.put(m.getName(), new Function<>((ctx, arg) -> {
                Object[] adaptedArg;
                if (m.getParameterTypes().length > 0 && m.getParameterTypes()[0] == DOMString.class && arg instanceof String) {
                    adaptedArg = new Object[]{new DOMStringImpl((String) arg)};
                } else if (m.getParameterTypes().length > 1) {
                    adaptedArg = (Object[]) arg;
                    if(adaptedArg[0] instanceof String){
                        adaptedArg[0] = new DOMStringImpl(adaptedArg[0].toString());
                    }
                } else if (arg != null){
                    adaptedArg = new Object[]{arg};
                } else {
                    adaptedArg = null;
                }
                Object res;
                try {
                    res = MethodUtils.invokeMethod(target, m.getName(), adaptedArg, m.getParameterTypes());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
                return wrapIfNeeded(res);

            }));
        });
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
        if (member instanceof Attribute) {
            return wrapIfNeeded(((Attribute) member).get());
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
        if (member instanceof Attribute) {
            
            
            
            try {
                ((Attribute) member).set(unwrapIfNeeded(o));
            } catch (ClassCastException e) {
                members.remove(s);
                setMember(s, o);
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
        
        String[] packages = new String[]{"org.xhtmlrenderer.js.canvas.impl", "org.xhtmlrenderer.js.dom.impl"};
        if (ArrayUtils.contains(packages, res.getClass().getPackage().getName())) {
            return new WebIDLAdapter<>(JS.getInstance(), res);
        } else {
            return res;
        }
    }

    public static Object unwrapIfNeeded(Object object) {
        if(object instanceof String) {
            return new DOMStringImpl((String) object);
        }
        if (object instanceof WebIDLAdapter) {
            return ((WebIDLAdapter) object).target;
        }
        return object;
    }

}
