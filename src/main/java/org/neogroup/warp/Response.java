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
    public void addCookie(Cookie cookie) {
        response.addCookie(cookie);
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
    public void sendRedirect(String url) throws IOException {
        response.sendRedirect(url);
    }

    /**
     * Set a header value
     * @param name name of header
     * @param value value of header
     */
    public void setHeader(String name, String value) {
        response.setHeader(name, value);
    }

    /**
     * Adds a new header
     * @param name name of header
     * @param value value of header
     */
    public void addHeader(String name, String value) {
        response.addHeader(name, value);
    }

    /**
     * Set status code
     * @param status status code
     */
    public void setStatus(int status) {
        response.setStatus(status);
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
     * Sets the content length of the response
     * @param length length
     */
    public void setContentLength(int length) {
        response.setContentLength(length);
    }

    /**
     * Set the content type of the response
     * @param contentType content type
     */
    public void setContentType(String contentType) {
        response.setContentType(contentType);
    }

    /**
     * Flushes the buffer
     * @throws IOException io exception
     */
    public void flushBuffer() throws IOException {
        response.flushBuffer();
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
    public void reset() {
        response.reset();
    }
}
