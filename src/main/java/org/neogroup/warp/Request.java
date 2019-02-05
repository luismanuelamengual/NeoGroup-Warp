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
 * Class that wraps a servlet request
 */
public class Request {

    private final HttpServletRequest request;
    private Map<String,String> parameters;

    /**
     * Servlet Request
     * @param request servlet request
     */
    public Request (HttpServletRequest request) {
        this.request = request;
        this.parameters = new HashMap<>();
    }

    /**
     * Returns the path info
     * @return path info for the request
     */
    public String getPathInfo() {
        return request.getPathInfo();
    }

    /**
     * Returns cookies in the request
     * @return cookies
     */
    public Cookie[] getCookies() {
        return request.getCookies();
    }

    /**
     * Get the header from a header name
     * @param name Name of the header
     * @return value of the header
     */
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    /**
     * Returns the headers with a given name
     * @param name Name of the header
     * @return headers
     */
    public Enumeration<String> getHeaders(String name) {
        return request.getHeaders(name);
    }

    /**
     * Returns the header names
     * @return header names
     */
    public Enumeration<String> getHeaderNames() {
        return request.getHeaderNames();
    }

    /**
     * Returns the method
     * @return method
     */
    public String getMethod() {
        return request.getMethod();
    }

    /**
     * Returns the URI of the request
     * @return URI of request
     */
    public String getRequestURI() {
        return request.getRequestURI();
    }

    /**
     * Returns the URL of the request
     * @return URL of request
     */
    public StringBuffer getRequestURL() {
        return request.getRequestURL();
    }

    /**
     * Return the session of the request
     * @param create True if a new session is required
     * @return session of request
     */
    public HttpSession getSession(boolean create) {
        return request.getSession(create);
    }

    /**
     * Return the session of the request
     * @return session of request
     */
    public HttpSession getSession() {
        return request.getSession();
    }

    /**
     * Return the content type
     * @return content type
     */
    public String getContentType() {
        return request.getContentType();
    }

    /**
     * Return the input stream
     * @return input stream
     * @throws IOException io exception
     */
    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    /**
     * Set a parameter for the request
     * @param key key of parameter
     * @param value value of parameter
     */
    public void set(String key, String value) {
        parameters.put(key, value);
    }

    /**
     * Returns the value of a parameter
     * @param key key of parameter
     * @return value of parameter
     */
    public String get(String key) {

        String value = parameters.get(key);
        if (value == null) {
            value = request.getParameter(key);
        }
        return value;
    }

    /**
     * Returns true if the parameter key exists
     * @param key parameter key
     * @return boolean
     */
    public boolean has(String key) {
        return get(key) != null;
    }

    /**
     * Returns the request protocol
     * @return protocol
     */
    public String getProtocol() {
        return request.getProtocol();
    }

    /**
     * Returns the scheme of the request
     * @return scheme
     */
    public String getScheme() {
        return request.getScheme();
    }
}
