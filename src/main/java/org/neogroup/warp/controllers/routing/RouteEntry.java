
package org.neogroup.warp.controllers.routing;

import java.lang.reflect.Method;

public class RouteEntry {

    private final String method;
    private final String[] pathParts;
    private final Object controller;
    private final Method controllerMethod;
    private final int priority;
    private final boolean auxiliary;

    public RouteEntry(String method, String[] pathParts, Object controller, Method controllerMethod, int priority, boolean auxiliary) {
        this.method = method;
        this.pathParts = pathParts;
        this.controller = controller;
        this.controllerMethod = controllerMethod;
        this.priority = priority;
        this.auxiliary = auxiliary;
    }

    public String getMethod() {
        return method;
    }

    public String[] getPathParts() {
        return pathParts;
    }

    public Object getController() {
        return controller;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isAuxiliary() {
        return auxiliary;
    }
}
