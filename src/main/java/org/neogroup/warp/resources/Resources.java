package org.neogroup.warp.resources;

import java.util.HashMap;
import java.util.Map;

import static org.neogroup.warp.Warp.getLogger;

public abstract class Resources {

    private static Map<String, Resource> resources;
    private static DataResource defaultDataResource;

    static {
        resources = new HashMap<>();
        defaultDataResource = new DataResource();
    }

    public static void register(String resourceName, Class<? extends Resource> resourceClass) {
        try {
            Resource resource = resourceClass.getConstructor().newInstance();
            resources.put(resourceName, resource);
            getLogger().info("Resource \"" + resourceClass.getName() + "\" registered !! [name=" + resourceName + "]");
        }
        catch (Exception ex) {
            throw new RuntimeException("Error registering resource \"" + resourceClass.getName() + "\" !!", ex);
        }
    }

    public static ResourceProxy get(String resourceName) {
        Resource resource = resources.get(resourceName);
        if (resource == null) {
            resource = defaultDataResource;
        }
        return new ResourceProxy(resourceName, resource);
    }
}