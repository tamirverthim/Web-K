package org.xhtmlrenderer.js.whatwg_dom;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.xhtmlrenderer.js.impl.DOMStringImpl;
import org.xhtmlrenderer.js.web_idl.Attribute;
import org.xhtmlrenderer.js.web_idl.DOMString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Taras Maslov
 * 7/27/2018
 */
@Slf4j
public class DOMTokenListImpl implements DOMTokenList {

    private final Consumer<Collection<String>> onChange;
    private List<DOMString> data = new ArrayList<>();

    public DOMTokenListImpl(Collection<String> strings, Consumer<Collection<String>> onChange) {
        this.data = strings.stream().map(DOMStringImpl::of).collect(Collectors.toList());
        this.onChange = onChange;
    }

    private void chagned(){
        if(this.onChange != null){
            this.onChange.accept(data.stream().map(Object::toString).collect(Collectors.toList()));
        }
    }
    
    @Override
    public boolean contains(DOMString token) {
        return data.contains(token);
    }

    @Override
    public void add(DOMString... tokens) {
        data.addAll(Arrays.asList(tokens));
        chagned();
    }

    @Override
    public void remove(DOMString... tokens) {
        val removed = data.removeAll(Arrays.asList(tokens));
        if(removed) {
            chagned();
        }
    }

    @Override
    public boolean toggle(DOMString token, Boolean force) {
        log.warn("toggle unimplemented");
        return false;
    }

    @Override
    public boolean replace(DOMString token, DOMString newToken) {
        val index = data.indexOf(token);
        if(index > -1){
            data.set(index, newToken);
            chagned();
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(DOMString token) {
        return false;
    }

    @Override
    public Attribute<DOMString> value() {
        return new Attribute<DOMString>() {
            @Override
            public DOMString get() {
                return DOMStringImpl.of(data.stream().map(Object::toString).reduce("", (a, b) -> a + " " + b));
            }

            @Override
            public void set(DOMString string) {
                data.clear();
                data.add(string);
                chagned();
            }
        };
    }

    @Override
    public DOMString item(int index) {
        return data.size() > index ? data.get(index) : null;
    }

    @Override
    public int length() {
        return data.size();
    }
    
}
