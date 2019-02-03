package org.neogroup.warp;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Class that represents an http response
 * @author Luis Manuel Amengual
 */
public class Response {

    private final HttpServletResponse response;
    private Object responseObject;

    /**
     * Constructor for the response
     * @param response servlet response
     */
    public Response (HttpServletResponse response) {
        this.response = response;
    }

    /**
     * Adds a new cookie to the response
     * @param cookie cookie
     */
    public Response addCookie(Cookie cookie) {
        response.addCookie(cookie);
        return this;
    }

    /**
     * Returns true if a header exists
     * @param name name of header
     * @return boolean
     */
    public boolean containsHeader(String name) {
        return response.containsHeader(name);
    }

    /**
     * Encodes a url
     * @param url url to encode
     * @return encoded url
     */
    public String encodeURL(String url) {
        return response.encodeURL(url);
    }

    /**
     * Encode a url
     * @param url url to encode
     * @return encoded url
     */
    public String encodeRedirectURL(String url) {
        return response.encodeRedirectURL(url);
    }

    /**
     * Sends a redirection
     * @param url url to redirect
     * @throws IOException io exception
     */
    public Response sendRedirect(String url) throws IOException {
        response.sendRedirect(url);
        return this;
    }

    /**
     * Set a header value
     * @param name name of header
     * @param value value of header
     */
    public Response setHeader(String name, String value) {
        response.setHeader(name, value);
        return this;
    }

    /**
     * Adds a new header
     * @param name name of header
     * @param value value of header
     */
    public Response addHeader(String name, String value) {
        response.addHeader(name, value);
        return this;
    }

    /**
     * Set status code
     * @param status status code
     */
    public Response setStatus(int status) {
        response.setStatus(status);
        return this;
    }

    /**
     * Returns the status code
     * @return status code
     */
    public int getStatus() {
        return response.getStatus();
    }

    /**
     * Returns a header value
     * @param name name of header
     * @return value of the header
     */
    public String getHeader(String name) {
        return response.getHeader(name);
    }

    /**
     * Return header values for a given header name
     * @param name name of header
     * @return collection of header values
     */
    public Collection<String> getHeaders(String name) {
        return response.getHeaders(name);
    }

    /**
     * Return the header names
     * @return collection of names
     */
    public Collection<String> getHeaderNames() {
        return response.getHeaderNames();
    }

    /**
     * Return the response content type
     * @return content type
     */
    public String getContentType() {
        return response.getContentType();
    }

    /**
     * Returns the output stream of the response
     * @return outputstream
     * @throws IOException io exception
     */
    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    /**
     * Returns the print writer for the response
     * @return print writer
     * @throws IOException io exception
     */
    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    /**
     * Flushes the buffer
     * @throws IOException io exception
     */
    public Response flushBuffer() throws IOException {
        response.flushBuffer();
        return this;
    }

    /**
     * Return true if the response is commited
     * @return boolean
     */
    public boolean isCommitted() {
        return response.isCommitted();
    }

    /**
     * Resets the response
     */
    public Response reset() {
        response.reset();
        return this;
    }

    public Response flush() {
        try {
            getWriter().flush();
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response close() {
        try {
            getWriter().close();
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response print(boolean b) {
        try {
            getWriter().print(b);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response print(char c) {
        try {
            getWriter().print(c);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response print(int i) {
        try {
            getWriter().print(i);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response print(long l) {
        try {
            getWriter().print(l);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response print(float v) {
        try {
            getWriter().print(v);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response print(double v) {
        try {
            getWriter().print(v);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response print(String s) {
        try {
            getWriter().print(s);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response print(Object o) {
        try {
            getWriter().print(o);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PrintWriter printf(String s, Object... objects) {
        try {
            return getWriter().printf(s, objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the content length of the response
     * @param length length
     */
    public Response setContentLength(int length) {
        response.setContentLength(length);
        return this;
    }

    /**
     * Set the content type of the response
     * @param contentType content type
     */
    public Response setContentType(String contentType) {
        response.setContentType(contentType);
        return this;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}
