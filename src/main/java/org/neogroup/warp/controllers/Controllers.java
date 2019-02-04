package org.neogroup.warp.controllers;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;
import org.neogroup.warp.controllers.routing.*;
import org.neogroup.warp.controllers.routing.Error;
import org.neogroup.warp.controllers.routing.Parameter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neogroup.warp.Warp.getLogger;

public abstract class Controllers {
    private static final Map<Class, Object> controllers;
    private static final Routes routes;
    private static final Routes notFoundRoutes;
    private static final Routes errorRoutes;

    static {
        controllers = new HashMap<>();
        routes = new Routes();
        notFoundRoutes = new Routes();
        errorRoutes = new Routes();
    }

    public static void registerController (Class controllerClass) {
        try {
            getLogger().info("Registering controller \"" + controllerClass.getName() + "\" ...");
            Object controller = controllerClass.getConstructor().newInstance();
            controllers.put(controllerClass, controller);

            for (Method controllerMethod : controllerClass.getDeclaredMethods()) {
                Get getAnnotation = controllerMethod.getAnnotation(Get.class);
                if (getAnnotation != null) {
                    registerRoutes(routes, "GET", getAnnotation.value(), controller, controllerMethod, getAnnotation.priority(), getAnnotation.auxiliary());
                }
                Post postAnnotation = controllerMethod.getAnnotation(Post.class);
                if (postAnnotation != null) {
                    registerRoutes(routes, "POST", postAnnotation.value(), controller, controllerMethod, postAnnotation.priority(), postAnnotation.auxiliary());
                }
                Put putAnnotation = controllerMethod.getAnnotation(Put.class);
                if (putAnnotation != null) {
                    registerRoutes(routes, "PUT", putAnnotation.value(), controller, controllerMethod, putAnnotation.priority(), putAnnotation.auxiliary());
                }
                Delete deleteAnnotation = controllerMethod.getAnnotation(Delete.class);
                if (deleteAnnotation != null) {
                    registerRoutes(routes, "DELETE", deleteAnnotation.value(), controller, controllerMethod, deleteAnnotation.priority(), deleteAnnotation.auxiliary());
                }
                Route routeAnnotation = controllerMethod.getAnnotation(Route.class);
                if (routeAnnotation != null) {
                    registerRoutes(routes, null, routeAnnotation.value(), controller, controllerMethod, routeAnnotation.priority(), routeAnnotation.auxiliary());
                }
                NotFound notFoundAnnotation = controllerMethod.getAnnotation(NotFound.class);
                if (notFoundAnnotation != null) {
                    registerRoutes(notFoundRoutes, null, notFoundAnnotation.value(), controller, controllerMethod, notFoundAnnotation.priority(), notFoundAnnotation.auxiliary());
                }
                Error errorAnnotation = controllerMethod.getAnnotation(Error.class);
                if (errorAnnotation != null) {
                    registerRoutes(errorRoutes, null, errorAnnotation.value(), controller, controllerMethod, errorAnnotation.priority(), errorAnnotation.auxiliary());
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering controller \"" + controllerClass.getName() + "\" !!", ex);
        }
    }

    public static void registerRoutes (Routes routes, String method, String[] paths, Object controller, Method controllerMethod, int priority, boolean auxiliary) {
        for (String path : paths) {
            registerRoute(routes, method, path, controller, controllerMethod, priority, auxiliary);
        }
    }

    public static void registerRoute (Routes routes, String method, String path, Object controller, Method controllerMethod, int priority, boolean auxiliary) {
        routes.addRoute(new RouteEntry(method, path, controller, controllerMethod, priority, auxiliary));
        getLogger().info((method != null?"[" + method + "] ":"") + "\"" + path + "\" route registered !!");
    }

    public static <C extends Object> C get (Class<? extends C> controllerClass) {
        return (C)controllers.get(controllerClass);
    }

    private static void handleNotFound (Request request, Response response) throws InvocationTargetException, IllegalAccessException, IOException {
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

    private static void handleException (Request request, Response response, Throwable throwable) {
        try {
            List<RouteEntry> errorRoutes = Controllers.errorRoutes.findRoutes(request);
            if (!errorRoutes.isEmpty()) {
                for (RouteEntry route : errorRoutes) {
                    executeRoute(route, request, response, throwable);
                }
                writeResponse(response);
            } else {
                throw new RuntimeException("Unhandled exception");
            }
        }
        catch (Throwable errorThrowable) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try { errorThrowable.printStackTrace(response.getWriter()); } catch (Exception err) {}
        }
    }

    public static void handle(Request request, Response response) {
        try {
            try {
                List<RouteEntry> routes = Controllers.routes.findRoutes(request);
                boolean routeFound = false;
                for (RouteEntry route : routes) {
                    if (!route.isAuxiliary()) {
                        routeFound = true;
                        break;
                    }
                }
                if (routeFound) {
                    for (RouteEntry route : routes) {
                        executeRoute(route, request, response);
                    }
                    writeResponse(response);
                }
                else {
                    throw new RouteNotFoundException();
                }
            } catch (RouteNotFoundException notFoundException) {
                handleNotFound(request, response);
            }
        }
        catch (Throwable throwable) {
            handleException(request, response, throwable);
        }
    }

    private static void executeRoute (RouteEntry route, Request request, Response response) throws InvocationTargetException, IllegalAccessException {
        executeRoute(route, request, response, null);
    }

    private static void executeRoute (RouteEntry route, Request request, Response response, Throwable throwable) throws InvocationTargetException, IllegalAccessException {
        Object controller = route.getController();
        Method controllerMethod = route.getControllerMethod();
        Object[] parameters = new Object[controllerMethod.getParameterCount()];
        java.lang.reflect.Parameter[] methodParameters = controllerMethod.getParameters();
        for (int i = 0; i < methodParameters.length; i++) {
            java.lang.reflect.Parameter methodParameter = methodParameters[i];
            Class methodParameterClass = methodParameter.getType();
            if (Request.class.isAssignableFrom(methodParameterClass)) {
                parameters[i] = request;
            }
            else if (Response.class.isAssignableFrom(methodParameterClass)) {
                parameters[i] = response;
            }
            else if (throwable != null && Throwable.class.isAssignableFrom(methodParameterClass)) {
                parameters[i] = throwable;
            }
            Parameter paramAnnotation = methodParameter.getAnnotation(Parameter.class);
            if (paramAnnotation != null) {
                Object parameterValue = request.getParameter(paramAnnotation.value());
                if (paramAnnotation.required() && parameterValue == null) {
                    throw new RouteNotFoundException();
                }
                parameters[i] = parameterValue;
            }
        }
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
