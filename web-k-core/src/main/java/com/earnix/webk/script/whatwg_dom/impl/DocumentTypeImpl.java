package com.earnix.webk.script.whatwg_dom.impl;

import com.earnix.webk.dom.nodes.DocumentTypeModel;
import com.earnix.webk.script.impl.NodeImpl;
import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.DOMString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@Slf4j
public class DocumentTypeImpl extends NodeImpl implements com.earnix.webk.script.whatwg_dom.DocumentType {

    private DocumentTypeModel target;

    public DocumentTypeImpl(DocumentTypeModel target) {
        super(target);
        this.target = target;
    }

    @Override
    public String name() {
        return target.attr("name");
    }

    @Override
    public String publicId() {
        return target.attr("publicId");
    }

    @Override
    public String systemId() {
        return target.attr("systemId");
    }

    @Override
    public @DOMString Attribute<String> textContent() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return target.outerHtml();
            }

            @Override
            public void set(String s) {
                log.error("Unimplemented");
            }
        };
    }
}
