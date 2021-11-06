
package org.neogroup.warp;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.neogroup.warp.controllers.Controllers;
import org.neogroup.warp.http.Request;
import org.neogroup.warp.http.Response;

import java.io.IOException;

import static org.neogroup.warp.Warp.getProperty;

public class WarpServlet extends HttpServlet {

    private static final String CORS_ENABLED_PROPERTY_NAME = "cors_enabled";
    private static final String CORS_ORIGINS_ALLOWED_PROPERTY_NAME = "cors_origins_allowed";
    private static final String CORS_METHODS_ALLOWED_PROPERTY_NAME = "cors_methods_allowed";
    private static final String CORS_HEADERS_ALLOWED_PROPERTY_NAME = "cors_headers_allowed";

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_METHODS_HEADER = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER = "Access-Control-Allow-Headers";

    private static final String OPTIONS_METHOD = "OPTIONS";

    private static final String TRUE_VALUE = "true";
    private static final String FALSE_VALUE = "false";
    private static final String WILDCARD_VALUE = "*";

    @Override
    public void init(ServletConfig config) {}

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        if (getProperty(CORS_ENABLED_PROPERTY_NAME, FALSE_VALUE).equalsIgnoreCase(TRUE_VALUE)) {
            servletResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER , getProperty(CORS_ORIGINS_ALLOWED_PROPERTY_NAME, WILDCARD_VALUE));
            if (servletRequest.getMethod().equalsIgnoreCase(OPTIONS_METHOD)) {
                servletResponse.setHeader(ACCESS_CONTROL_ALLOW_METHODS_HEADER, getProperty(CORS_METHODS_ALLOWED_PROPERTY_NAME, WILDCARD_VALUE));
                servletResponse.setHeader(ACCESS_CONTROL_ALLOW_HEADERS_HEADER, getProperty(CORS_HEADERS_ALLOWED_PROPERTY_NAME, WILDCARD_VALUE));
                servletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                handleRequest(servletRequest, servletResponse);
            }
        } else {
            handleRequest(servletRequest, servletResponse);
        }
    }

    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        Request request = new Request(servletRequest);
        Response response = new Response(servletResponse);
        WarpContext context = new WarpContext(request, response);
        try { Controllers.handle(context); } catch (Exception ex) {}
        try { context.release(); } catch (Exception ex) {}
    }
}
