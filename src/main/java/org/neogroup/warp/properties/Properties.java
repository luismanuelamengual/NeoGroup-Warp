package org.neogroup.warp.properties;

import java.io.IOException;
import java.io.InputStream;

public abstract class Properties {

    private static final java.util.Properties properties;
    private static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "warp.properties";

    static {
        properties = new java.util.Properties();
        try (InputStream in = Properties.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_RESOURCE_NAME)) {
            properties.load(in);
        }
        catch (IOException exception) {
            throw new RuntimeException("Error reading properties resource file", exception);
        }
    }

    public static boolean has(String property) {
        return properties.containsKey(property);
    }

    public static void set(String property, Object value) {
        properties.put(property, value);
    }

    public static <R> R get(String property) {
        return (R)properties.getProperty(property);
    }
}