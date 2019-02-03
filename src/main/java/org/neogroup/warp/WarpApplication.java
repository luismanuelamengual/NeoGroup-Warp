package org.neogroup.warp;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.data.DataSourceComponent;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;
import org.neogroup.warp.utils.Scanner;
import org.neogroup.warp.views.ViewFactory;
import org.neogroup.warp.views.ViewFactoryComponent;

import javax.sql.DataSource;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.neogroup.warp.Warp.*;

public class WarpApplication {

    private static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "warp.properties";
    private static final String BASE_PACKAGE_PROPERTY = "org.neogroup.warp.basePackage";

    private int port;
    private String webRootFolder;
    private String webRootContextPath;

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

    public String getWebRootFolder() {
        return webRootFolder;
    }

    public void setWebRootFolder(String webRootFolder) {
        this.webRootFolder = webRootFolder;
    }

    public String getWebRootContextPath() {
        return webRootContextPath;
    }

    public void setWebRootContextPath(String webRootContextPath) {
        this.webRootContextPath = webRootContextPath;
    }

    public void start () {
        initializeComponents();
        initializeServer();
    }

    protected void initializeComponents() {
        getLogger().info("Initializing Warp Components ...");
        try {
            Warp.loadPropertiesFromResource(DEFAULT_PROPERTIES_RESOURCE_NAME);
        }
        catch (Exception ex) {
            getLogger().warn("Unable to load properties from resource \"" + DEFAULT_PROPERTIES_RESOURCE_NAME + "\" !!", ex);
        }

        String basePackage = getProperty(BASE_PACKAGE_PROPERTY);
        Scanner scanner = new Scanner();
        scanner.findClasses(cls -> {
            if ((basePackage == null || cls.getPackage().getName().startsWith(basePackage))) {
                try {

                    ControllerComponent controllerAnnotation = (ControllerComponent)cls.getAnnotation(ControllerComponent.class);
                    if (controllerAnnotation != null) {
                        registerController(cls);
                        return true;
                    }

                    ViewFactoryComponent viewFactoryComponent = (ViewFactoryComponent)cls.getAnnotation(ViewFactoryComponent.class);
                    if (viewFactoryComponent != null) {
                        if (ViewFactory.class.isAssignableFrom(cls)) {
                            registerViewFactory(cls);
                            return true;
                        }
                    }

                    ResourceComponent resourceComponent = (ResourceComponent)cls.getAnnotation(ResourceComponent.class);
                    if (resourceComponent != null) {
                        if (Resource.class.isAssignableFrom(cls)) {
                            registerResource(cls);
                            return true;
                        }
                    }

                    DataSourceComponent dataSourceComponent = (DataSourceComponent)cls.getAnnotation(DataSourceComponent.class);
                    if (dataSourceComponent != null) {
                        if (DataSource.class.isAssignableFrom(cls)) {
                            registerDataSource(cls);
                            return true;
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        });
        getLogger().info("Warp Components initialized !!");
    }

    protected void initializeServer() {
        getLogger().info("Initializing Warp Server [port:" + port + "] ...");
        Server server;
        try {
            ServletHolder holder = new ServletHolder(WarpServlet.class);
            ServletHandler handler = new ServletHandler();
            handler.addServletWithMapping(holder, "/*");

            String webRootFolder = getWebRootFolder();
            if (webRootFolder == null) {
                webRootFolder = System.getProperty("web.dir");
                if (webRootFolder == null) {
                    webRootFolder = guessWebRootFolder();
                }
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

            server = new Server(port);
            HandlerCollection handlerCollection = new HandlerCollection();
            handlerCollection.setHandlers(new Handler[] {contextHandler, handler});
            server.setHandler(handlerCollection);
            server.start();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error initializing warp server", ex);
        }
        getLogger().info("Warp Server [port:" + port + "] initialized !!");

        try { server.join(); } catch (Exception ex) {
            getLogger().error("Warp server error", ex);
        }
    }

    private Class getMainClass() {
        getLogger().info("Initializing Warp Server [port:" + port + "] ...");

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
