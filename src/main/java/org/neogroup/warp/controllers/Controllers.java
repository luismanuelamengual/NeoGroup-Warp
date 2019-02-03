package org.neogroup.warp.controllers;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;
import org.neogroup.warp.controllers.routing.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
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
            Object controller = null;

            if (!Modifier.isAbstract(controllerClass.getModifiers())) {
                controller = controllerClass.getConstructor().newInstance();
                controllers.put(controllerClass, controller);
            }

            for (Field controllerField : controllerClass.getDeclaredFields()) {

                if (AbstractRoute.class.isAssignableFrom(controllerField.getType())) {

                    try {
                        controllerField.setAccessible(true);
                        AbstractRoute route = (AbstractRoute) controllerField.get(controller);

                        Get getAnnotation = controllerField.getAnnotation(Get.class);
                        if (getAnnotation != null) {
                            for (String path : getAnnotation.value()) {
                                registerRoute("GET", path, route);
                            }
                        }
                        Post postAnnotation = controllerField.getAnnotation(Post.class);
                        if (postAnnotation != null) {
                            for (String path : postAnnotation.value()) {
                                registerRoute("POST", path, route);
                            }
                        }
                        Put putAnnotation = controllerField.getAnnotation(Put.class);
                        if (putAnnotation != null) {
                            for (String path : putAnnotation.value()) {
                                registerRoute("PUT", path, route);
                            }
                        }
                        Delete deleteAnnotation = controllerField.getAnnotation(Delete.class);
                        if (deleteAnnotation != null) {
                            for (String path : deleteAnnotation.value()) {
                                registerRoute("DELETE", path, route);
                            }
                        }
                        Path pathAnnotation = controllerField.getAnnotation(Path.class);
                        if (pathAnnotation != null) {
                            for (String path : pathAnnotation.value()) {
                                registerRoute(null, path, route);
                            }
                        }

                    } catch (Exception ex) {
                    }
                }
            }
            getLogger().info("Controller \"" + controllerClass.getName() + "\" registered !!");
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering controller \"" + controllerClass.getName() + "\" !!", ex);
        }
    }

    public static void registerRoute (String method, String path, AbstractRoute route) {

        Routes routesCollection = null;
        if (route instanceof Route) {
            routesCollection = routes;
        } else if (route instanceof BeforeRoute) {
            routesCollection = beforeRoutes;
        } else if (route instanceof AfterRoute) {
            routesCollection = afterRoutes;
        } else if (route instanceof NotFoundRoute) {
            routesCollection = notFoundRoutes;
        } else if (route instanceof ErrorRoute) {
            routesCollection = errorRoutes;
        }

        if (routesCollection != null) {
            routesCollection.addRoute(new RouteEntry(method, path, route));
        }
    }

    public static <C extends Object> C get (Class<? extends C> controllerClass) {
        return (C)controllers.get(controllerClass);
    }

    public static void handle(Request request, Response response) {

        try {
            RouteEntry routeEntry = routes.findBestRoute(request);
            if (routeEntry != null) {

                RouteEntry beforeRouteEntry = beforeRoutes.findBestRoute(request);
                boolean executeRoute = true;
                if (beforeRouteEntry != null) {
                    executeRoute = ((BeforeRoute)beforeRouteEntry.getRoute()).handle(request, response);
                }

                if (executeRoute) {
                    Object routeResponse = ((Route)routeEntry.getRoute()).handle(request, response);

                    RouteEntry afterRouteEntry = afterRoutes.findBestRoute(request);
                    if (afterRouteEntry != null) {
                        routeResponse = ((AfterRoute)afterRouteEntry.getRoute()).handle(request, response, routeResponse);
                    }

                    if (routeResponse != null) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }
                }
            } else {

                RouteEntry notFoundRouteEntry = notFoundRoutes.findBestRoute(request);
                if (notFoundRouteEntry != null) {
                    Object routeResponse = ((NotFoundRoute)notFoundRouteEntry.getRoute()).handle(request, response);

                    if (routeResponse != null) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Route for path \"" + request.getPathInfo() + "\" not found !!");
                }
            }
        }
        catch (Throwable throwable) {

            try {
                RouteEntry errorRouteEntry = errorRoutes.findBestRoute(request);
                if (errorRouteEntry != null) {

                    Object routeResponse = ((ErrorRoute)errorRouteEntry.getRoute()).handle(request, response, throwable);

                    if (routeResponse != null) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }

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
}
