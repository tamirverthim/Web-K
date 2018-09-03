package org.xhtmlrenderer.util.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.util.XRLogger;

import java.util.HashMap;
import java.util.logging.Level;

/**
 * @author Taras Maslov
 * 9/3/2018
 */
@Slf4j// todo
public class Slf4jXRLogger implements XRLogger {
    
    private HashMap<String, Logger> loggers = new HashMap<>();
    
    @Override
    public void log(String where, Level level, String msg) {
          
    }

    @Override
    public void log(String where, Level level, String msg, Throwable th) {

    }

    @Override
    public void setLevel(String logger, Level level) {

    }

    public Logger getLogger(String where) {
        return  null;
    }
}
