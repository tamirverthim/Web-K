package com.earnix.webk.script.xhr;

import com.earnix.webk.script.future.Blob;
import com.earnix.webk.script.future.Worker;
import com.earnix.webk.script.html.HTMLFormElement;
import com.earnix.webk.script.web_idl.Constructor;
import com.earnix.webk.script.web_idl.Exposed;
import com.earnix.webk.script.web_idl.Iterable;
import com.earnix.webk.script.web_idl.Nullable;
import com.earnix.webk.script.web_idl.Optional;
import com.earnix.webk.script.web_idl.Sequence;
import com.earnix.webk.script.web_idl.USVString;
import com.earnix.webk.script.whatwg_dom.Window;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Taras Maslov
 * 11/12/2018
 */
@Exposed({Window.class, Worker.class})
@Constructor
public interface FormData extends Iterable<Pair</* USVString */String, FormDataEntryValue>> {

    void constructor(HTMLFormElement form);
    
    void append(@USVString String name, @USVString String value);

    void append(@USVString String name, Blob blobValue, @Optional @USVString String filename);

    void delete(@USVString String name);

    @Nullable
    FormDataEntryValue get(@USVString String name);

    Sequence<FormDataEntryValue> getAll(@USVString String name);

    boolean has(@USVString String name);

    void set(@USVString String name, @USVString String value);

    void set(@USVString String name, Blob blobValue, @Optional @USVString String filename);

    //    iterable<@USVString String , FormDataEntryValue>;
}
