package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Dictionary;
import com.earnix.webk.runtime.web_idl.impl.Name;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Dictionary
public class ElementDefinitionOptions {
    public @DOMString @Name("extends") String _extends;
}
