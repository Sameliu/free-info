package com.sijin.free.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sijinzhang on 16/8/24.
 */
public class SystemConfig {
    private static Properties prop;

    static {
        init();
    }

    private static void init() {
        InputStream is = null;
        prop = new Properties();
        try {
            is = SystemConfig.class.getClassLoader().getResourceAsStream("system.properties");
            prop.load(is);
        } catch (Exception e) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static String getValue(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    public static String getValue(String key) {
        return prop.getProperty(key);
    }
}
