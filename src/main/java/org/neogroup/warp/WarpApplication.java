package org.neogroup.warp;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.data.DataSource;
import org.neogroup.warp.data.DataSourceComponent;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceComponent;
import org.neogroup.warp.utils.Scanner;
import org.neogroup.warp.views.ViewFactory;
import org.neogroup.warp.views.ViewFactoryComponent;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.neogroup.warp.Warp.*;

public class WarpApplication {

    private static final String BASE_PACKAGE_PROPERTY = "org.neogroup.warp.basePackage";

    private int port;
    private String webRootFolder;
    private String webRootContextPath;
    private boolean sslEnabled = false;

    public WarpApplication () {
    }

    public WarpApplication(int port) {
        this.port = port;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
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
        getLogger().log(System.Logger.Level.INFO,"Initializing Warp Components ...");
        String basePackage = getProperty(BASE_PACKAGE_PROPERTY);
        Scanner.findClasses(cls -> {
            if ((basePackage == null || cls.getPackage().getName().startsWith(basePackage))) {
                try {

                    ControllerComponent controllerAnnotation = (ControllerComponent)cls.getAnnotation(ControllerComponent.class);
                    if (controllerAnnotation != null) {
                        registerController(controllerAnnotation.value(), cls);
                        return true;
                    }

                    ViewFactoryComponent viewFactoryComponent = (ViewFactoryComponent)cls.getAnnotation(ViewFactoryComponent.class);
                    if (viewFactoryComponent != null) {
                        if (ViewFactory.class.isAssignableFrom(cls)) {
                            registerViewFactory(viewFactoryComponent.value(), cls);
                            return true;
                        }
                    }

                    ResourceComponent resourceComponent = (ResourceComponent)cls.getAnnotation(ResourceComponent.class);
                    if (resourceComponent != null) {
                        if (Resource.class.isAssignableFrom(cls)) {
                            registerResource(resourceComponent.value(), cls);
                            return true;
                        }
                    }

                    DataSourceComponent dataSourceComponent = (DataSourceComponent)cls.getAnnotation(DataSourceComponent.class);
                    if (dataSourceComponent != null) {
                        if (DataSource.class.isAssignableFrom(cls)) {
                            registerDataSource(dataSourceComponent.value(), cls);
                            return true;
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        });
        getLogger().log(System.Logger.Level.INFO,"Warp Components initialized !!");
    }

    protected void initializeServer() {
        getLogger().log(System.Logger.Level.INFO,"Initializing Warp Server [port:" + port + "] ...");
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

            server = new Server();

            if (!sslEnabled) {
                ServerConnector connector = new ServerConnector(server);
                connector.setPort(port);
                server.setConnectors(new Connector[] { connector });
            } else {
                HttpConfiguration https = new HttpConfiguration();
                https.addCustomizer(new SecureRequestCustomizer());
                SslContextFactory sslContextFactory = new SslContextFactory.Server();
                String keyStoreName = Warp.getProperty("ssl_keystore_name");
                URL keyStoreResource = getClass().getClassLoader().getResource(keyStoreName);
                sslContextFactory.setKeyStorePath(keyStoreResource.toExternalForm());
                sslContextFactory.setKeyStorePassword(Warp.getProperty("ssl_keystore_password"));
                ServerConnector sslConnector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https));
                sslConnector.setPort(port);
                server.setConnectors(new Connector[] { sslConnector });
            }

            HandlerCollection handlerCollection = new HandlerCollection();
            handlerCollection.setHandlers(new Handler[] {contextHandler, handler});
            server.setHandler(handlerCollection);
            server.start();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error initializing warp server", ex);
        }
        getLogger().log(System.Logger.Level.INFO,"Warp Server [port:" + port + "] initialized !!");

        try { server.join(); } catch (Exception ex) {
            getLogger().log(System.Logger.Level.ERROR,"Warp server error", ex);
        }
    }

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

    private String guessWebRootFolder() {
        URL location = getMainClass().getProtectionDomain().getCodeSource().getLocation();
        File locationFile = new File(location.getFile());
        Path webappPath = Paths.get (locationFile.getPath());
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
