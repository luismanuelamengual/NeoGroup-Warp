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

    private static final String ROUTE_PATH_SEPARATOR = "/";

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

    private static String getNormalizedPath (String path) {
        if (!path.isEmpty() && path.startsWith(ROUTE_PATH_SEPARATOR)) {
            path = path.substring(1);
        }
        return path;
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
        path = getNormalizedPath(path);
        String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
        routes.addRoute(new RouteEntry(method, pathParts, controller, controllerMethod, priority, auxiliary));
        getLogger().info((method != null?"[" + method + "] ":"") + "\"" + path + "\" route registered !!");
    }

    public static <C extends Object> C get (Class<? extends C> controllerClass) {
        return (C)controllers.get(controllerClass);
    }

    private static void handleNotFound (Request request, Response response) throws InvocationTargetException, IllegalAccessException, IOException {
        String path = getNormalizedPath(request.getPathInfo());
        String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
        List<RouteEntry> notFoundRoutes = Controllers.notFoundRoutes.findRoutes(request.getMethod(), pathParts);
        if (!notFoundRoutes.isEmpty()) {
            for (RouteEntry route : notFoundRoutes) {
                executeRoute(route, pathParts, request, response);
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
            String path = getNormalizedPath(request.getPathInfo());
            String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
            List<RouteEntry> errorRoutes = Controllers.errorRoutes.findRoutes(request.getMethod(), pathParts);
            if (!errorRoutes.isEmpty()) {
                for (RouteEntry route : errorRoutes) {
                    executeRoute(route, pathParts, request, response, throwable);
                }
                writeResponse(response);
            } else {
                throw throwable;
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
                String path = getNormalizedPath(request.getPathInfo());
                String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
                List<RouteEntry> routes = Controllers.routes.findRoutes(request.getMethod(), pathParts);
                boolean routeFound = false;
                for (RouteEntry route : routes) {
                    if (!route.isAuxiliary()) {
                        routeFound = true;
                        break;
                    }
                }
                if (routeFound) {
                    for (RouteEntry route : routes) {
                        executeRoute(route, pathParts, request, response);
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

    private static void executeRoute (RouteEntry route, String[] pathParts, Request request, Response response) throws InvocationTargetException, IllegalAccessException {
        executeRoute(route, pathParts, request, response, null);
    }

    private static void executeRoute (RouteEntry route, String[] pathParts, Request request, Response response, Throwable throwable) throws InvocationTargetException, IllegalAccessException {

        Map<String,String> extraRouteParameters = null;
        String[] routePathParts = route.getPathParts();
        for (int i = 0; i < routePathParts.length; i++) {
            String pathPart = routePathParts[i];
            if (pathPart.startsWith(Routes.ROUTE_PARAMETER_PREFIX)) {
                if (extraRouteParameters == null) {
                    extraRouteParameters = new HashMap<>();
                }
                String parameterName = pathPart.substring(1);
                String parameterValue = pathParts[i];
                if (!parameterValue.isEmpty()) {
                    extraRouteParameters.put(parameterName, parameterValue);
                }
            }
        }

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
                String parameterName = paramAnnotation.value();
                Object parameterValue = null;
                if (extraRouteParameters != null && extraRouteParameters.containsKey(parameterName)) {
                    parameterValue = extraRouteParameters.get(parameterName);
                }
                else if (request.hasParameter(parameterName)) {
                    parameterValue = request.getParameter(parameterName);
                }
                if (paramAnnotation.required() && parameterValue == null) {
                    throw new RuntimeException("Parameter \"" + paramAnnotation.value() + "\" is required !!");
                }
                parameters[i] = parameterValue;
            }
        }
        Object responseObject = controllerMethod.invoke(controller, parameters);
        if (responseObject != null) {
            response.setResponseObject(responseObject);
        }
    }

    private static void writeResponse (Response response) {
        Object responseObject = response.getResponseObject();
        if (responseObject != null) {
            response.print(responseObject);
            response.flush();
        }
    }
}
