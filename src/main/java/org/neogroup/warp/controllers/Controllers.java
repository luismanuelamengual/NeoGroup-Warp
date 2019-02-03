package org.neogroup.warp.controllers;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;
import org.neogroup.warp.controllers.routing.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neogroup.warp.Warp.getLogger;

public abstract class Controllers {
    private static final Map<Class, Object> controllers;
    private static final Routes routes;
    private static final Routes beforeRoutes;
    private static final Routes afterRoutes;
    private static final Routes notFoundRoutes;
    private static final Routes errorRoutes;

    static {
        controllers = new HashMap<>();
        routes = new Routes();
        beforeRoutes = new Routes();
        afterRoutes = new Routes();
        notFoundRoutes = new Routes();
        errorRoutes = new Routes();
    }

    public static void registerController (Class controllerClass) {
        try {
            Object controller = controllerClass.getConstructor().newInstance();
            controllers.put(controllerClass, controller);

            for (Method controllerMethod : controllerClass.getDeclaredMethods()) {
                try {
                    Get getAnnotation = controllerMethod.getAnnotation(Get.class);
                    if (getAnnotation != null) {
                        registerRoutes(RouteType.NORMAL, "GET", getAnnotation.value(), controller, controllerMethod);
                    }
                    Post postAnnotation = controllerMethod.getAnnotation(Post.class);
                    if (postAnnotation != null) {
                        registerRoutes(RouteType.NORMAL, "POST", getAnnotation.value(), controller, controllerMethod);
                    }
                    Put putAnnotation = controllerMethod.getAnnotation(Put.class);
                    if (putAnnotation != null) {
                        registerRoutes(RouteType.NORMAL, "PUT", getAnnotation.value(), controller, controllerMethod);
                    }
                    Delete deleteAnnotation = controllerMethod.getAnnotation(Delete.class);
                    if (deleteAnnotation != null) {
                        registerRoutes(RouteType.NORMAL, "DELETE", getAnnotation.value(), controller, controllerMethod);
                    }
                    Path pathAnnotation = controllerMethod.getAnnotation(Path.class);
                    if (pathAnnotation != null) {
                        registerRoutes(RouteType.NORMAL, null, getAnnotation.value(), controller, controllerMethod);
                    }

                } catch (Exception ex) {
                }
            }
            getLogger().info("Controller \"" + controllerClass.getName() + "\" registered !!");
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering controller \"" + controllerClass.getName() + "\" !!", ex);
        }
    }

    public static void registerRoutes (RouteType type, String method, String[] paths, Object controller, Method controllerMethod) {
        for (String path : paths) {
            registerRoute(type, method, path, controller, controllerMethod);
        }
    }

    public static void registerRoute (RouteType type, String method, String path, Object controller, Method controllerMethod) {
        Routes routes = null;
        switch(type) {
            case NORMAL: routes = Controllers.routes; break;
            case BEFORE: routes = Controllers.beforeRoutes; break;
            case AFTER: routes = Controllers.afterRoutes; break;
            case NOT_FOUND: routes = Controllers.notFoundRoutes; break;
            case ERROR: routes = Controllers.errorRoutes; break;
        }
        routes.addRoute(new RouteEntry(method, path, controller, controllerMethod));
    }

    public static <C extends Object> C get (Class<? extends C> controllerClass) {
        return (C)controllers.get(controllerClass);
    }

    public static void handle(Request request, Response response) {
        try {
            List<RouteEntry> routes = Controllers.routes.findRoutes(request);
            if (!routes.isEmpty()) {
                List<RouteEntry> beforeRoutes = Controllers.beforeRoutes.findRoutes(request);
                for (RouteEntry beforeRoute : beforeRoutes) {
                    executeRoute(beforeRoute, request, response);
                }
                if (response.getResponseObject() == null) {
                    for (RouteEntry route : routes) {
                        executeRoute(route, request, response);
                    }
                    List<RouteEntry> afterRoutes = Controllers.afterRoutes.findRoutes(request);
                    for (RouteEntry route : afterRoutes) {
                        executeRoute(route, request, response);
                    }
                }
                writeResponse(response);
            } else {
                List<RouteEntry> notFoundRoutes = Controllers.notFoundRoutes.findRoutes(request);
                if (!notFoundRoutes.isEmpty()) {
                    for (RouteEntry route : notFoundRoutes) {
                        executeRoute(route, request, response);
                    }
                    writeResponse(response);
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Route for path \"" + request.getPathInfo() + "\" not found !!");
                }
            }
        }
        catch (Throwable throwable) {

            try {
                List<RouteEntry> errorRoutes = Controllers.errorRoutes.findRoutes(request);
                if (!errorRoutes.isEmpty()) {
                    for (RouteEntry route : errorRoutes) {
                        executeRoute(route, request, response);
                    }
                    writeResponse(response);
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    throwable.printStackTrace(response.getWriter());
                }
            }
            catch (Throwable errorThrowable) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                try { errorThrowable.printStackTrace(response.getWriter()); } catch (Exception err) {}
            }
        }
    }

    private static void executeRoute (RouteEntry route, Request request, Response response) throws InvocationTargetException, IllegalAccessException {
        Object controller = route.getController();
        Method controllerMethod = route.getControllerMethod();
        Object[] parameters = new Object[controllerMethod.getParameterCount()];
        Object responseObject = controllerMethod.invoke(controller, parameters);
        if (responseObject != null) {
            response.setResponseObject(responseObject);
        }
    }

    private static void writeResponse (Response response) throws IOException {
        Object responseObject = response.getResponseObject();
        if (responseObject != null) {
            response.getWriter().print(responseObject);
            response.flushBuffer();
        }
    }
}
