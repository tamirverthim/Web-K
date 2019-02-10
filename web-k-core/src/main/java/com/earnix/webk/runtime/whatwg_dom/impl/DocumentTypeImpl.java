package com.earnix.webk.runtime.whatwg_dom.impl;

import com.earnix.webk.runtime.whatwg_dom.impl.helper.Validate;
import com.earnix.webk.runtime.whatwg_dom.impl.internal.StringUtil;
import com.earnix.webk.runtime.impl.LeafNode;
import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.DOMException;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.whatwg_dom.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Taras Maslov
 * 7/13/2018
 */
@Slf4j
public class DocumentTypeImpl extends LeafNode implements com.earnix.webk.runtime.whatwg_dom.DocumentType {

    // todo needs a bit of a chunky cleanup. this level of detail isn't needed
    public static final String PUBLIC_KEY = "PUBLIC";
    public static final String SYSTEM_KEY = "SYSTEM";
    private static final String NAME = "name";
    private static final String PUB_SYS_KEY = "pubSysKey"; // PUBLIC or SYSTEM
    private static final String PUBLIC_ID = "publicId";
    private static final String SYSTEM_ID = "systemId";
    // todo: quirk mode from publicId and systemId

    public DocumentTypeImpl() {
        super();
    }

    @Override
    public String name() {
        return attr("name");
    }

    @Override
    public String publicId() {
        return attr("publicId");
    }

    @Override
    public String systemId() {
        return attr("systemId");
    }

    @Override
    public @DOMString Attribute<String> textContent() {
        return new Attribute<String>() {
            @Override
            public String get() {
                return outerHtml();
            }

            @Override
            public void set(String s) {
                log.error("Unimplemented");
            }
        };
    }

    @Override
    public Node appendChild(Node node) {
        throw new DOMException("Illegal operation");
    }

    // region model

    /**
     * Create a new doctype element.
     *
     * @param name     the doctype's name
     * @param publicId the doctype's public ID
     * @param systemId the doctype's system ID
     */
    public DocumentTypeImpl(String name, String publicId, String systemId) {
        Validate.notNull(name);
        Validate.notNull(publicId);
        Validate.notNull(systemId);
        attr(NAME, name);
        attr(PUBLIC_ID, publicId);
        if (has(PUBLIC_ID)) {
            attr(PUB_SYS_KEY, PUBLIC_KEY);
        }
        attr(SYSTEM_ID, systemId);
    }

    /**
     * Create a new doctype element.
     *
     * @param name     the doctype's name
     * @param publicId the doctype's public ID
     * @param systemId the doctype's system ID
     * @param baseUri  unused
     * @deprecated
     */
    public DocumentTypeImpl(String name, String publicId, String systemId, String baseUri) {
        attr(NAME, name);
        attr(PUBLIC_ID, publicId);
        if (has(PUBLIC_ID)) {
            attr(PUB_SYS_KEY, PUBLIC_KEY);
        }
        attr(SYSTEM_ID, systemId);
    }

    /**
     * Create a new doctype element.
     *
     * @param name     the doctype's name
     * @param publicId the doctype's public ID
     * @param systemId the doctype's system ID
     * @param baseUri  unused
     * @deprecated
     */
    public DocumentTypeImpl(String name, String pubSysKey, String publicId, String systemId, String baseUri) {
        attr(NAME, name);
        if (pubSysKey != null) {
            attr(PUB_SYS_KEY, pubSysKey);
        }
        attr(PUBLIC_ID, publicId);
        attr(SYSTEM_ID, systemId);
    }

    public void setPubSysKey(String value) {
        if (value != null)
            attr(PUB_SYS_KEY, value);
    }

    @Override
    public String nodeName() {
        return "#doctype";
    }

    @Override
    protected void outerHtmlHead(Appendable accum, int depth, DocumentImpl.OutputSettings out) throws IOException {
        if (out.syntax() == DocumentImpl.OutputSettings.Syntax.html && !has(PUBLIC_ID) && !has(SYSTEM_ID)) {
            // looks like a html5 doctype, go lowercase for aesthetics
            accum.append("<!doctype");
        } else {
            accum.append("<!DOCTYPE");
        }
        if (has(NAME))
            accum.append(" ").append(attr(NAME));
        if (has(PUB_SYS_KEY))
            accum.append(" ").append(attr(PUB_SYS_KEY));
        if (has(PUBLIC_ID))
            accum.append(" \"").append(attr(PUBLIC_ID)).append('"');
        if (has(SYSTEM_ID))
            accum.append(" \"").append(attr(SYSTEM_ID)).append('"');
        accum.append('>');
    }

    @Override
    protected void outerHtmlTail(Appendable accum, int depth, DocumentImpl.OutputSettings out) {
    }

    private boolean has(final String attribute) {
        return !StringUtil.isBlank(attr(attribute));
    }
    
    // endregion
}
