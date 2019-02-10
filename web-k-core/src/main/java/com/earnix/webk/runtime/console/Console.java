package com.earnix.webk.runtime.console;

import com.earnix.webk.runtime.web_idl.Any;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.DefaultBoolean;
import com.earnix.webk.runtime.web_idl.DefaultString;
import com.earnix.webk.runtime.web_idl.Nullable;
import com.earnix.webk.runtime.web_idl.Optional;
import com.earnix.webk.runtime.web_idl.Sequence;
import com.earnix.webk.runtime.web_idl.service.Name;

/**
 * It is WebIDL namespace. TODO: namespace annotation.
 * <br/>
 * https://console.spec.whatwg.org/#idl-index
 *
 * @author Taras Maslov
 * 11/11/2018
 */
//[Exposed=(Window,Worker,Worklet)]
public interface Console {
    // Logging
    @Name("assert")
    void _assert(@Optional @DefaultBoolean(false) boolean condition, @Any Object... data);

    void clear();

    void debug(@Any Object... data);

    void error(@Any Object... data);

    void info(@Any Object... data);

    void log(@Any Object... data);

    void table(@Any Object tabularData, @Optional Sequence<@DOMString String> properties);

    void trace(@Any Object... data);

    void warn(@Any Object... data);

    void dir(@Any Object item, @Optional @Nullable Object options);

    void dirxml(Object... data);

    // Counting
    void count(@Optional @DOMString @DefaultString("default") String label);

    void countReset(@Optional @DOMString @DefaultString("default") String label);

    // Grouping
    void group(@Any Object... data);

    void groupCollapsed(@Any Object... data);

    void groupEnd();

    // Timing
    void time(@Optional @DOMString @DefaultString("default") String label);

    void timeLog(@Optional @DOMString @DefaultString("default") String label, @Any Object... data);

    void timeEnd(@Optional @DOMString @DefaultString("default") String label);
}
