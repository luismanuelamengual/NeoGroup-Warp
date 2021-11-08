package org.neogroup.warp.resources;

import org.neogroup.warp.controllers.Controllers;
import org.neogroup.warp.controllers.routing.RoutingPriority;
import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.http.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.neogroup.warp.Warp.*;

public abstract class Resources {

    private static final String USE_DATABASE_RESOURCES_PROPERTY = "useDatabaseResources";

    private static Map<String, Resource> resources;
    private static Map<String, Class> modelClassByResourceName;
    private static Map<Class, Resource> resourcesByModelClass;

    static {
        resources = new HashMap<>();
        resourcesByModelClass = new HashMap<>();
        modelClassByResourceName = new HashMap<>();
    }

    public static void register(String resourceName, Class<? extends Resource> resourceClass) {
        register(resourceName, resourceClass, false);
    }

    public static void register(String resourceName, Class<? extends Resource> resourceClass, boolean publish) {
        try {
            Type type = resourceClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                Class modelClass = (Class) fieldArgTypes[0];
                Resource resource = resourceClass.getConstructor().newInstance();
                if (!modelClass.isAssignableFrom(DataObject.class)) {
                    resourcesByModelClass.put(modelClass, resource);
                    modelClassByResourceName.put(resourceName, modelClass);
                    getLogger().info("Resource \"" + resourceClass.getName() + "\" registered !! [modelClass: " + modelClass.getName() + "]");
                }
                resources.put(resourceName, resource);
                getLogger().info("Resource \"" + resourceClass.getName() + "\" registered !! [name: " + resourceName + "]");

                if (publish) {
                    ResourceController resourceController = new ResourceController(resourceName, resource);
                    Class resourceControllerClass = resourceController.getClass();
                    Controllers.registerRoute("GET", resourceName, resourceController, resourceControllerClass.getDeclaredMethod("getResources", Request.class), RoutingPriority.NORMAL);
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
        ResourceProxy<DataObject> resourceProxy = null;
        if (resource == null) {
            if (getProperty(USE_DATABASE_RESOURCES_PROPERTY).equals("true")) {
                try {
                    resourceProxy = new ResourceProxy<>(resourceName, new ConnectionResource(getConnection()));
                } catch (Exception ex) {
                    throw new RuntimeException("Error retrieving default connection for resource \"" + resourceName + "\"");
                }
            } else {
                throw new RuntimeException("Resource \"" + resourceName + "\" not found !!");
            }
        } else {
            Class modelClass = modelClassByResourceName.get(resourceName);
            if (modelClass == null || modelClass.equals(DataObject.class)) {
                resourceProxy = new ResourceProxy<>(resourceName, resource);
            } else {
                resourceProxy = new GenericResourceProxy(resourceName, resource);
            }
        }
        return resourceProxy;
    }
}