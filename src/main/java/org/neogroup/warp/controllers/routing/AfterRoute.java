package org.neogroup.warp.controllers.routing;

import org.neogroup.warp.controllers.Request;
import org.neogroup.warp.controllers.Response;

public interface AfterRoute extends AbstractRoute {

    public Object handle (Request request, Response response, Object routeResponse);
}
