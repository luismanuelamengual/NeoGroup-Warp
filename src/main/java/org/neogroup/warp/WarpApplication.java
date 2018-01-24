package org.neogroup.warp;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Warp application for a standalone web application execution
 * @author Luis Manuel Amengual
 */
public class WarpApplication {

    private int port;
    private String scanBasePackage;

    /**
     * Default constructor for the web application
     */
    public WarpApplication () {
    }

    /**
     * Constructor for a web application with a port
     * @param port port to listen
     */
    public WarpApplication(int port) {
        this.port = port;
    }

    /**
     * Get the port for the web application
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the port for the web application
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get the base scan package to find components
     * @return base scan package
     */
    public String getScanBasePackage() {
        return scanBasePackage;
    }

    /**
     * Set the base scan package to find components
     * @param scanBasePackage base scan package
     */
    public void setScanBasePackage(String scanBasePackage) {
        this.scanBasePackage = scanBasePackage;
    }

    /**
     * Initialize the web application
     */
    public void start () {

        try {
            ServletHolder holder = new ServletHolder(WarpServlet.class);
            if (scanBasePackage != null) {
                holder.setInitParameter(WarpServlet.SCAN_BASE_PACKAGE_PARAMETER_NAME, scanBasePackage);
            }
            ServletHandler handler = new ServletHandler();
            handler.addServletWithMapping(holder, "/*");

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase(getWebappFolder());
            resourceHandler.setDirectoriesListed(true);
            ContextHandler contextHandler= new ContextHandler("/resources");
            contextHandler.setHandler(resourceHandler);

            Server server = new Server(port);
            HandlerCollection handlerCollection = new HandlerCollection();
            handlerCollection.setHandlers(new Handler[] {contextHandler, handler});
            server.setHandler(handlerCollection);
            server.start();
            server.join();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error initializing warp application", ex);
        }
    }

    /**
     * Get the main class for the web application
     * @return main class
     */
    private Class getMainClass() {

        Class clazz = null;
        StackTraceElement trace[] = Thread.currentThread().getStackTrace();
        if (trace.length > 0) {
            String className = trace[trace.length - 1].getClassName();
            try {
                clazz = Class.forName(className);
            }
            catch (Exception ex) {
                throw new RuntimeException("Could not get class for \"" + className + "\"");
            }
        }
        if (clazz == null) {
            throw new RuntimeException("Cannot determine main class.");
        }
        return clazz;
    }

    /**
     * Guess the webapp folder for the web application
     * @return guessed webapp folder for serving static files
     */
    private String getWebappFolder() {

        URL location = getMainClass().getProtectionDomain().getCodeSource().getLocation();
        Path webappPath = Paths.get (location.getFile());
        String baseFolderName = webappPath.getFileName().toString();
        if (baseFolderName.equals("classes")) {
            webappPath = webappPath.getParent().getParent().resolve("src").resolve("main").resolve("webapp");
        }
        else if (baseFolderName.equals("test-classes")) {
            webappPath = webappPath.getParent().getParent().resolve("src").resolve("test").resolve("webapp");
        }
        else {
            webappPath = Paths.get("./webapp");
        }
        return webappPath.toString();
    }
}
