
package org.neogroup.warp;

import org.neogroup.util.Scanner;
import org.neogroup.warp.controllers.Controller;
import org.neogroup.warp.routing.Get;
import org.neogroup.warp.routing.Route;
import org.neogroup.warp.routing.RouteEntry;
import org.neogroup.warp.routing.Routes;

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

    public WarpServlet() {

        controllers = new ArrayList<>();
        routes = new Routes();
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
                        Route requestAnnotation = controllerMethod.getAnnotation(Route.class);
                        if (requestAnnotation != null) {
                            for (String path : requestAnnotation.value()) {
                                routes.addRoute(new RouteEntry(null, path, controller, controllerMethod));
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
                response.getWriter().println("NOT FOUND !!");
            }
        }
        catch (Exception ex) {
            response.getWriter().println("ERROR: " + ex.getMessage());
        }
    }
}
