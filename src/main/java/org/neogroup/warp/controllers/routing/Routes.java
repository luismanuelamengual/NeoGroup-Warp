
package org.neogroup.warp.controllers.routing;

import org.neogroup.warp.Request;

import java.util.List;

public class Routes {

    private static final String ROUTE_GENERIC_PATH = "*";
    private static final String ROUTE_PARAMETER_PREFIX = ":";
    private static final String ROUTE_PARAMETER_WILDCARD = "%";
    private static final String ROUTE_PATH_SEPARATOR = "/";

    private final RouteIndex routeIndex;

    public Routes() {
        routeIndex = new RouteIndex();
    }

    private String getNormalizedPath (String path) {
        if (!path.isEmpty() && path.startsWith(ROUTE_PATH_SEPARATOR)) {
            path = path.substring(1);
        }
        return path;
    }

    public void addRoute(RouteEntry route) {
        String path = getNormalizedPath(route.getPath());
        String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
        RouteIndex currentRootIndex = routeIndex;
        for (String pathPart : pathParts) {
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

    public RouteEntry findRoute(Request request) {
        String path = getNormalizedPath(request.getPathInfo());
        String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
        RouteEntry webRoute = findBestRoute(request, routeIndex, pathParts, 0);

        if (webRoute != null) {
            if (webRoute.getPath().contains(ROUTE_PARAMETER_PREFIX)) {
                String routePath = getNormalizedPath(webRoute.getPath());
                String[] routePathParts = routePath.split(ROUTE_PATH_SEPARATOR);
                for (int i = 0; i < routePathParts.length; i++) {
                    String pathPart = routePathParts[i];
                    if (pathPart.startsWith(ROUTE_PARAMETER_PREFIX)) {
                        String parameterName = pathPart.substring(1);
                        String parameterValue = pathParts[i];
                        request.setParameter(parameterName, parameterValue);
                    }
                }
            }
        }

        return webRoute;
    }

    private RouteEntry findBestRoute(Request request, RouteIndex currentRootIndex, String[] pathParts, int pathIndex) {
        RouteEntry route = null;
        if (pathIndex < pathParts.length) {
            String pathPart = pathParts[pathIndex];
            RouteIndex nextRootIndex = currentRootIndex.getRouteIndex(pathPart);
            if (nextRootIndex != null) {
                route = findBestRoute(request, nextRootIndex, pathParts, pathIndex + 1);
            }
            if (route == null) {
                nextRootIndex = currentRootIndex.getRouteIndex(ROUTE_PARAMETER_WILDCARD);
                if (nextRootIndex != null) {
                    route = findBestRoute(request, nextRootIndex, pathParts, pathIndex + 1);
                }
            }
        }
        else {
            for (RouteEntry routeEntry : currentRootIndex.getRoutes()) {
                if (routeEntry.getMethod() == null || routeEntry.getMethod().equals(request.getMethod())) {
                    route = routeEntry;
                    break;
                }
            }
        }
        if (route == null) {
            for (RouteEntry routeEntry : currentRootIndex.getGenericRoutes()) {
                if (routeEntry.getMethod() == null || routeEntry.getMethod().equals(request.getMethod())) {
                    route = routeEntry;
                    break;
                }
            }
        }
        return route;
    }

    private void findRoutes(List<RouteEntry> routes, Request request, RouteIndex currentRootIndex, String[] pathParts, int pathIndex) {
        if (pathIndex < pathParts.length) {
            String pathPart = pathParts[pathIndex];
            RouteIndex nextRootIndex = currentRootIndex.getRouteIndex(pathPart);
            if (nextRootIndex != null) {
                findRoutes(routes, request, nextRootIndex, pathParts, pathIndex + 1);
            }
            nextRootIndex = currentRootIndex.getRouteIndex(ROUTE_PARAMETER_WILDCARD);
            if (nextRootIndex != null) {
                findRoutes(routes, request, nextRootIndex, pathParts, pathIndex + 1);
            }
        }
        else {
            for (RouteEntry routeEntry : currentRootIndex.getRoutes()) {
                if (routeEntry.getMethod() == null || routeEntry.getMethod().equals(request.getMethod())) {
                    routes.add(routeEntry);
                    break;
                }
            }
        }
        for (RouteEntry routeEntry : currentRootIndex.getGenericRoutes()) {
            if (routeEntry.getMethod() == null || routeEntry.getMethod().equals(request.getMethod())) {
                routes.add(routeEntry);
                break;
            }
        }
    }
}
