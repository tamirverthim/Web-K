package com.earnix.kbrowser.script.whatwg_dom;

import com.earnix.kbrowser.script.web_idl.Callback;
import com.earnix.kbrowser.script.web_idl.Exposed;
import com.earnix.kbrowser.script.web_idl.Unsigned;

/**
 * @author Taras Maslov
 * 6/21/2018
 */
@Exposed(Window.class)
@Callback
public interface NodeFilter {
    // Constants for acceptNode()
    @Unsigned short FILTER_ACCEPT = 1;
    @Unsigned short FILTER_REJECT = 2;
    @Unsigned short FILTER_SKIP = 3;

    // Constants for whatToShow
    @Unsigned long SHOW_ALL = 0xFFFFFFFF;
    @Unsigned long SHOW_ELEMENT = 0x1;
    @Unsigned long SHOW_ATTRIBUTE = 0x2;
    @Unsigned long SHOW_TEXT = 0x4;
    @Unsigned long SHOW_CDATA_SECTION = 0x8;
    @Unsigned long SHOW_ENTITY_REFERENCE = 0x10; // historical
    @Unsigned long SHOW_ENTITY = 0x20; // historical
    @Unsigned long SHOW_PROCESSING_INSTRUCTION = 0x40;
    @Unsigned long SHOW_COMMENT = 0x80;
    @Unsigned long SHOW_DOCUMENT = 0x100;
    @Unsigned long SHOW_DOCUMENT_TYPE = 0x200;
    @Unsigned long SHOW_DOCUMENT_FRAGMENT = 0x400;
    @Unsigned long SHOW_NOTATION = 0x800; // historical

    @Unsigned
    short acceptNode(Node node);
}
