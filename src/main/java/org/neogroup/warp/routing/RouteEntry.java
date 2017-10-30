
package org.neogroup.warp.routing;

import java.lang.reflect.Method;

public class RouteEntry {

    private final String method;
    private final String path;
    private final Class controllerClass;
    private final Method controllerMethod;

    public RouteEntry(String method, String path, Class controllerClass, Method controllerMethod) {
        this.method = method;
        this.path = path;
        this.controllerClass = controllerClass;
        this.controllerMethod = controllerMethod;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Class getControllerClass() {
        return controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
