package org.neogroup.warp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 */
public class WarpApplication {

    private int port;
    private String scanBasePackage;

    /**
     *
     */
    public WarpApplication () {

    }

    /**
     *
     * @param port
     */
    public WarpApplication(int port) {
        this.port = port;
    }

    /**
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     *
     * @return
     */
    public String getScanBasePackage() {
        return scanBasePackage;
    }

    /**
     *
     * @param scanBasePackage
     */
    public void setScanBasePackage(String scanBasePackage) {
        this.scanBasePackage = scanBasePackage;
    }

    /**
     *
     */
    public void start () {

        try {
            ServletHolder holder = new ServletHolder(WarpServlet.class);
            if (scanBasePackage != null) {
                holder.setInitParameter(WarpServlet.SCAN_BASE_PACKAGE_PARAMETER_NAME, scanBasePackage);
            }
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
