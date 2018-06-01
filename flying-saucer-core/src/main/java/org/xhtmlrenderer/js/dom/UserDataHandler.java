package org.xhtmlrenderer.js.dom;

import org.xhtmlrenderer.js.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/1/2018
 */
public interface UserDataHandler {
    // OperationType
    final @Unsigned short NODE_CLONED = 1;
    final @Unsigned short NODE_IMPORTED = 2;
    final @Unsigned short NODE_DELETED = 3;
    final @Unsigned short NODE_RENAMED = 4;
    final @Unsigned short NODE_ADOPTED = 5;

    void handle(@Unsigned short operation,
                DOMString key,
                DOMUserData data,
                Node src,
                Node dst);
}
