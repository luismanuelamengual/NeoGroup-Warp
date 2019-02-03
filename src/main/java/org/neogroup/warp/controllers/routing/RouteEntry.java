
package org.neogroup.warp.controllers.routing;

import java.lang.reflect.Method;

public class RouteEntry {

    private final String method;
    private final String path;
    private final Object controller;
    private final Method controllerMethod;

    public RouteEntry(String method, String path, Object controller, Method controllerMethod) {
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

    public Object getController() {
        return controller;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
