package org.neogroup.warp;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private final HttpServletRequest request;
    private final Map<String,String> parameters;

    public Request (HttpServletRequest request) {
        this.request = request;
        parameters = new HashMap<>();
    }

    public String getPathInfo() {
        return request.getPathInfo();
    }

    public Cookie[] getCookies() {
        return request.getCookies();
    }

    public String getHeader(String s) {
        return request.getHeader(s);
    }

    public Enumeration<String> getHeaders(String s) {
        return request.getHeaders(s);
    }

    public Enumeration<String> getHeaderNames() {
        return request.getHeaderNames();
    }

    public String getMethod() {
        return request.getMethod();
    }

    public String getRequestURI() {
        return request.getRequestURI();
    }

    public StringBuffer getRequestURL() {
        return request.getRequestURL();
    }

    public HttpSession getSession(boolean b) {
        return request.getSession(b);
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    public String getContentType() {
        return request.getContentType();
    }

    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(String key) {

        String value = parameters.get(key);
        if (value == null) {
            value = request.getParameter(key);
        }
        return value;
    }

    public boolean hasParameter(String key) {
        return getParameter(key) != null;
    }

    public String getProtocol() {
        return request.getProtocol();
    }

    public String getScheme() {
        return request.getScheme();
    }
}
