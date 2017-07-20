
package org.neogroup.warp;

import org.neogroup.util.Scanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WarpServlet extends HttpServlet {

    public static final String SCAN_BASE_PACKAGE_PARAMETER_NAME = "scan_base_package";

    private List<Controller> controllers;

    @Override
    public void init(ServletConfig config) throws ServletException {

        controllers = new ArrayList<>();
        String scanBasePackage = config.getInitParameter(SCAN_BASE_PACKAGE_PARAMETER_NAME);
        Scanner scanner = new Scanner();
        scanner.findClasses(cls -> {
            if (cls.isAssignableFrom(Controller.class) && cls.getPackage().getName().startsWith(scanBasePackage)) {
                try {
                    Controller controller = (Controller)cls.newInstance();
                    controllers.add(controller);
                    System.out.println(cls);
                    return true;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }            }
            return false;
        });
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("URI: " + request.getPathInfo());
    }
}
