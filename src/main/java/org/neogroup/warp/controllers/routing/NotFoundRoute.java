package org.neogroup.warp.controllers.routing;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;

public interface NotFoundRoute extends AbstractRoute {

    public Object handle (Request request, Response response);
}
