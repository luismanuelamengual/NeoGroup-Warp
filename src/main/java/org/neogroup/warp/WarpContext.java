package org.neogroup.warp;

import org.neogroup.warp.data.DataConnection;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class WarpContext {

    private final Request request;
    private final Response response;
    private Locale locale;
    private TimeZone timeZone;
    private Map<String, DataConnection> connections;

    public WarpContext(Request request, Response response) {
        this.request = request;
        this.response = response;
        locale = Locale.getDefault();
        timeZone = TimeZone.getDefault();
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
