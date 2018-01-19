
package org.neogroup.warp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WarpServlet extends HttpServlet {

    public static final String SCAN_BASE_PACKAGE_PARAMETER_NAME = "scan_base_package";
    private static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "warp.properties";

    public WarpServlet() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        String propertiesResourceName = DEFAULT_PROPERTIES_RESOURCE_NAME;
        try {
            Warp.loadPropertiesFromResource(propertiesResourceName);
        }
        catch (Exception ex) {
            Warp.getLogger().warn("Unable to load properties from resource \"" + propertiesResourceName + "\" !!", ex);
        }
        String scanBasePackage = config.getInitParameter(SCAN_BASE_PACKAGE_PARAMETER_NAME);
        Warp.registerComponents(scanBasePackage);
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        Warp.handleRequest(servletRequest, servletResponse);
    }
}
