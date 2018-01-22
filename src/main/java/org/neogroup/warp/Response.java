package org.neogroup.warp;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 *
 */
public class Response {

    private final HttpServletResponse response;

    /**
     *
     * @param response
     */
    public Response (HttpServletResponse response) {
        this.response = response;
    }

    /**
     *
     * @param cookie
     */
    public void addCookie(Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     *
     * @param s
     * @return
     */
    public boolean containsHeader(String s) {
        return response.containsHeader(s);
    }

    /**
     *
     * @param s
     * @return
     */
    public String encodeURL(String s) {
        return response.encodeURL(s);
    }

    /**
     *
     * @param s
     * @return
     */
    public String encodeRedirectURL(String s) {
        return response.encodeRedirectURL(s);
    }

    /**
     *
     * @param s
     * @throws IOException
     */
    public void sendRedirect(String s) throws IOException {
        response.sendRedirect(s);
    }

    /**
     *
     * @param s
     * @param s1
     */
    public void setHeader(String s, String s1) {
        response.setHeader(s, s1);
    }

    /**
     *
     * @param s
     * @param s1
     */
    public void addHeader(String s, String s1) {
        response.addHeader(s, s1);
    }

    /**
     *
     * @param i
     */
    public void setStatus(int i) {
        response.setStatus(i);
    }

    /**
     *
     * @return
     */
    public int getStatus() {
        return response.getStatus();
    }

    /**
     *
     * @param s
     * @return
     */
    public String getHeader(String s) {
        return response.getHeader(s);
    }

    /**
     *
     * @param s
     * @return
     */
    public Collection<String> getHeaders(String s) {
        return response.getHeaders(s);
    }

    /**
     *
     * @return
     */
    public Collection<String> getHeaderNames() {
        return response.getHeaderNames();
    }

    /**
     *
     * @return
     */
    public String getContentType() {
        return response.getContentType();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    /**
     *
     * @param i
     */
    public void setContentLength(int i) {
        response.setContentLength(i);
    }

    /**
     *
     * @param s
     */
    public void setContentType(String s) {
        response.setContentType(s);
    }

    /**
     *
     * @throws IOException
     */
    public void flushBuffer() throws IOException {
        response.flushBuffer();
    }

    /**
     *
     * @return
     */
    public boolean isCommitted() {
        return response.isCommitted();
    }

    /**
     *
     */
    public void reset() {
        response.reset();
    }
}
