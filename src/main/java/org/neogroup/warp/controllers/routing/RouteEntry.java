
package org.neogroup.warp.controllers.routing;

import java.util.Map;

public class RouteEntry {

    private final String method;
    private final String path;
    private final AbstractRoute route;
    private Map<String,String> parameters;

    public RouteEntry(String method, String path, AbstractRoute route) {
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

    public AbstractRoute getRoute() {
        return route;
    }


}
