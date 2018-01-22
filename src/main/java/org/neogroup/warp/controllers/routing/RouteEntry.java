
package org.neogroup.warp.controllers.routing;

/**
 *
 */
public class RouteEntry {

    private final String method;
    private final String path;
    private final AbstractRoute route;

    /**
     *
     * @param method
     * @param path
     * @param route
     */
    public RouteEntry(String method, String path, AbstractRoute route) {
        this.method = method;
        this.path = path;
        this.route = route;
    }

    /**
     *
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @return
     */
    public AbstractRoute getRoute() {
        return route;
    }
}
