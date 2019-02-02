package org.neogroup.warp;

import org.neogroup.warp.data.DataSources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class WarpContext {

    private static final String DEFAULT_DATASOURCE_NAME = "default";

    private final Request request;
    private final Response response;
    private Map<String, Connection> connections;

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

    public Connection getConnection() {
        if (connections == null) {
            connections = new HashMap<>();
        }
        Connection connection = connections.get(DEFAULT_DATASOURCE_NAME);
        if (connection == null) {
            connection = DataSources.getConnection();
        }
        return connection;
    }

    public Connection getConnection(String sourceName) {
        if (connections == null) {
            connections = new HashMap<>();
        }
        Connection connection = connections.get(sourceName);
        if (connection == null) {
            connection = DataSources.getConnection(sourceName);
        }
        return connection;
    }

    public void release () {
        if (connections != null) {
            for (Connection connection : connections.values()) {
                try { connection.close(); } catch (Exception ex) {}
            }
            connections.clear();
            connections = null;
        }
    }
}
