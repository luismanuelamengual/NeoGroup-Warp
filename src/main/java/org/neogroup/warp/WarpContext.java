package org.neogroup.warp;

import org.neogroup.warp.data.DataConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * Context of an http request
 * @author Luis Manuel Amengual
 */
public class WarpContext {

    private final Request request;
    private final Response response;
    private Map<String, DataConnection> connections;

    /**
     * Constructor of the context with a request and a response
     * @param request request
     * @param response response
     */
    public WarpContext(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Returns the request
     * @return request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Returns the response
     * @return response
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Returns the connections used in the http exchange
     * @return connections
     */
    public Map<String, DataConnection> getConnections() {
        if (connections == null) {
            connections = new HashMap<>();
        }
        return connections;
    }

    /**
     * Release the context
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
