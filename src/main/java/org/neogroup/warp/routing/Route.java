package org.neogroup.warp.routing;

import org.neogroup.warp.*;

public interface Route {

    public Object handleRequest (org.neogroup.warp.Request request, Response response);
}
