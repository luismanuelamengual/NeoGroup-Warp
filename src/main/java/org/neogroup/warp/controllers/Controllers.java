package org.neogroup.warp.controllers;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;
import org.neogroup.warp.WarpContext;
import org.neogroup.warp.formatters.Formatter;
import org.neogroup.warp.formatters.JsonFormatter;
import org.neogroup.warp.controllers.routing.Error;
import org.neogroup.warp.controllers.routing.*;
import org.neogroup.warp.views.View;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neogroup.warp.Warp.getLogger;

public abstract class Controllers {

    private static final String JSON_CONTENT_TYPE = "application/json";

    private static final String ROUTE_PATH_SEPARATOR = "/";

    private static final Map<Class, Object> controllers;
    private static final Routes routes;
    private static final Routes beforeRoutes;
    private static final Routes afterRoutes;
    private static final Routes notFoundRoutes;
    private static final Routes errorRoutes;

    private static final Formatter defaultFormatter = new JsonFormatter();

    static {
        controllers = new HashMap<>();
        routes = new Routes();
        beforeRoutes = new Routes();
        afterRoutes = new Routes();
        notFoundRoutes = new Routes();
        errorRoutes = new Routes();
    }

    private static String getNormalizedPath (String path) {
        if (!path.isEmpty() && path.startsWith(ROUTE_PATH_SEPARATOR)) {
            path = path.substring(1);
        }
        return path;
    }

    public static void registerController (String controllerPath, Class controllerClass) {
        try {
            getLogger().info("Registering controller \"" + controllerClass.getName() + "\" ...");
            Object controller = controllerClass.getConstructor().newInstance();
            controllers.put(controllerClass, controller);

            for (Method controllerMethod : controllerClass.getDeclaredMethods()) {
                Route routeAnnotation = controllerMethod.getAnnotation(Route.class);
                if (routeAnnotation != null) {
                    registerRoutes(routes, null, controllerPath, routeAnnotation.value(), controller, controllerMethod, routeAnnotation.priority());
                }
                Get getAnnotation = controllerMethod.getAnnotation(Get.class);
                if (getAnnotation != null) {
                    registerRoutes(routes, "GET", controllerPath, getAnnotation.value(), controller, controllerMethod, getAnnotation.priority());
                }
                Post postAnnotation = controllerMethod.getAnnotation(Post.class);
                if (postAnnotation != null) {
                    registerRoutes(routes, "POST", controllerPath, postAnnotation.value(), controller, controllerMethod, postAnnotation.priority());
                }
                Put putAnnotation = controllerMethod.getAnnotation(Put.class);
                if (putAnnotation != null) {
                    registerRoutes(routes, "PUT", controllerPath, putAnnotation.value(), controller, controllerMethod, putAnnotation.priority());
                }
                Delete deleteAnnotation = controllerMethod.getAnnotation(Delete.class);
                if (deleteAnnotation != null) {
                    registerRoutes(routes, "DELETE", controllerPath, deleteAnnotation.value(), controller, controllerMethod, deleteAnnotation.priority());
                }
                Options optionsAnnotation = controllerMethod.getAnnotation(Options.class);
                if (optionsAnnotation != null) {
                    registerRoutes(routes, "OPTIONS", controllerPath, optionsAnnotation.value(), controller, controllerMethod, optionsAnnotation.priority());
                }
                NotFound notFoundAnnotation = controllerMethod.getAnnotation(NotFound.class);
                if (notFoundAnnotation != null) {
                    registerRoutes(notFoundRoutes, null, controllerPath, notFoundAnnotation.value(), controller, controllerMethod, notFoundAnnotation.priority());
                }
                Error errorAnnotation = controllerMethod.getAnnotation(Error.class);
                if (errorAnnotation != null) {
                    registerRoutes(errorRoutes, null, controllerPath, errorAnnotation.value(), controller, controllerMethod, errorAnnotation.priority());
                }
                Before beforeAnnotation = controllerMethod.getAnnotation(Before.class);
                if (beforeAnnotation != null) {
                    registerRoutes(beforeRoutes, null, controllerPath, beforeAnnotation.value(), controller, controllerMethod, beforeAnnotation.priority());
                }
                BeforeGet beforeGetAnnotation = controllerMethod.getAnnotation(BeforeGet.class);
                if (beforeGetAnnotation != null) {
                    registerRoutes(beforeRoutes, "GET", controllerPath, beforeGetAnnotation.value(), controller, controllerMethod, beforeGetAnnotation.priority());
                }
                BeforePost beforePostAnnotation = controllerMethod.getAnnotation(BeforePost.class);
                if (beforePostAnnotation != null) {
                    registerRoutes(beforeRoutes, "POST", controllerPath, beforePostAnnotation.value(), controller, controllerMethod, beforePostAnnotation.priority());
                }
                BeforePut beforePutAnnotation = controllerMethod.getAnnotation(BeforePut.class);
                if (beforePutAnnotation != null) {
                    registerRoutes(beforeRoutes, "PUT", controllerPath, beforePutAnnotation.value(), controller, controllerMethod, beforePutAnnotation.priority());
                }
                BeforeDelete beforeDeleteAnnotation = controllerMethod.getAnnotation(BeforeDelete.class);
                if (beforeDeleteAnnotation != null) {
                    registerRoutes(beforeRoutes, "DELETE", controllerPath, beforeDeleteAnnotation.value(), controller, controllerMethod, beforeDeleteAnnotation.priority());
                }
                After afterAnnotation = controllerMethod.getAnnotation(After.class);
                if (afterAnnotation != null) {
                    registerRoutes(afterRoutes, null, controllerPath, afterAnnotation.value(), controller, controllerMethod, afterAnnotation.priority());
                }
                AfterGet afterGetAnnotation = controllerMethod.getAnnotation(AfterGet.class);
                if (afterGetAnnotation != null) {
                    registerRoutes(afterRoutes, "GET", controllerPath, afterGetAnnotation.value(), controller, controllerMethod, afterGetAnnotation.priority());
                }
                AfterPost afterPostAnnotation = controllerMethod.getAnnotation(AfterPost.class);
                if (afterPostAnnotation != null) {
                    registerRoutes(afterRoutes, "POST", controllerPath, afterPostAnnotation.value(), controller, controllerMethod, afterPostAnnotation.priority());
                }
                AfterPut afterPutAnnotation = controllerMethod.getAnnotation(AfterPut.class);
                if (afterPutAnnotation != null) {
                    registerRoutes(afterRoutes, "PUT", controllerPath, afterPutAnnotation.value(), controller, controllerMethod, afterPutAnnotation.priority());
                }
                AfterDelete afterDeleteAnnotation = controllerMethod.getAnnotation(AfterDelete.class);
                if (afterDeleteAnnotation != null) {
                    registerRoutes(afterRoutes, "DELETE", controllerPath, afterDeleteAnnotation.value(), controller, controllerMethod, afterDeleteAnnotation.priority());
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering controller \"" + controllerClass.getName() + "\" !!", ex);
        }
    }

    private static String getPath(String controllerPath, String routePath) {
        StringBuilder path = new StringBuilder();
        String normalizedControllerPath = controllerPath.trim();
        String normalizedRoutePath = routePath.trim();
        if (!normalizedControllerPath.isEmpty()) {
            path.append(normalizedControllerPath);
        }
        if (!routePath.isEmpty()) {
            if (normalizedControllerPath.endsWith(ROUTE_PATH_SEPARATOR) && normalizedRoutePath.startsWith(ROUTE_PATH_SEPARATOR)) {
                normalizedRoutePath = normalizedRoutePath.substring(1);
            } else if (!normalizedControllerPath.endsWith(ROUTE_PATH_SEPARATOR) && !normalizedRoutePath.startsWith(ROUTE_PATH_SEPARATOR)) {
                path.append(ROUTE_PATH_SEPARATOR);
            }
            path.append(normalizedRoutePath);
        }
        return path.toString();
    }

    public static void registerRoute (String method, String path, Object controller, Method controllerMethod, int priority) {
        registerRoute(routes, method, path, controller, controllerMethod, priority);
    }

    public static void registerNotFoundRoute (String method, String path, Object controller, Method controllerMethod, int priority) {
        registerRoute(notFoundRoutes, method, path, controller, controllerMethod, priority);
    }

    public static void registerErrorRoute (String method, String path, Object controller, Method controllerMethod, int priority) {
        registerRoute(errorRoutes, method, path, controller, controllerMethod, priority);
    }

    private static void registerRoutes (Routes routes, String method, String controllerPath, String[] paths, Object controller, Method controllerMethod, int priority) {
        for (String path : paths) {
            registerRoute(routes, method, getPath(controllerPath, path), controller, controllerMethod, priority);
        }
    }

    private static void registerRoute (Routes routes, String method, String path, Object controller, Method controllerMethod, int priority) {
        path = getNormalizedPath(path);
        String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
        routes.addRoute(new RouteEntry(method, pathParts, controller, controllerMethod, priority));
        getLogger().info((method != null?"[" + method + "] ":"") + "\"" + path + "\" => " + controller.getClass().getName() + "@" + controllerMethod.getName());
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

    public static void handle(WarpContext context) {
        Request request = context.getRequest();
        Response response = context.getResponse();
        try {
            String path = getNormalizedPath(request.getPathInfo());
            String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
            List<RouteEntry> routes = Controllers.routes.findRoutes(request.getMethod(), pathParts);

            if (!routes.isEmpty()) {
                List<RouteEntry> beforeRoutes = Controllers.beforeRoutes.findRoutes(request.getMethod(), pathParts);
                for (RouteEntry route : beforeRoutes) {
                    executeRoute(route, pathParts, request, response);
                    if (context.isRoutingStopped()) {
                        break;
                    }
                }

                if (!context.isRoutingStopped()) {
                    for (RouteEntry route : routes) {
                        executeRoute(route, pathParts, request, response);
                        if (context.isRoutingStopped()) {
                            break;
                        }
                    }
                }

                if (!context.isRoutingStopped()) {
                    List<RouteEntry> afterRoutes = Controllers.afterRoutes.findRoutes(request.getMethod(), pathParts);
                    for (RouteEntry route : afterRoutes) {
                        executeRoute(route, pathParts, request, response);
                        if (context.isRoutingStopped()) {
                            break;
                        }
                    }
                }

                writeResponse(response);
            }
            else {
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

        String[] routePathParts = route.getPathParts();
        for (int i = 0; i < routePathParts.length; i++) {
            String pathPart = routePathParts[i];
            if (pathPart.startsWith(Routes.ROUTE_PARAMETER_PREFIX)) {
                String parameterName = pathPart.substring(1);
                String parameterValue = pathParts[i];
                if (!parameterValue.isEmpty()) {
                    request.set(parameterName, parameterValue);
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
            else {
                Parameter paramAnnotation = methodParameter.getAnnotation(Parameter.class);
                if (paramAnnotation != null) {
                    String parameterName = paramAnnotation.value();
                    Object parameterValue = request.get(parameterName, methodParameterClass);
                    if (parameterValue == null) {
                        if (paramAnnotation.required()) {
                            throw new RuntimeException("Parameter \"" + paramAnnotation.value() + "\" is required !!");
                        } else {
                            if (!Object.class.isAssignableFrom(methodParameterClass)) {
                                parameterValue = Array.get(Array.newInstance(methodParameterClass, 1), 0);
                            }
                        }
                    }
                    parameters[i] = parameterValue;
                } else {
                    HeaderParameter headerParamAnnotation = methodParameter.getAnnotation(HeaderParameter.class);
                    if (headerParamAnnotation != null) {
                        if (!String.class.isAssignableFrom(methodParameterClass)) {
                            throw new RuntimeException("Header parameter \"" + headerParamAnnotation.value() + "\" must be of type String !!");
                        }
                        String parameterName = headerParamAnnotation.value();
                        String parameterValue = request.getHeader (parameterName);
                        if (parameterValue == null) {
                            if (headerParamAnnotation.required()) {
                                throw new RuntimeException("Header parameter \"" + headerParamAnnotation.value() + "\" is required !!");
                            }
                        }
                        parameters[i] = parameterValue;
                    } else {
                        Body bodyAnnotation = methodParameter.getAnnotation(Body.class);
                        if (bodyAnnotation != null) {
                            if (byte[].class.isAssignableFrom(methodParameterClass)) {
                                parameters[i] = request.getBodyBytes();
                            } else if (String.class.isAssignableFrom(methodParameterClass)) {
                                parameters[i] = request.getBody();
                            } else if (InputStream.class.isAssignableFrom(methodParameterClass)) {
                                parameters[i] = request.getBodyInputStream();
                            }
                        }
                    }
                }
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
            if (responseObject instanceof View) {
                response.print(((View) responseObject).render());
            } else if (responseObject instanceof String) {
                response.print(responseObject);
            } else if (responseObject instanceof byte[]) {
                response.print((byte[])responseObject);
            } else {
                if (response.getContentType() == null) {
                    response.setContentType(JSON_CONTENT_TYPE);
                }
                response.print(defaultFormatter.format(responseObject));
            }
            response.flush();
        }
    }
}
