package org.neogroup.warp;

import org.neogroup.warp.data.DataConnection;
import org.neogroup.warp.data.DataSources;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class WarpContext {

    private final Request request;
    private final Response response;
    private Locale locale;
    private TimeZone timeZone;
    private DataConnection connection;
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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public DataConnection getConnection() {
        if (connection == null) {
            connection = DataSources.getConnection();
        }
        return connection;
    }

    public DataConnection getConnection(String sourceName) {
        DataConnection connection = connections.get(sourceName);
        if (connection == null) {
            connection = DataSources.getConnection(sourceName);
            connections.put(sourceName, connection);
        }
        return connection;
    }

    public void release () {
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
        locale = null;
        timeZone = null;
    }
}
