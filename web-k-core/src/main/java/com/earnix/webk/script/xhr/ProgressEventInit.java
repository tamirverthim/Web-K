package com.earnix.webk.script.xhr;

import com.earnix.webk.script.web_idl.Unsigned;
import com.earnix.webk.script.whatwg_dom.EventInit;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ProgressEventInit extends EventInit {
    boolean lengthComputable = false;
    @Unsigned long loaded = 0;
    @Unsigned long total = 0;
}
