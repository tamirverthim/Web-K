package com.earnix.kbrowser.simple.extend.form;

public class NameValuePair {
    private String _name;
    private String _value;

    public NameValuePair(String name, String value) {
        _name = name;
        _value = value;
    }

    public String getName() {
        return _name;
    }

    public String getValue() {
        return _value;
    }

    public String toString() {
        return getName();
    }

    public boolean isHeading() {
        return _value == null;
    }

}
