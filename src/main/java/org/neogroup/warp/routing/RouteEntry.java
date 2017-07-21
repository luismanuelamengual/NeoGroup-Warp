
package org.neogroup.warp.routing;

import org.neogroup.warp.controllers.Controller;

import java.lang.reflect.Method;

public class RouteEntry {

    private final String method;
    private final String path;
    private final Controller controller;
    private final Method controllerMethod;

    public RouteEntry(String method, String path, Controller controller, Method controllerMethod) {
        this.method = method;
        this.path = path;
        this.controller = controller;
        this.controllerMethod = controllerMethod;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Controller getController() {
        return controller;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
