
package org.neogroup.warp.controllers.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Routes {

    public static final String ROUTE_GENERIC_PATH = "*";
    public static final String ROUTE_PARAMETER_PREFIX = ":";

    private static final String ROUTE_PARAMETER_WILDCARD = "%";
    private static final Comparator<RouteEntry> routeEntryComparator = (r1, r2) -> r2.getPriority() - r1.getPriority();

    private final RouteIndex routeIndex;

    public Routes() {
        routeIndex = new RouteIndex();
    }

    public void addRoute(RouteEntry route) {
        RouteIndex currentRootIndex = routeIndex;
        for (String pathPart : route.getPathParts()) {
            if (pathPart.equals(ROUTE_GENERIC_PATH)) {
                currentRootIndex.addGenericRoute(route);
                return;
            }
            else if (pathPart.startsWith(ROUTE_PARAMETER_PREFIX)) {
                pathPart = ROUTE_PARAMETER_WILDCARD;
            }
            RouteIndex routeIndex = currentRootIndex.getRouteIndex(pathPart);
            if (routeIndex == null) {
                routeIndex = new RouteIndex();
                currentRootIndex.addRouteIndex(pathPart, routeIndex);
            }
            currentRootIndex = routeIndex;
        }
        currentRootIndex.addRoute(route);
    }

    public void clearRoutes () {
        routeIndex.clear();
    }

    public List<RouteEntry> findRoutes(String method, String[] pathParts) {
        List<RouteEntry> routes = new ArrayList<>();
        findRoutes(routes, routeIndex, method, pathParts, 0);
        Collections.sort(routes, routeEntryComparator);
        return routes;
    }

    private void findRoutes(List<RouteEntry> routes, RouteIndex currentRootIndex, String method, String[] pathParts, int pathIndex) {
        if (pathIndex < pathParts.length) {
            String pathPart = pathParts[pathIndex];
            RouteIndex nextRootIndex = currentRootIndex.getRouteIndex(pathPart);
            if (nextRootIndex != null) {
                findRoutes(routes, nextRootIndex, method, pathParts, pathIndex + 1);
            }
            nextRootIndex = currentRootIndex.getRouteIndex(ROUTE_PARAMETER_WILDCARD);
            if (nextRootIndex != null) {
                findRoutes(routes, nextRootIndex, method, pathParts, pathIndex + 1);
            }
        }
        else {
            for (RouteEntry routeEntry : currentRootIndex.getRoutes()) {
                if (routeEntry.getMethod() == null || routeEntry.getMethod().equals(method)) {
                    routes.add(routeEntry);
                }
            }
        }
        for (RouteEntry routeEntry : currentRootIndex.getGenericRoutes()) {
            if (routeEntry.getMethod() == null || routeEntry.getMethod().equals(method)) {
                routes.add(routeEntry);
            }
        }
    }
}
