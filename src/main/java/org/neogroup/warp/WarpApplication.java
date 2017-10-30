package org.neogroup.warp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WarpApplication {

    private int port;
    private String scanBasePackage;

    public WarpApplication () {

    }

    public WarpApplication(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getScanBasePackage() {
        return scanBasePackage;
    }

    public void setScanBasePackage(String scanBasePackage) {
        this.scanBasePackage = scanBasePackage;
    }

    public void start () {

        try {
            Warp.initialize(scanBasePackage);

            ServletHolder holder = new ServletHolder(WarpServlet.class);
            ServletHandler handler = new ServletHandler();
            handler.addServletWithMapping(holder, "/*");
            Server server = new Server(port);
            server.setHandler(handler);
            server.start();
            server.join();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error initializing warp application", ex);
        }
    }
}
