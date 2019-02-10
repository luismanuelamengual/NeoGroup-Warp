package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataElement;
import org.neogroup.warp.data.DataObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.neogroup.warp.Warp.getLogger;

public abstract class Resources {

    private static Map<String, Resource> resources;
    private static Map<Class, Resource> resourcesByModelClass;

    static {
        resources = new HashMap<>();
        resourcesByModelClass = new HashMap<>();
    }

    public static void register(String resourceName, Class<? extends Resource> resourceClass) {
        try {
            Type type = resourceClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                Class modelClass = (Class) fieldArgTypes[0];
                Resource resource = resourceClass.getConstructor().newInstance();
                StringBuilder log = new StringBuilder();

                log.append("Resource \"").append(resourceClass.getName()).append("\" registered !! [");
                if (resourceName != null && !resourceName.isEmpty()) {
                    resources.put(resourceName, resource);
                    log.append("name: ").append(resourceName);
                }
                if (!modelClass.isAssignableFrom(DataObject.class)) {
                    resourcesByModelClass.put(modelClass, resource);
                    log.append(", class: ").append(modelClass.getName());
                }
                log.append("]");

                getLogger().info(log.toString());
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Error registering resource \"" + resourceClass.getName() + "\" !!", ex);
        }
    }

    public static <M extends DataElement> ResourceProxy<M> get(Class<M> modelClass) {
        Resource<M> resource = resourcesByModelClass.get(modelClass);
        if (resource == null) {
            throw new RuntimeException("Resource not found for model \"" + modelClass  + "\" !!");
        }
        return new ResourceProxy<>(modelClass.toString(), resource);
    }

    public static ResourceProxy<DataObject> get(String resourceName) {
        Resource<DataObject> resource = resources.get(resourceName);
        if (resource == null) {
            throw new RuntimeException("Resource not found with name \"" + resourceName + "\" !!");
        }
        return new ResourceProxy<>(resourceName, resource);
    }
}