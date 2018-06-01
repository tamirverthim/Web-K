package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public class DOMException extends Exception {
    public final static short INDEX_SIZE_ERR = 1;
    public final static short DOMSTRING_SIZE_ERR = 2;
    public final static short HIERARCHY_REQUEST_ERR = 3;
    public final static short WRONG_DOCUMENT_ERR = 4;
    public final static short INVALID_CHARACTER_ERR = 5;
    public final static short NO_DATA_ALLOWED_ERR = 6;
    public final static short NO_MODIFICATION_ALLOWED_ERR = 7;
    public final static short NOT_FOUND_ERR = 8;
    public final static short NOT_SUPPORTED_ERR = 9;
    public final static short INUSE_ATTRIBUTE_ERR = 10;
    // Introduced in DOM Level 2:
    public final static short INVALID_STATE_ERR = 11;
    // Introduced in DOM Level 2:
    public final static short SYNTAX_ERR = 12;
    // Introduced in DOM Level 2:
    public final static short INVALID_MODIFICATION_ERR = 13;
    // Introduced in DOM Level 2:
    public final static short NAMESPACE_ERR = 14;
    // Introduced in DOM Level 2:
    public final static short INVALID_ACCESS_ERR = 15;
    // Introduced in DOM Level 3:
    public final static short VALIDATION_ERR = 16;
    // Introduced in DOM Level 3:
    public final static short TYPE_MISMATCH_ERR = 17;

    @Unsigned
    private short code;
}
