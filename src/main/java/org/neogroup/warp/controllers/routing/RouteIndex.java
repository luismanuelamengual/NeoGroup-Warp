
package org.neogroup.warp.controllers.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteIndex {

    private List<RouteEntry> routes;
    private List<RouteEntry> genericRoutes;
    private Map<String, RouteIndex> routeIndexes;

    public RouteIndex() {
        routes = new ArrayList<>();
        genericRoutes = new ArrayList<>();
        routeIndexes = new HashMap<>();
    }

    public void clear () {
        routes.clear();
        genericRoutes.clear();
        routeIndexes.clear();
    }

    public List<RouteEntry> getRoutes() {
        return routes;
    }

    public List<RouteEntry> getGenericRoutes() {
        return genericRoutes;
    }

    public void addRoute(RouteEntry route) {
        routes.add(route);
    }

    public void addGenericRoute(RouteEntry route) {
        genericRoutes.add(route);
    }

    public void addRouteIndex (String context, RouteIndex index) {
        routeIndexes.put(context, index);
    }

    public RouteIndex getRouteIndex (String context) {
        return routeIndexes.get(context);
    }
}
