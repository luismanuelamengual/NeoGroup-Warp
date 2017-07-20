
package org.neogroup.warp.routing;

public class RouteEntry {

    private final String method;
    private final String path;
    private final Route route;

    public RouteEntry(String method, String path, Route route) {
        this.method = method;
        this.path = path;
        this.route = route;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Route getRoute() {
        return route;
    }
}
