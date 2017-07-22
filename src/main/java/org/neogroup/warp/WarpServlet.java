
package org.neogroup.warp;

import org.neogroup.util.Scanner;
import org.neogroup.warp.controllers.Controller;
import org.neogroup.warp.routing.*;
import org.neogroup.warp.routing.Error;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WarpServlet extends HttpServlet {

    public static final String SCAN_BASE_PACKAGE_PARAMETER_NAME = "scan_base_package";

    private List<Controller> controllers;
    private final Routes routes;
    private final Routes beforeRoutes;
    private final Routes afterRoutes;
    private final Routes notFoundRoutes;
    private final Routes errorRoutes;

    public WarpServlet() {

        controllers = new ArrayList<>();
        routes = new Routes();
        beforeRoutes = new Routes();
        afterRoutes = new Routes();
        notFoundRoutes = new Routes();
        errorRoutes = new Routes();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        String scanBasePackage = config.getInitParameter(SCAN_BASE_PACKAGE_PARAMETER_NAME);
        Scanner scanner = new Scanner();
        scanner.findClasses(cls -> {
            if (Controller.class.isAssignableFrom(cls) && cls.getPackage().getName().startsWith(scanBasePackage)) {
                try {
                    Controller controller = (Controller)cls.newInstance();
                    controllers.add(controller);

                    for (Method controllerMethod : cls.getDeclaredMethods()) {
                        Get getAnnotation = controllerMethod.getAnnotation(Get.class);
                        if (getAnnotation != null) {
                            for (String path : getAnnotation.value()) {
                                routes.addRoute(new RouteEntry("GET", path, controller, controllerMethod));
                            }
                        }
                        Post postAnnotation = controllerMethod.getAnnotation(Post.class);
                        if (postAnnotation != null) {
                            for (String path : postAnnotation.value()) {
                                routes.addRoute(new RouteEntry("POST", path, controller, controllerMethod));
                            }
                        }
                        Put putAnnotation = controllerMethod.getAnnotation(Put.class);
                        if (putAnnotation != null) {
                            for (String path : putAnnotation.value()) {
                                routes.addRoute(new RouteEntry("PUT", path, controller, controllerMethod));
                            }
                        }
                        Delete deleteAnnotation = controllerMethod.getAnnotation(Delete.class);
                        if (deleteAnnotation != null) {
                            for (String path : deleteAnnotation.value()) {
                                routes.addRoute(new RouteEntry("DELETE", path, controller, controllerMethod));
                            }
                        }
                        Route requestAnnotation = controllerMethod.getAnnotation(Route.class);
                        if (requestAnnotation != null) {
                            for (String path : requestAnnotation.value()) {
                                routes.addRoute(new RouteEntry(null, path, controller, controllerMethod));
                            }
                        }
                        Before beforeAnnotation = controllerMethod.getAnnotation(Before.class);
                        if (beforeAnnotation != null) {
                            for (String path : beforeAnnotation.value()) {
                                beforeRoutes.addRoute(new RouteEntry(null, path, controller, controllerMethod));
                            }
                        }
                        After afterAnnotation = controllerMethod.getAnnotation(After.class);
                        if (afterAnnotation != null) {
                            for (String path : afterAnnotation.value()) {
                                afterRoutes.addRoute(new RouteEntry(null, path, controller, controllerMethod));
                            }
                        }
                        NotFound notFoundAnnotation = controllerMethod.getAnnotation(NotFound.class);
                        if (notFoundAnnotation != null) {
                            for (String path : notFoundAnnotation.value()) {
                                notFoundRoutes.addRoute(new RouteEntry(null, path, controller, controllerMethod));
                            }
                        }
                        Error errorAnnotation = controllerMethod.getAnnotation(Error.class);
                        if (errorAnnotation != null) {
                            for (String path : errorAnnotation.value()) {
                                notFoundRoutes.addRoute(new RouteEntry(null, path, controller, controllerMethod));
                            }
                        }
                    }

                    return true;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }            }
            return false;
        });
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {

        Request request = new Request(servletRequest);
        Response response = new Response(servletResponse);
        try {
            RouteEntry routeEntry = routes.findRoute(request);
            if (routeEntry != null) {
                Object routeResponse = routeEntry.getControllerMethod().invoke(routeEntry.getController(), request, response);
                if (routeResponse != null) {
                    if (routeResponse instanceof String) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }
                }
            } else {
                RouteEntry notFoundRoute = notFoundRoutes.findRoute(request);
                if (notFoundRoute != null) {
                    notFoundRoute.getControllerMethod().invoke(notFoundRoute.getController(), request, response);
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
                    errorRoute.getControllerMethod().invoke(errorRoute.getController(), request, response, throwable);
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
}
