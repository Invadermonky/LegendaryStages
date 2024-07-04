package com.invadermonky.templatemod.util;

import com.invadermonky.templatemod.TemplateMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.internal.LogManagerStatus;

public class LogHelper {
    private static final Logger LOG = LogManager.getLogger(TemplateMod.MOD_NAME);
    public static void debug(Object obj) { LOG.debug(obj); }
    public static void error(Object obj) { LOG.error(obj); }
    public static void fatal(Object obj) { LOG.fatal(obj); }
    public static void info(Object obj) { LOG.info(obj); }
    public static void trace(Object obj) { LOG.trace(obj); }
    public static void warn(Object obj) { LOG.warn(obj); }
}
