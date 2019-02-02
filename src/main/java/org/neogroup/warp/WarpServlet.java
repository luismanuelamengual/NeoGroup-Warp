
package org.neogroup.warp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.neogroup.warp.Warp.handleRequest;

public class WarpServlet extends HttpServlet {

    public WarpServlet() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        handleRequest(servletRequest, servletResponse);
    }
}
