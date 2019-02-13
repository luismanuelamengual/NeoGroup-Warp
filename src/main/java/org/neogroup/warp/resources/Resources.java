package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.neogroup.warp.Warp.getLogger;

public abstract class Resources {

    private static Map<String, Resource> resources;
    private static Map<Class, Resource> resourcesByModelClass;
    private static DataResource defaultDataResource;

    static {
        resources = new HashMap<>();
        resourcesByModelClass = new HashMap<>();
        defaultDataResource = new DataResource();
    }

    public static void register(String resourceName, Class<? extends Resource> resourceClass) {
        try {
            Type type = resourceClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                Class modelClass = (Class) fieldArgTypes[0];
                Resource resource = resourceClass.getConstructor().newInstance();
                if (!modelClass.isAssignableFrom(DataObject.class)) {
                    resourcesByModelClass.put(modelClass, resource);
                    getLogger().info("Resource \"" + resourceClass.getName() + "\" registered !! [modelClass: " + modelClass.getName() + "]");
                }
                else {
                    resources.put(resourceName, resource);
                    getLogger().info("Resource \"" + resourceClass.getName() + "\" registered !! [name: " + resourceName + "]");
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
            resource = defaultDataResource;
        }
        return new ResourceProxy<>(resourceName, resource);
    }
}