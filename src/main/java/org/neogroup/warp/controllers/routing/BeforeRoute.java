package org.neogroup.warp.controllers.routing;

import org.neogroup.warp.Request;
import org.neogroup.warp.Response;

public interface BeforeRoute extends AbstractRoute {

    public boolean handle(Request request, Response response);
}
