package com.earnix.webk.runtime.console.impl;

import com.earnix.webk.runtime.console.Console;
import com.earnix.webk.runtime.web_idl.DOMString;
import com.earnix.webk.runtime.web_idl.Sequence;
import com.earnix.webk.util.XRLog;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.logging.Level;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Taras Maslov
 * 11/11/2018
 */
@Slf4j
public class ConsoleImpl implements Console {

    @Override
    public void _assert(boolean condition, Object... data) {
        if (condition) {
            return;
        }

        val message = "Assertion failed";

        final String[] strings;

        if (data.length == 0) {
            strings = new String[]{message};
        } else {
            if (data[0] instanceof String) {
                strings = Stream.of(data).map(String::valueOf).toArray(String[]::new);
                strings[0] = message + ": " + strings[0];
            } else {
                strings = new String[data.length + 1];
                strings[0] = message;
                System.arraycopy(
                        Stream.of(data).map(String::valueOf).toArray(String[]::new), 0, strings, 1, data.length
                );
            }
        }

        // data appending not done, need mutable varargs support

        log.info("assert " + StringUtils.join(strings, " "));

    }

    @Override
    public void clear() {

    }

    @Override
    public void debug(Object... data) {
        log.info("Debug: \n" + toString(data));
    }

    @Override
    public void error(Object... data) {
        log.info("Error: \n" + toString(data));
    }

    @Override
    public void info(Object... data) {
        log.info("Info: \n" + toString(data));
    }

    @Override
    public void log(Object... data) {
        log.info( "Log: \n" + toString(data));
    }

    @Override
    public void table(Object tabularData, Sequence<@DOMString String> properties) {
        log.info("Table: \n" + String.valueOf(tabularData));
        //noinspection unchecked
        log.info(StringUtils.join(
                StreamSupport.<String>stream(properties.spliterator(), false).toArray(String[]::new),
                " "
        ));

    }

    @Override
    public void trace(Object... data) {
        log.info("Trace: \n" + toString(data));
    }

    @Override
    public void warn(Object... data) {
        XRLog.script(Level.WARNING, "Warning: \n" + toString(data));
    }

    @Override
    public void dir(Object item, Object options) {

    }

    @Override
    public void dirxml(Object... data) {

    }

    @Override
    public void count(@DOMString String label) {

    }

    @Override
    public void countReset(@DOMString String label) {

    }

    @Override
    public void group(Object... data) {

    }

    @Override
    public void groupCollapsed(Object... data) {

    }

    @Override
    public void groupEnd() {

    }

    @Override
    public void time(@DOMString String label) {

    }

    @Override
    public void timeLog(@DOMString String label, Object... data) {

    }

    @Override
    public void timeEnd(@DOMString String label) {

    }

    private String toString(Object... data) {
        return StringUtils.join(Stream.of(data).map(String::valueOf).toArray(String[]::new), " ");
    }
}
