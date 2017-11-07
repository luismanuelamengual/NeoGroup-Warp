package org.neogroup.warp.controllers;

import org.neogroup.warp.controllers.routing.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Controllers {

    private final Map<Class, Object> controllers;
    private final Routes routes;
    private final Routes beforeRoutes;
    private final Routes afterRoutes;
    private final Routes notFoundRoutes;
    private final Routes errorRoutes;

    public Controllers() {
        this.controllers = new HashMap<>();
        this.routes = new Routes();
        this.beforeRoutes = new Routes();
        this.afterRoutes = new Routes();
        this.notFoundRoutes = new Routes();
        this.errorRoutes = new Routes();
    }

    public void registerController (Class controllerClass) {

        try {
            Object controller = controllerClass.getConstructor().newInstance();
            controllers.put(controllerClass, controller);

            for (Field controllerField : controllerClass.getDeclaredFields()) {

                if (AbstractRoute.class.isAssignableFrom(controllerField.getType())) {

                    try {
                        controllerField.setAccessible(true);
                        AbstractRoute route = (AbstractRoute) controllerField.get(controller);
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
                            Get getAnnotation = controllerField.getAnnotation(Get.class);
                            if (getAnnotation != null) {
                                for (String path : getAnnotation.value()) {
                                    routesCollection.addRoute(new RouteEntry("GET", path, route));
                                }
                            }
                            Post postAnnotation = controllerField.getAnnotation(Post.class);
                            if (postAnnotation != null) {
                                for (String path : postAnnotation.value()) {
                                    routesCollection.addRoute(new RouteEntry("POST", path, route));
                                }
                            }
                            Put putAnnotation = controllerField.getAnnotation(Put.class);
                            if (putAnnotation != null) {
                                for (String path : putAnnotation.value()) {
                                    routesCollection.addRoute(new RouteEntry("PUT", path, route));
                                }
                            }
                            Delete deleteAnnotation = controllerField.getAnnotation(Delete.class);
                            if (deleteAnnotation != null) {
                                for (String path : deleteAnnotation.value()) {
                                    routesCollection.addRoute(new RouteEntry("DELETE", path, route));
                                }
                            }
                            Path pathAnnotation = controllerField.getAnnotation(Path.class);
                            if (pathAnnotation != null) {
                                for (String path : pathAnnotation.value()) {
                                    routesCollection.addRoute(new RouteEntry(null, path, route));
                                }
                            }
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering controller \"" + controllerClass.getName() + "\" !!", ex);
        }
    }

    public <C extends Object> C getController (Class<? extends C> controllerClass) {
        return (C)controllers.get(controllerClass);
    }

    public void handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        Request request = new Request(servletRequest);
        Response response = new Response(servletResponse);

        try {
            RouteEntry routeEntry = routes.findRoute(request);
            if (routeEntry != null) {

                RouteEntry beforeRouteEntry = beforeRoutes.findRoute(request);
                boolean executeRoute = true;
                if (beforeRouteEntry != null) {
                    executeRoute = ((BeforeRoute)beforeRouteEntry.getRoute()).handle(request, response);
                }

                if (executeRoute) {
                    Object routeResponse = ((Route)routeEntry.getRoute()).handle(request, response);

                    RouteEntry afterRouteEntry = afterRoutes.findRoute(request);
                    if (afterRouteEntry != null) {
                        routeResponse = ((AfterRoute)afterRouteEntry.getRoute()).handle(request, response, routeResponse);
                    }

                    if (routeResponse != null) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }
                }
            } else {

                RouteEntry notFoundRouteEntry = notFoundRoutes.findRoute(request);
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
                RouteEntry errorRouteEntry = errorRoutes.findRoute(request);
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
