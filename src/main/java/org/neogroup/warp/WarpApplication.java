package org.neogroup.warp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class WarpApplication {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(WarpServlet.class, "/*");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
