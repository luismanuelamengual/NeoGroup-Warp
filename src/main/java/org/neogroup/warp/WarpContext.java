package org.neogroup.warp;

import org.neogroup.warp.data.DataConnection;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class WarpContext {

    private final Request request;
    private final Response response;
    private Map<String, DataConnection> connections;

    /**
     *
     * @param request
     * @param response
     */
    public WarpContext(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    /**
     *
     * @return
     */
    public Request getRequest() {
        return request;
    }

    /**
     *
     * @return
     */
    public Response getResponse() {
        return response;
    }

    /**
     *
     * @return
     */
    public Map<String, DataConnection> getConnections() {
        if (connections == null) {
            connections = new HashMap<>();
        }
        return connections;
    }

    /**
     *
     */
    public void release () {
        if (connections != null) {
            for (DataConnection connection : connections.values()) {
                connection.close();
            }
            connections.clear();
            connections = null;
        }
    }
}
