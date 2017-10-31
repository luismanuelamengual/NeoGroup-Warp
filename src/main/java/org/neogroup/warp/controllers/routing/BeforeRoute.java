package org.neogroup.warp.controllers.routing;

import org.neogroup.warp.controllers.Request;
import org.neogroup.warp.controllers.Response;

public interface BeforeRoute extends AbstractRoute {

    public boolean handle(Request request, Response response);
}
