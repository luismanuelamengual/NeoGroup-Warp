package org.neogroup.warp;

import org.neogroup.warp.data.DataConnection;
import org.neogroup.warp.data.DataSources;
import org.neogroup.warp.http.Request;
import org.neogroup.warp.http.Response;

import java.util.HashMap;
import java.util.Map;

public class WarpContext {

    private static final Map<Long, WarpContext> contexts = new HashMap<>();

    private final long threadId;
    private final Request request;
    private final Response response;
    private DataConnection connection;
    private Map<String, DataConnection> connections;

    public WarpContext(Request request, Response response) {
        this.request = request;
        this.response = response;
        this.threadId = Thread.currentThread().getId();
        contexts.put(this.threadId, this);
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

    public DataConnection getConnection() {
        if (connection == null) {
            connection = DataSources.getConnection();
        }
        return connection;
    }

    public DataConnection getConnection(String sourceName) {
        if (connections == null) {
            connections = new HashMap<>();
        }
        DataConnection connection = connections.get(sourceName);
        if (connection == null) {
            connection = DataSources.getConnection(sourceName);
            connections.put(sourceName, connection);
        }
        return connection;
    }

    public void release () {
        if (contexts.remove(threadId) != null) {
            if (connection != null) {
                try { connection.close(); } catch (Exception ex) {}
                connection = null;
            }
            if (connections != null) {
                for (DataConnection connection : connections.values()) {
                    try { connection.close(); } catch (Exception ex) {}
                }
                connections.clear();
                connections = null;
            }
        }
    }

    public static WarpContext getCurrent() {
        return contexts.get(Thread.currentThread().getId());
    }
}
