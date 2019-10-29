package org.neogroup.warp.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Introspection {

    private static final Map<Class, List<Property>> properties = new HashMap<>();

    public static List<Property> getProperties(Class implementationClass) {
        List<Property> properties = Introspection.properties.get(implementationClass);
        if (properties == null) {
            properties = Introspection.properties.put(implementationClass, new ArrayList<>());
            Field[] declaredFields = implementationClass.getDeclaredFields();
            for (Field field : declaredFields) {
                String propertyName = field.getName();
                String accessorPropertyName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                String getterMethodName = (Boolean.class.isAssignableFrom(field.getType())? "is" : "get") + accessorPropertyName;
                Method getterMethod = null;
                try {
                    getterMethod = implementationClass.getDeclaredMethod(getterMethodName);
                } catch (NoSuchMethodException e) {}
                String setterMethodName = "set" + accessorPropertyName;
                Method setterMethod = null;
                try {
                    setterMethod = implementationClass.getDeclaredMethod(setterMethodName);
                } catch (NoSuchMethodException e) {}
                if (getterMethod != null || setterMethod != null) {
                    properties.add(new Property(propertyName, getterMethod, setterMethod));
                }
            }
        }
        return properties;
    }

    public static class Property<T> {

        private final String name;
        private final Method getterMethod;
        private final Method setterMethod;

        public Property(String name, Method getterMethod, Method setterMethod) {
            this.name = name;
            this.getterMethod = getterMethod;
            this.setterMethod = setterMethod;
        }

        public String getName() {
            return name;
        }

        public <O extends Object> O getValue(T instance) {
            Object result = null;
            try {
                result = getterMethod.invoke(instance);
            } catch (Exception e) {}
            return (O)result;
        }

        public void setValue(T instance, Object value) {
            try {
                setterMethod.invoke(instance, value);
            } catch (Exception e) {}
        }
    }
}
