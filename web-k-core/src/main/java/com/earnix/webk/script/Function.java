package com.earnix.webk.script;

import jdk.nashorn.api.scripting.JSObject;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Set;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Function<T> implements JSObject {

    final String id;
    final ScriptContext scriptContext;

    @FunctionalInterface
    public interface Callback<T> {
        T call(Object ctx, Object... arg);
    }

    private Callback<T> callback;

    public Function(ScriptContext scriptContext, Callback<T> callback, String id) {
        this.callback = callback;
        this.id = id;
        this.scriptContext = scriptContext;
    }

    @Override
    public Object call(Object o, Object... objects) {
        return callback.call(o, objects);
    }

    @Override
    public Object newObject(Object... objects) {
        return new Function<>(scriptContext, callback, id);
    }

    @Override
    public Object eval(String s) {
        return scriptContext.eval(s);
    }

    @Override
    public Object getMember(String s) {
        if ("toString".equals(s)) {
            return new Function<>(scriptContext, (Callback<Object>) (ctx, arg) -> id, id + ".toString");
        }
        return null;
    }

    @Override
    public Object getSlot(int i) {
        return null;
    }

    @Override
    public boolean hasMember(String s) {
        return "toString".equals(s);
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
