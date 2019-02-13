package org.neogroup.warp;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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
     * Returns the parameter names in the request
     * @return set of parameter names
     */
    public Set<String> getParameterNames() {
        Enumeration<String> requestParameterNames = request.getParameterNames();
        Set<String> parameterNames = new HashSet<>();
        while (requestParameterNames.hasMoreElements()) {
            String parameterName = requestParameterNames.nextElement();
            parameterNames.add(parameterName);
        }
        parameterNames.addAll(parameters.keySet());
        return parameterNames;
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

    public String get(String key, String defaultValue) {
        String value = get(key);
        return value != null? value : defaultValue;
    }

    public <V> V get(String key, Class<? extends V> valueClass) {
        Object value = get(key);
        if (value != null) {
            if (!String.class.isAssignableFrom(valueClass)) {
                if (int.class.isAssignableFrom(valueClass) || Integer.class.isAssignableFrom(valueClass)) {
                    value = Integer.parseInt((String)value);
                } else if (float.class.isAssignableFrom(valueClass) || Float.class.isAssignableFrom(valueClass)) {
                    value = Float.parseFloat((String)value);
                } else if (double.class.isAssignableFrom(valueClass) || Double.class.isAssignableFrom(valueClass)) {
                    value = Double.parseDouble((String)value);
                } else if (boolean.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)) {
                    value = Boolean.parseBoolean((String)value);
                } else {
                    throw new RuntimeException("Parameter type \"" + valueClass.getName() + "\" not supported !!");
                }
            }
        }
        return (V)value;
    }

    public <V> V get(String key, Class<? extends V> valueClass, V defaultValue) {
        V value = get(key, valueClass);
        return value != null? value : defaultValue;
    }

    public Integer getInt(String key) {
        return get(key, Integer.class);
    }

    public int getInt(String key, int defaultValue) {
        return get(key, Integer.class, defaultValue);
    }

    public Double getDouble(String key) {
        return get(key, Double.class);
    }

    public double getDouble(String key, double defaultValue) {
        return get(key, Double.class, defaultValue);
    }

    public float getFloat(String key) {
        return get(key, float.class);
    }

    public float getFloat(String key, float defaultValue) {
        return get(key, Float.class, defaultValue);
    }

    public boolean getBoolean(String key) {
        return get(key, boolean.class);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return get(key, Boolean.class, defaultValue);
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
