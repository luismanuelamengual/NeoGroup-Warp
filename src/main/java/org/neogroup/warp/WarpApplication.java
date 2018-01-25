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
    private String webRootFolder;
    private String webRootContextPath;

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
     * @param port port of the web server
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get the web root folder
     * @return folder containing static files
     */
    public String getWebRootFolder() {
        return webRootFolder;
    }

    /**
     * Set the web root foler
     * @param webRootFolder folder containing static files
     */
    public void setWebRootFolder(String webRootFolder) {
        this.webRootFolder = webRootFolder;
    }

    /**
     * Get the web root context path. Defaults to "/"
     * @return the context path of the web root
     */
    public String getWebRootContextPath() {
        return webRootContextPath;
    }

    /**
     * Set the web root context path
     * @param webRootContextPath web root context path
     */
    public void setWebRootContextPath(String webRootContextPath) {
        this.webRootContextPath = webRootContextPath;
    }

    /**
     * Initialize the web application
     */
    public void start () {

        try {
            ServletHolder holder = new ServletHolder(WarpServlet.class);
            ServletHandler handler = new ServletHandler();
            handler.addServletWithMapping(holder, "/*");

            String webRootFolder = getWebRootFolder();
            if (webRootFolder == null) {
                webRootFolder = guessWebRootFolder();
            }

            String webRootContextPath = getWebRootContextPath();
            if (webRootContextPath == null) {
                webRootContextPath = "/";
            }

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase(webRootFolder);
            resourceHandler.setDirectoriesListed(true);
            ContextHandler contextHandler= new ContextHandler(webRootContextPath);
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
    private String guessWebRootFolder() {

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
