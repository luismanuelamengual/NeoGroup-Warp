
package org.neogroup.warp.controllers.routing;

import org.neogroup.warp.Request;

/**
 * Collection of routes
 * @author Luis Manuel Amengual
 */
public class Routes {

    private static final String ROUTE_GENERIC_PATH = "*";
    private static final String ROUTE_PARAMETER_PREFIX = ":";
    private static final String ROUTE_PARAMETER_WILDCARD = "%";
    private static final String ROUTE_PATH_SEPARATOR = "/";

    private final RouteIndex routeIndex;

    /**
     *
     */
    public Routes() {
        routeIndex = new RouteIndex();
    }

    /**
     * Adds a new web route for a controller method
     * @param route Route for controller method
     */
    public void addRoute(RouteEntry route) {

        String path = route.getPath();
        String[] pathParts = path.split(ROUTE_PATH_SEPARATOR);
        RouteIndex currentRootIndex = routeIndex;
        for (String pathPart : pathParts) {
            if (pathPart.isEmpty()) {
                continue;
            }
            String index = null;
            if (pathPart.startsWith(ROUTE_PARAMETER_PREFIX)) {
                index = ROUTE_PARAMETER_WILDCARD;
            } else {
                index = pathPart;
            }
            RouteIndex routeIndex = currentRootIndex.getRouteIndex(index);
            if (routeIndex == null) {
                routeIndex = new RouteIndex();
                currentRootIndex.addRouteIndex(index, routeIndex);
            }
            currentRootIndex = routeIndex;
            if (index.equals(ROUTE_GENERIC_PATH)) {
                break;
            }
        }
        currentRootIndex.addRoute(route);
    }

    /**
     * Clear all the added routes for controllers
     */
    public void clearRoutes () {
        routeIndex.clear();
    }

    /**
     * Finds a web route from an http request
     * @param request http request
     * @return route for a controller method
     */
    public RouteEntry findRoute(Request request) {

        String[] pathParts = request.getPathInfo().split(ROUTE_PATH_SEPARATOR);
        RouteEntry webRoute = findRoute(request, routeIndex, pathParts, 0);

        if (webRoute != null) {
            if (webRoute.getPath().contains(ROUTE_PARAMETER_PREFIX)) {
                String[] routePathParts = webRoute.getPath().split(ROUTE_PATH_SEPARATOR);
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

    /**
     * Finds a web route from an http request
     * @param request http request
     * @param currentRootIndex current root index
     * @param pathParts path parts
     * @param pathIndex index of path
     * @return route for a controller method
     */
    protected RouteEntry findRoute(Request request, RouteIndex currentRootIndex, String[] pathParts, int pathIndex) {

        RouteEntry route = null;
        if (pathIndex >= pathParts.length) {
            for (RouteEntry routeEntry : currentRootIndex.getRoutes()) {
                if (routeEntry.getMethod() == null || routeEntry.getMethod().equals(request.getMethod())) {
                    route = routeEntry;
                    break;
                }
            }
            if (route == null) {
                RouteIndex genericRouteIndex = currentRootIndex.getRouteIndex(ROUTE_GENERIC_PATH);
                if (genericRouteIndex != null) {
                    route = findRoute(request, genericRouteIndex, pathParts, pathParts.length);
                }
            }
        }
        else {
            String pathPart = pathParts[pathIndex];
            if (pathPart.isEmpty()) {
                route = findRoute(request, currentRootIndex, pathParts, pathIndex + 1);
            }
            else {
                RouteIndex nextRootIndex = currentRootIndex.getRouteIndex(pathPart);
                if (nextRootIndex != null) {
                    route = findRoute(request, nextRootIndex, pathParts, pathIndex + 1);
                }
                if (route == null) {
                    nextRootIndex = currentRootIndex.getRouteIndex(ROUTE_PARAMETER_WILDCARD);
                    if (nextRootIndex != null) {
                        route = findRoute(request, nextRootIndex, pathParts, pathIndex + 1);
                    }
                }
                if (route == null) {
                    nextRootIndex = currentRootIndex.getRouteIndex(ROUTE_GENERIC_PATH);
                    if (nextRootIndex != null) {
                        route = findRoute(request, nextRootIndex, pathParts, pathParts.length);
                    }
                }
            }
        }
        return route;
    }
}
