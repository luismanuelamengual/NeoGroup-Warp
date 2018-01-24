
package org.neogroup.warp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that is used for web application servers like tomcat
 */
public class WarpServlet extends HttpServlet {

    /**
     * Default constructor for the warp servlet
     */
    public WarpServlet() {
    }

    /**
     * Initialization for the servlet
     * @param config default servlet configuration
     * @throws ServletException servlet exception
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        Warp.init();
    }

    /**
     * Method that is called for each request
     * @param servletRequest request
     * @param servletResponse response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        Warp.handleRequest(servletRequest, servletResponse);
    }
}
