package com.sprouts.spm_framework.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * 读取配置文件的工具类
 * 
 * @author Howson
 * 
 */
public class ConfigUtils {

    // private static String configName = "../config/frame-config.xml";
    private static String configName = "frame-config.xml"; // 本地测试时使用
    private static String configExtName = null; // 外部使用Config工具类时使用
    private static Configuration config = null;
    private static Configuration configExt = null;
    private static Logger log = new Logger();

    public static void initConfig() {
        try {
            config = new XMLConfiguration(configName);
        } catch (ConfigurationException e) {
            log.error("Error in initialize the configuration:", e);
        }
    }

    public static void initExternalConfig(String configPath) {
        try {
            if (configPath != null) {
                configExt = new XMLConfiguration(configPath);
            } else {
                log.warn("The config path you figured is null!");
                return;
            }
            configExtName = configPath;
        } catch (ConfigurationException e) {
            log.error("Error in initialize the configuration:", e);
        }
    }

    /**
     * 外部调用Config工具类时使用
     * 
     * @param key
     * @return
     */
    public static String getValue(String key) {
        initExternalConfig(configExtName);
        return configExt.getString(key);
    }

    public static String getString(String key) {
        initConfig();
        return config.getString(key);
    }
}
