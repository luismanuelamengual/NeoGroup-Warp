package org.neogroup.warp.controllers.routing;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;

public interface AfterRoute extends AbstractRoute {

    public Object handle (Request request, Response response, Object routeResponse);
}
