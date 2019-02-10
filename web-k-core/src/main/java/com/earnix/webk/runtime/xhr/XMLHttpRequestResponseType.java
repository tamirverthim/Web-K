package com.earnix.webk.runtime.xhr;

import com.earnix.webk.runtime.web_idl.service.Name;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
public enum XMLHttpRequestResponseType {
    @Name("") none,
    arraybuffer,
    blob,
    document,
    json,
    text
}
