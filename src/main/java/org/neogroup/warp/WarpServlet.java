
package org.neogroup.warp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WarpServlet extends HttpServlet {

    public static final String SCAN_BASE_PACKAGE_PARAMETER_NAME = "scan_base_package";

    public WarpServlet() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        Warp.initialize(config.getInitParameter(SCAN_BASE_PACKAGE_PARAMETER_NAME));
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {

        Warp.processServletRequest(servletRequest, servletResponse);
    }
}
