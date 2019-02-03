package com.earnix.webk.script.impl;

import com.earnix.webk.script.whatwg_dom.Element;
import com.earnix.webk.script.whatwg_dom.NonDocumentTypeChildNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class NonDocumentTypeChildNodeImpl implements NonDocumentTypeChildNode {

    final NodeImpl target;

    @Override
    public Element previousElementSibling() {
        if (target instanceof ElementImpl) {
            return  ((ElementImpl) target).previousElementSibling();
        }

        return null;
    }

    @Override
    public Element nextElementSibling() {
        if (target instanceof ElementImpl) {
            return ((ElementImpl) target).nextElementSibling();
        }
        return null;
    }
}
