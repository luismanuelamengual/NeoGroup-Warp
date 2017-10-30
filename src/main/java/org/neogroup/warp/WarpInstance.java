package org.neogroup.warp;

import org.neogroup.util.Scanner;
import org.neogroup.warp.controllers.Controller;
import org.neogroup.warp.controllers.Request;
import org.neogroup.warp.controllers.Response;
import org.neogroup.warp.controllers.routing.*;
import org.neogroup.warp.controllers.routing.Error;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WarpInstance {

    private boolean initialized;
    private final Map<Class, Object> controllers;
    private final Routes routes;
    private final Routes beforeRoutes;
    private final Routes afterRoutes;
    private final Routes notFoundRoutes;
    private final Routes errorRoutes;

    public WarpInstance () {
        this(null);
    }

    public WarpInstance (String scanBasePackage) {

        initialized = false;
        this.controllers = new HashMap<>();
        this.routes = new Routes();
        this.beforeRoutes = new Routes();
        this.afterRoutes = new Routes();
        this.notFoundRoutes = new Routes();
        this.errorRoutes = new Routes();
    }

    protected void initialize () {
        initialize(null);
    }

    protected void initialize (String basePackage) {

        if (!initialized) {
            controllers.clear();
            routes.clearRoutes();
            beforeRoutes.clearRoutes();
            afterRoutes.clearRoutes();
            notFoundRoutes.clearRoutes();
            errorRoutes.clearRoutes();

            Scanner scanner = new Scanner();
            scanner.findClasses(cls -> {
                if ((basePackage == null || cls.getPackage().getName().startsWith(basePackage))) {

                    Controller controllerAnnotation = (Controller) cls.getAnnotation(Controller.class);
                    if (controllerAnnotation != null) {
                        try {
                            if (controllerAnnotation.singleInstance()) {
                                controllers.put(cls, cls.newInstance());
                            }

                            for (Method controllerMethod : cls.getDeclaredMethods()) {
                                Get getAnnotation = controllerMethod.getAnnotation(Get.class);
                                if (getAnnotation != null) {
                                    for (String path : getAnnotation.value()) {
                                        routes.addRoute(new RouteEntry("GET", path, cls, controllerMethod));
                                    }
                                }
                                Post postAnnotation = controllerMethod.getAnnotation(Post.class);
                                if (postAnnotation != null) {
                                    for (String path : postAnnotation.value()) {
                                        routes.addRoute(new RouteEntry("POST", path, cls, controllerMethod));
                                    }
                                }
                                Put putAnnotation = controllerMethod.getAnnotation(Put.class);
                                if (putAnnotation != null) {
                                    for (String path : putAnnotation.value()) {
                                        routes.addRoute(new RouteEntry("PUT", path, cls, controllerMethod));
                                    }
                                }
                                Delete deleteAnnotation = controllerMethod.getAnnotation(Delete.class);
                                if (deleteAnnotation != null) {
                                    for (String path : deleteAnnotation.value()) {
                                        routes.addRoute(new RouteEntry("DELETE", path, cls, controllerMethod));
                                    }
                                }
                                Route requestAnnotation = controllerMethod.getAnnotation(Route.class);
                                if (requestAnnotation != null) {
                                    for (String path : requestAnnotation.value()) {
                                        routes.addRoute(new RouteEntry(null, path, cls, controllerMethod));
                                    }
                                }
                                Before beforeAnnotation = controllerMethod.getAnnotation(Before.class);
                                if (beforeAnnotation != null) {
                                    for (String path : beforeAnnotation.value()) {
                                        beforeRoutes.addRoute(new RouteEntry(null, path, cls, controllerMethod));
                                    }
                                }
                                After afterAnnotation = controllerMethod.getAnnotation(After.class);
                                if (afterAnnotation != null) {
                                    for (String path : afterAnnotation.value()) {
                                        afterRoutes.addRoute(new RouteEntry(null, path, cls, controllerMethod));
                                    }
                                }
                                NotFound notFoundAnnotation = controllerMethod.getAnnotation(NotFound.class);
                                if (notFoundAnnotation != null) {
                                    for (String path : notFoundAnnotation.value()) {
                                        notFoundRoutes.addRoute(new RouteEntry(null, path, cls, controllerMethod));
                                    }
                                }
                                Error errorAnnotation = controllerMethod.getAnnotation(Error.class);
                                if (errorAnnotation != null) {
                                    for (String path : errorAnnotation.value()) {
                                        errorRoutes.addRoute(new RouteEntry(null, path, cls, controllerMethod));
                                    }
                                }
                            }
                            return true;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                return false;
            });
            initialized = true;
        }
    }

    protected void processServletRequest (HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {

        Request request = new Request(servletRequest);
        Response response = new Response(servletResponse);
        try {
            RouteEntry route = routes.findRoute(request);
            if (route != null) {

                RouteEntry beforeRoute = beforeRoutes.findRoute(request);
                boolean executeRoute = true;
                if (beforeRoute != null) {
                    executeRoute = (boolean)beforeRoute.getControllerMethod().invoke(getController(beforeRoute.getControllerClass()), request, response);
                }

                if (executeRoute) {
                    Object routeResponse = route.getControllerMethod().invoke(getController(route.getControllerClass()), request, response);

                    RouteEntry afterRoute = afterRoutes.findRoute(request);
                    if (afterRoute != null) {
                        routeResponse = afterRoute.getControllerMethod().invoke(getController(afterRoute.getControllerClass()), request, response, routeResponse);
                    }

                    if (routeResponse != null) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }
                }
            } else {
                RouteEntry notFoundRoute = notFoundRoutes.findRoute(request);
                if (notFoundRoute != null) {
                    notFoundRoute.getControllerMethod().invoke(getController(notFoundRoute.getControllerClass()), request, response);
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Route for path \"" + request.getPathInfo() + "\" not found !!");
                }
            }
        }
        catch (Throwable throwable) {
            try {
                RouteEntry errorRoute = errorRoutes.findRoute(request);
                if (errorRoute != null) {
                    errorRoute.getControllerMethod().invoke(getController(errorRoute.getControllerClass()), request, response, throwable);
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    throwable.printStackTrace(response.getWriter());
                }
            }
            catch (Throwable errorThrowable) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                errorThrowable.printStackTrace(response.getWriter());
            }
        }
    }

    public <C extends Object> C getController (Class<? extends C> controllerClass) {

        Object controllerInstance = controllers.get(controllerClass);
        if (controllerInstance == null) {
            try {
                controllerInstance = controllerClass.newInstance();
            } catch (Exception ex) { throw new RuntimeException("Controller \"" + controllerClass.getName() + "\" could not be created", ex); }
        }
        return (C)controllerInstance;
    }
}
