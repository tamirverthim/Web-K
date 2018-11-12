package com.earnix.webk.script;

import com.earnix.webk.script.web_idl.Function;
import jdk.nashorn.api.scripting.JSObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Set;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FunctionAdapter<T> implements JSObject {

    final String id;
    final ScriptContext scriptContext;
    
    @Getter @Setter private Integer argsCount;
    @Getter @Setter private Function<T> callback;
    
    public FunctionAdapter(ScriptContext scriptContext, Function<T> callback, Integer argsCount, String id) {
        this.callback = callback;
        this.argsCount = argsCount;
        this.id = id;
        this.scriptContext = scriptContext;
    }

    public FunctionAdapter(ScriptContext scriptContext, Function<T> callback, String id) {
        this.callback = callback;
        this.argsCount = null;
        this.id = id;
        this.scriptContext = scriptContext;
    }

    @Override
    public Object call(Object o, Object... objects) {
        
        return callback.call(o, objects);
    }

    @Override
    public Object newObject(Object... objects) {
        return new FunctionAdapter<>(scriptContext, callback, null, id);
    }

    @Override
    public Object eval(String s) {
        return scriptContext.eval(s);
    }

    @Override
    public Object getMember(String s) {
        if ("toString".equals(s)) {
            return new FunctionAdapter<>(scriptContext, (ctx, arg) -> id, 0, id + ".toString");
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
