package org.neogroup.warp.http;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Class that wraps a servlet request
 */
public class Request {

    private static final String X_WWW_FORM_URLENCODED_PARTS_SEPARATOR = "\\&";
    private static final String X_WWW_FORM_URLENCODED_NAME_VALUE_SEPARATOR = "=";
    private static final String X_WWW_FORM_URLENCODED_CHARSET = "UTF-8";

    private static final String SERVER_ADDRESS_PORT_SEPARATOR = ":";
    private static final String SERVER_ADDRESS_SCHEME_SEPARATOR = "://";

    private static final String MULTIPART_FORM_DATA_CONTENT_DISPOSITION_HEADER_SEPARATOR = ";";
    private static final String MULTIPART_FORM_DATA_CONTENT_DISPOSITION_TYPE = "form-data";
    private static final String MULTIPART_FORM_DATA_CONTENT_DISPOSITION_NAME_PROPERTY = "name";
    private static final String MULTIPART_FORM_DATA_CONTENT_DISPOSITION_PROPERTY_QUOTES = "\"";
    private static final String MULTIPART_FORM_DATA_KEY_VALUE_SEPARATOR = "=";

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
     * Returns the remote Address
     * @return The remote address
     */
    public String getRemoteAddress() {
        return request.getRemoteAddr();
    }

    /**
     * Returns the remote host
     * @return The remote host
     */
    public String getRemoteHost() {
        return request.getRemoteHost();
    }

    /**
     * Returns the remote port
     * @return The remote port
     */
    public int getRemotePort() {
        return request.getRemotePort();
    }

    /**
     * Returns the URI of the request
     * @return URI of request
     */
    public String getUri() {
        return request.getRequestURI();
    }

    /**
     * Returns the URL of the request
     * @return URL of request
     */
    public String getUrl() {
        return request.getRequestURL().toString();
    }

    /**
     * Return the query string of the request
     * @return String query string
     */
    public String getQueryString() {
        return request.getQueryString();
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

    /**
     * Returns the path info
     * @return path info for the request
     */
    public String getPath() {
        return request.getPathInfo();
    }

    /**
     * Returns the server address
     * @return String address
     */
    public String getServerAddress() {
        String url = getUrl();
        String path = getPath();
        return url.substring(0, url.length() - path.length());
    }

    /**
     * Returns the server host
     * @return host
     */
    public String getServerHost() {
        String serverHost = getServerAddress();
        int protocolIndex = serverHost.indexOf(SERVER_ADDRESS_SCHEME_SEPARATOR);
        if (protocolIndex >= 0) {
            serverHost = serverHost.substring(protocolIndex + SERVER_ADDRESS_SCHEME_SEPARATOR.length());
        }
        int portSeparatorIndex = serverHost.indexOf(SERVER_ADDRESS_PORT_SEPARATOR);
        if (portSeparatorIndex >= 0) {
            serverHost = serverHost.substring(0, portSeparatorIndex);
        }
        return serverHost;
    }

    /**
     * Returns the server port
     * @return port
     */
    public int getServerPort() {
        return request.getServerPort();
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
     */
    public ServletInputStream getBodyInputStream() {
        try {
            return request.getInputStream();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @return bytes of the content
     */
    public byte[] getBodyBytes() {
        try {
            return request.getInputStream().readAllBytes();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the body of the request
     * @return String body of the request
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
        parameterNames.addAll(getExtraParameters().keySet());
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
    public void set(String key, Object value) {
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
            String contentType = getContentType();
            if (contentType != null) {
                contentType = contentType.trim();
                if (contentType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
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
                else if (contentType.contains(MediaType.MULTIPART_FORM_DATA)) {
                    String boundary = contentType.substring(contentType.lastIndexOf(MULTIPART_FORM_DATA_KEY_VALUE_SEPARATOR)+1);
                    MultipartItemsReader reader = new MultipartItemsReader(getBodyBytes(), boundary.getBytes());
                    List<MultipartItem> items = reader.readItems();
                    for(MultipartItem item : items) {
                        String dispositionHeader = item.getHeader(Header.CONTENT_DISPOSITION);
                        if (dispositionHeader != null) {
                            String[] dispositionHeaderTokens = dispositionHeader.split(MULTIPART_FORM_DATA_CONTENT_DISPOSITION_HEADER_SEPARATOR);
                            if (dispositionHeaderTokens[0].equals(MULTIPART_FORM_DATA_CONTENT_DISPOSITION_TYPE)) {
                                for (int i = 1; i < dispositionHeaderTokens.length; i++) {
                                    String dispositionToken = dispositionHeaderTokens[i].trim();
                                    String[] dispositionTokenParts = dispositionToken.split(MULTIPART_FORM_DATA_KEY_VALUE_SEPARATOR);
                                    String dispositionTokenProperty = dispositionTokenParts[0].trim();
                                    if (dispositionTokenProperty.equals(MULTIPART_FORM_DATA_CONTENT_DISPOSITION_NAME_PROPERTY)) {
                                        String parameterName = dispositionTokenParts[1].trim();
                                        if (parameterName.startsWith(MULTIPART_FORM_DATA_CONTENT_DISPOSITION_PROPERTY_QUOTES) && parameterName.endsWith(MULTIPART_FORM_DATA_CONTENT_DISPOSITION_PROPERTY_QUOTES)) {
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
                if (value instanceof MultipartItem) {
                    value = new String(((MultipartItem) value).getContent());
                }
                value = Integer.parseInt((String) value);
            } else if (float.class.isAssignableFrom(valueClass) || Float.class.isAssignableFrom(valueClass)) {
                if (value instanceof MultipartItem) {
                    value = new String(((MultipartItem) value).getContent());
                }
                value = Float.parseFloat((String) value);
            } else if (double.class.isAssignableFrom(valueClass) || Double.class.isAssignableFrom(valueClass)) {
                if (value instanceof MultipartItem) {
                    value = new String(((MultipartItem) value).getContent());
                }
                value = Double.parseDouble((String) value);
            } else if (boolean.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)) {
                if (value instanceof MultipartItem) {
                    value = new String(((MultipartItem) value).getContent());
                }
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
}
