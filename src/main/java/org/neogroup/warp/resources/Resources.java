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
                if (modelClass.isAssignableFrom(DataObject.class)) {
                    resources.put(resourceName, resource);
                    getLogger().info("Resource \"" + resourceClass.getName() + "\" registered !! [name=" + resourceName + "]");
                }
                else {
                    resourcesByModelClass.put(modelClass, resource);
                    getLogger().info("Resource \"" + resourceClass.getName() + "\" registered !! [modelClass=" + modelClass.getName() + "]");
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Error registering resource \"" + resourceClass.getName() + "\" !!", ex);
        }
    }

    public static <M> ResourceProxy<M> get(Class<M> modelClass) {
        Resource<M> resource = resourcesByModelClass.get(modelClass);
        if (resource == null) {
            throw new RuntimeException("Resource not found for model \"" + modelClass  + "\" !!");
        }
        return new ResourceProxy<>(modelClass.toString(), resource);
    }

    public static ResourceProxy<DataObject> get(String resourceName) {
        Resource resource = resources.get(resourceName);
        if (resource == null) {
            resource = new DataResource();
            resources.put(resourceName, resource);
        }
        return new ResourceProxy<>(resourceName, resource);
    }
}