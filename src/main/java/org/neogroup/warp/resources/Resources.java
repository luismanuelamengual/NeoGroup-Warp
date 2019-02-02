package org.neogroup.warp.resources;

import java.util.HashMap;
import java.util.Map;

public abstract class Resources {

    private static Map<String, Resource<ResourceItem>> resources;
    private static Map<Class, Resource> resourcesByModelClass;

    static {
        resources = new HashMap<>();
        resourcesByModelClass = new HashMap<>();
    }

    public static <M> void  registerResource (Class<M> modelClass, Resource<M> resource) {
        resourcesByModelClass.put(modelClass, resource);
    }

    public static void registerResource (String resourceName, Resource<ResourceItem> resource) {
        resources.put(resourceName, resource);
    }

    public static <M> ResourceProxy<M> get(Class<M> modelClass) {
        Resource<M> resource = resourcesByModelClass.get(modelClass);
        if (resource == null) {
            throw new RuntimeException("Resource not found for model \"" + modelClass  + "\" !!");
        }
        return new ResourceProxy<>(modelClass.toString(), resource);
    }

    public static ResourceProxy<ResourceItem> get(String resourceName) {
        Resource<ResourceItem> resource = resources.get(resourceName);
        if (resource == null) {
            throw new RuntimeException("Resource not found with name \"" + resourceName + "\" !!");
        }
        return new ResourceProxy<>(resourceName, resource);
    }
}