package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Attribute;
import com.earnix.kbrowser.script.web_idl.DOMString;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

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
    private List<String> data = new ArrayList<>();

    public DOMTokenListImpl(Collection<String> strings, Consumer<Collection<String>> onChange) {
        this.data = strings.stream().collect(Collectors.toList());
        this.onChange = onChange;
    }

    private void chagned() {
        if (this.onChange != null) {
            this.onChange.accept(data.stream().map(Object::toString).collect(Collectors.toList()));
        }
    }

    @Override
    public boolean contains(String token) {
        return data.contains(token);
    }

    @Override
    public void add(@DOMString String... tokens) {
        data.addAll(Arrays.asList(tokens));
        chagned();
    }

    @Override
    public void remove(@DOMString String... tokens) {
        val removed = data.removeAll(Arrays.asList(tokens));
        if (removed) {
            chagned();
        }
    }

    @Override
    public boolean toggle(@DOMString String token, Boolean force) {
        log.warn("toggle unimplemented");
        return false;
    }

    @Override
    public boolean replace(@DOMString String token, @DOMString String newToken) {
        val index = data.indexOf(token);
        if (index > -1) {
            data.set(index, newToken);
            chagned();
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(String token) {
        return false;
    }

    @Override
    public Attribute<String> value() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return data.stream().map(Object::toString).reduce("", (a, b) -> a + " " + b);
            }

            @Override
            public void set(String string) {
                data.clear();
                data.add(string);
                chagned();
            }
        };
    }

    @Override
    public String item(int index) {
        return data.size() > index ? data.get(index) : null;
    }

    @Override
    public int length() {
        return data.size();
    }

}
