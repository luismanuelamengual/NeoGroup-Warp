package org.neogroup.warp.controllers.routing;

import org.neogroup.warp.controllers.Request;
import org.neogroup.warp.controllers.Response;

public interface NotFoundRoute extends AbstractRoute {

    public Object handle (Request request, Response response);
}
