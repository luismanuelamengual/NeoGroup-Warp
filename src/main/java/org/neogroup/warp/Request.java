package org.neogroup.warp;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Class that wraps a servlet request
 */
public class Request {

    private static final String X_WWW_FORM_URLENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String X_WWW_FORM_URLENCODED_PARTS_SEPARATOR = "\\&";
    private static final String X_WWW_FORM_URLENCODED_NAME_VALUE_SEPARATOR = "=";
    private static final String X_WWW_FORM_URLENCODED_CHARSET = "UTF-8";

    private static final String MULTIPART_FORM_DATA_CONTENT_TYPE = "multipart/form-data";
    private static final String MULTIPART_FORM_DATA_CONTENT_DISPOSITION_HEADER = "Content-Disposition";

    private final HttpServletRequest request;
    private Map<String,Object> extraParameters;

    /**
     * Servlet Request
     * @param request servlet request
     */
    public Request (HttpServletRequest request) {
        this.request = request;
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
    public ServletInputStream getBodyInputStream() {
        try {
            return request.getInputStream();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the body in bytess
     * @return
     */
    public byte[] getBodyBytes() {
        try {
            return request.getInputStream().readAllBytes();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the body of the request
     * @return String body of the request
     * @throws IOException
     */
    public String getBody() {
        return new String(this.getBodyBytes());
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
        parameterNames.addAll(extraParameters.keySet());
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
        getExtraParameters().put(key, value);
    }

    /**
     * Returns the value of a parameter
     * @param key key of parameter
     * @return value of parameter
     */
    public <V> V get(String key) {
        Object value = getExtraParameters().get(key);
        if (value == null) {
            value = request.getParameter(key);
        }
        return (V)value;
    }

    private Map<String,Object> getExtraParameters() {
        if (extraParameters == null) {
            extraParameters = new HashMap<>();
            String contentType = getContentType().trim();
            if (contentType.equals(X_WWW_FORM_URLENCODED_CONTENT_TYPE)) {
                String content = getBody();
                String[] pairs = content.split(X_WWW_FORM_URLENCODED_PARTS_SEPARATOR);
                for (String pair : pairs) {
                    try {
                        String[] fields = pair.split(X_WWW_FORM_URLENCODED_NAME_VALUE_SEPARATOR);
                        String name = URLDecoder.decode(fields[0], X_WWW_FORM_URLENCODED_CHARSET);
                        String value = URLDecoder.decode(fields[1], X_WWW_FORM_URLENCODED_CHARSET);
                        extraParameters.put(name, value);
                    } catch (Exception ex) {}
                }
            }
            else if (contentType.contains(MULTIPART_FORM_DATA_CONTENT_TYPE)) {
                String boundary = contentType.substring(contentType.lastIndexOf("=")+1);
                MultipartItemsReader reader = new MultipartItemsReader(getBodyBytes(), boundary.getBytes());
                List<MultipartItem> items = reader.readItems();
                for(MultipartItem item : items) {
                    String dispositionHeader = item.getHeader(MULTIPART_FORM_DATA_CONTENT_DISPOSITION_HEADER);
                    if (dispositionHeader != null) {
                        String[] dispositionHeaderTokens = dispositionHeader.split(";");
                        if (dispositionHeaderTokens[0].equals("form-data")) {
                            for (int i = 1; i < dispositionHeaderTokens.length; i++) {
                                String dispositionToken = dispositionHeaderTokens[i].trim();
                                String[] dispositionTokenParts = dispositionToken.split("=");
                                String dispositionTokenProperty = dispositionTokenParts[0].trim();
                                if (dispositionTokenProperty.equals("name")) {
                                    String parameterName = dispositionTokenParts[1].trim();
                                    if (parameterName.startsWith("\"") && parameterName.endsWith("\"")) {
                                        parameterName = parameterName.substring(1, parameterName.length()-1);
                                    }
                                    extraParameters.put(parameterName, item);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return extraParameters;
    }

    public <V> V get(String key, V defaultValue) {
        String value = get(key);
        return value != null? (V)value : defaultValue;
    }

    public <V> V get(String key, Class<? extends V> valueClass) {
        Object value = get(key);
        if (value != null && !valueClass.isAssignableFrom(value.getClass())) {
            if (String.class.isAssignableFrom(valueClass)) {
                if (value instanceof MultipartItem) {
                    value = new String(((MultipartItem) value).getContent());
                } else {
                    value = value.toString();
                }
            } else if (byte[].class.isAssignableFrom(valueClass)) {
                if (value instanceof MultipartItem) {
                    value = ((MultipartItem) value).getContent();
                } else {
                    value = value.toString().getBytes();
                }
            } else if (int.class.isAssignableFrom(valueClass) || Integer.class.isAssignableFrom(valueClass)) {
                value = Integer.parseInt((String) value);
            } else if (float.class.isAssignableFrom(valueClass) || Float.class.isAssignableFrom(valueClass)) {
                value = Float.parseFloat((String) value);
            } else if (double.class.isAssignableFrom(valueClass) || Double.class.isAssignableFrom(valueClass)) {
                value = Double.parseDouble((String) value);
            } else if (boolean.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)) {
                value = Boolean.parseBoolean((String) value);
            } else {
                throw new RuntimeException("Parameter type \"" + valueClass.getName() + "\" not supported !!");
            }
        }
        return (V)value;
    }

    public <V> V get(String key, Class<? extends V> valueClass, V defaultValue) {
        V value = get(key, valueClass);
        return value != null? value : defaultValue;
    }

    public String getString(String key) {
        return get(key, String.class);
    }

    public String getString(String key, String defaultValue) {
        return get(key, String.class, defaultValue);
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
