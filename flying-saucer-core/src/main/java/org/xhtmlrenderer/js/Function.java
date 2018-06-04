package org.xhtmlrenderer.js;

import jdk.nashorn.api.scripting.JSObject;

import java.util.Collection;
import java.util.Set;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class Function <T, K> implements JSObject {

    @FunctionalInterface
    public interface Callback<T, K> {
        T call(Object ctx, K arg);
    }
    
    private Callback<T, K> callback;

    public Function(Callback<T, K> callback){
        this.callback = callback;
    }

    @Override
    public Object call(Object o, Object... objects) {
        Object arg;
        if(objects.length > 1){
            arg = objects;
        } else if(objects.length == 1) {
            arg = objects[0];
        } else {
            arg = null;
        }
        return callback.call(o, (K) arg);
    }

    @Override
    public Object newObject(Object... objects) {
        return new Function<>(callback);
    }

    @Override
    public Object eval(String s) {
        return JS.getInstance().eval(s);
    }

    @Override
    public Object getMember(String s) {
        return null;
    }

    @Override
    public Object getSlot(int i) {
        return null;
    }

    @Override
    public boolean hasMember(String s) {
        return false;
    }

    @Override
    public boolean hasSlot(int i) {
        return false;
    }

    @Override
    public void removeMember(String s) {

    }

    @Override
    public void setMember(String s, Object o) {

    }

    @Override
    public void setSlot(int i, Object o) {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public boolean isInstance(Object o) {
        return false;
    }

    @Override
    public boolean isInstanceOf(Object o) {
        return false;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isFunction() {
        return true;
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
