package org.neogroup.warp;

import org.neogroup.warp.data.DataConnection;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class WarpContext {

    private final Request request;
    private final Response response;
    private Map<String, DataConnection> connections;

    public WarpContext(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

    public Map<String, DataConnection> getConnections() {
        if (connections == null) {
            connections = new HashMap<>();
        }
        return connections;
    }

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
