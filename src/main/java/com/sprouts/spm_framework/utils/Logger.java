package com.sprouts.spm_framework.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 本地日志的工具类
 * 
 * @author Howson
 * 
 */
public class Logger {

    private static final Log log = LogFactory.getLog("framework");

    public void info(String message) {
        log.info(message);
    }

    public void info(String message, Throwable thr) {
        log.info(message, thr);
    }

    public void error(String message) {
        log.error(message);
    }

    public void error(String message, Throwable thr) {
        log.error(message, thr);
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void warn(String message, Throwable thr) {
        log.warn(message, thr);
    }

    public void debug(String message) {
        log.debug(message);
    }

    public void debug(String message, Throwable thr) {
        log.debug(message, thr);
    }
}
