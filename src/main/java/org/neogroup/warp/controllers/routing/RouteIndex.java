
package org.neogroup.warp.controllers.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Index of routes
 * @author Luis Manuel Amengual
 */
public class RouteIndex {

    private List<RouteEntry> routes;
    private Map<String, RouteIndex> routeIndexes;

    /**
     *
     */
    public RouteIndex() {
        routes = new ArrayList<>();
        routeIndexes = new HashMap<>();
    }

    /**
     *
     */
    public void clear () {
        routes.clear();
        routeIndexes.clear();
    }

    /**
     *
     * @return
     */
    public List<RouteEntry> getRoutes() {
        return routes;
    }

    /**
     *
     * @param route
     */
    public void addRoute(RouteEntry route) {
        routes.add(route);
    }

    /**
     *
     * @param context
     * @param index
     */
    public void addRouteIndex (String context, RouteIndex index) {
        routeIndexes.put(context, index);
    }

    /**
     *
     * @param context
     * @return
     */
    public RouteIndex getRouteIndex (String context) {
        return routeIndexes.get(context);
    }
}
