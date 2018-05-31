package org.xhtmlrenderer.js;

import jdk.nashorn.api.scripting.JSObject;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.reflections.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Taras Maslov
 * 5/31/2018
 */
@Slf4j
public class WebIDJAdapter<T> implements JSObject {
    
    private T target;
    private JS js;
    private HashMap<String, Object> members;
    
    public WebIDJAdapter(JS js, T target) {
        this.target = target;
        this.js = js;
        processTarget();
    }

    private void processTarget() {
        ReflectionUtils.getAllMethods(target.getClass(), (method -> !Modifier.isStatic(method.getModifiers()))).forEach(m ->{
            members.put(m.getName(), new Function<Object[], Object>() {
                @Override
                public Object apply(Object[] objects) {
                    try {
                        return m.invoke(target, objects);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
    }

    @Override
    public Object call(Object o, Object... objects) {
        // todo
        return null;
    }

    @Override
    public Object newObject(Object... objects) {
        return new WebIDJAdapter(js, ReflectionHelper.create(target.getClass()));
    }

    @Override
    public Object eval(String s) {
        return js.eval(s);
    }

    @Override
    public Object getMember(String s) {
        return members.get(s);
    }

    @Override
    public Object getSlot(int i) {
        return null;
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
        members.put(s, o);
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
        if (o instanceof WebIDJAdapter){
            val adapter = (WebIDJAdapter)o;
            return target.getClass().isAssignableFrom(adapter.target.getClass());
        } else {
            return false;
        }
    }

    @Override
    public boolean isInstanceOf(Object o) {
        // todo check diff from prev
        if (o instanceof WebIDJAdapter){
            val adapter = (WebIDJAdapter)o;
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
}
