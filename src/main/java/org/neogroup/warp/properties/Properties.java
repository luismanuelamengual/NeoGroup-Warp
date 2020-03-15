package org.neogroup.warp.properties;

import java.io.IOException;
import java.io.InputStream;

public abstract class Properties {

    private static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "app.properties";

    static {
        java.util.Properties properties = new java.util.Properties();
        try (InputStream in = Properties.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_RESOURCE_NAME)) {
            if (in != null) {
                properties.load(in);
            }
            for (Object key : properties.keySet()) {
                System.setProperty((String)key, properties.getProperty((String)key));
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Error reading properties resource file", exception);
        }
    }

    public static boolean has(String property) {
        return System.getProperty(property) != null;
    }

    public static void set(String property, String value) {
        System.setProperty(property, value);
    }

    public static String get(String property) {
        return System.getProperty(property);
    }

    public static String get(String property, String defaultValue) {
        return System.getProperty(property, defaultValue);
    }
}