package org.neogroup.warp;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Request {

    private final HttpServletRequest request;
    private final Map<String,String> parameters;

    /**
     *
     * @param request
     */
    public Request (HttpServletRequest request) {
        this.request = request;
        parameters = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public String getPathInfo() {
        return request.getPathInfo();
    }

    /**
     *
     * @return
     */
    public Cookie[] getCookies() {
        return request.getCookies();
    }

    /**
     *
     * @param s
     * @return
     */
    public String getHeader(String s) {
        return request.getHeader(s);
    }

    /**
     *
     * @param s
     * @return
     */
    public Enumeration<String> getHeaders(String s) {
        return request.getHeaders(s);
    }

    /**
     *
     * @return
     */
    public Enumeration<String> getHeaderNames() {
        return request.getHeaderNames();
    }

    /**
     *
     * @return
     */
    public String getMethod() {
        return request.getMethod();
    }

    /**
     *
     * @return
     */
    public String getRequestURI() {
        return request.getRequestURI();
    }

    /**
     *
     * @return
     */
    public StringBuffer getRequestURL() {
        return request.getRequestURL();
    }

    /**
     *
     * @param b
     * @return
     */
    public HttpSession getSession(boolean b) {
        return request.getSession(b);
    }

    /**
     *
     * @return
     */
    public HttpSession getSession() {
        return request.getSession();
    }

    /**
     *
     * @return
     */
    public String getContentType() {
        return request.getContentType();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    /**
     *
     * @param key
     * @param value
     */
    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    /**
     *
     * @param key
     * @return
     */
    public String getParameter(String key) {

        String value = parameters.get(key);
        if (value == null) {
            value = request.getParameter(key);
        }
        return value;
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean hasParameter(String key) {
        return getParameter(key) != null;
    }

    /**
     *
     * @return
     */
    public String getProtocol() {
        return request.getProtocol();
    }

    /**
     *
     * @return
     */
    public String getScheme() {
        return request.getScheme();
    }
}
