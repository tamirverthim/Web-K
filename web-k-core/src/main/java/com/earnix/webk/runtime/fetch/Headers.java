package com.earnix.webk.runtime.fetch;

import com.earnix.webk.runtime.web_idl.ByteString;
import com.earnix.webk.runtime.web_idl.Iterable;
import com.earnix.webk.runtime.web_idl.Nullable;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
public interface Headers extends Iterable<Pair<String, String>> {
    void append(@ByteString String name, @ByteString String value);
    void delete(@ByteString String name);
    @Nullable
    @ByteString String get(@ByteString String name);
    boolean has(ByteString name);
    void set(ByteString name, ByteString value);
//    iterable<ByteString, ByteString>;
}

