/*
 * {{{ header & license
 * Copyright (c) 2007 Wisconsin Court System
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package com.earnix.kbrowser.log4j;

import com.earnix.kbrowser.util.XRLog;
import com.earnix.kbrowser.util.XRLogger;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Log4JXRLogger implements XRLogger {

    private static final String DEFAULT_LOGGER_NAME = "com.earnix.kbrowser.other";

    private static final Map LOGGER_NAME_MAP;

    static {
        LOGGER_NAME_MAP = new HashMap();

        LOGGER_NAME_MAP.put(XRLog.CONFIG, "com.earnix.kbrowser.config");
        LOGGER_NAME_MAP.put(XRLog.EXCEPTION, "com.earnix.kbrowser.exception");
        LOGGER_NAME_MAP.put(XRLog.GENERAL, "com.earnix.kbrowser.general");
        LOGGER_NAME_MAP.put(XRLog.INIT, "com.earnix.kbrowser.init");
        LOGGER_NAME_MAP.put(XRLog.JUNIT, "com.earnix.kbrowser.junit");
        LOGGER_NAME_MAP.put(XRLog.LOAD, "com.earnix.kbrowser.load");
        LOGGER_NAME_MAP.put(XRLog.MATCH, "com.earnix.kbrowser.match");
        LOGGER_NAME_MAP.put(XRLog.CASCADE, "com.earnix.kbrowser.cascade");
        LOGGER_NAME_MAP.put(XRLog.XML_ENTITIES, "com.earnix.kbrowser.load.xmlentities");
        LOGGER_NAME_MAP.put(XRLog.CSS_PARSE, "com.earnix.kbrowser.cssparse");
        LOGGER_NAME_MAP.put(XRLog.LAYOUT, "com.earnix.kbrowser.layout");
        LOGGER_NAME_MAP.put(XRLog.RENDER, "com.earnix.kbrowser.render");
    }

    private String defaultLoggerName = DEFAULT_LOGGER_NAME;
    private Map loggerNameMap = LOGGER_NAME_MAP;

    public void log(String where, Level level, String msg) {
        Logger.getLogger(getLoggerName(where)).log(toLog4JLevel(level), msg);
    }

    public void log(String where, Level level, String msg, Throwable th) {
        Logger.getLogger(getLoggerName(where)).log(toLog4JLevel(level), msg, th);
    }

    private org.apache.log4j.Level toLog4JLevel(Level level) {
        if (level == Level.SEVERE) {
            return org.apache.log4j.Level.ERROR;
        } else if (level == Level.WARNING) {
            return org.apache.log4j.Level.WARN;
        } else if (level == Level.INFO) {
            return org.apache.log4j.Level.INFO;
        } else if (level == Level.CONFIG) {
            return org.apache.log4j.Level.INFO;
        } else if (level == Level.FINE || level == Level.FINER || level == Level.FINEST) {
            return org.apache.log4j.Level.DEBUG;
        } else {
            return org.apache.log4j.Level.INFO;
        }
    }

    private String getLoggerName(String xrLoggerName) {
        String result = (String) loggerNameMap.get(xrLoggerName);
        if (result != null) {
            return result;
        } else {
            return defaultLoggerName;
        }
    }

    public void setLevel(String logger, Level level) {
        throw new UnsupportedOperationException("log4j should be not be configured here");
    }

    public Map getLoggerNameMap() {
        return loggerNameMap;
    }

    public void setLoggerNameMap(Map loggerNameMap) {
        this.loggerNameMap = loggerNameMap;
    }

    public String getDefaultLoggerName() {
        return defaultLoggerName;
    }

    public void setDefaultLoggerName(String defaultLoggerName) {
        this.defaultLoggerName = defaultLoggerName;
    }

}