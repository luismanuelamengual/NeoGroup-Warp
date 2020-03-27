package org.neogroup.warp;

import org.eclipse.jetty.http.HttpVersion;
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
import java.util.logging.Level;

import static org.neogroup.warp.Warp.*;

public class WarpApplication {

    private static final String SSL_KEYSTORE_NAME_PROPERTY = "ssl_keystore_name";
    private static final String SSL_KEYSTORE_PASSWORD_PROPERTY = "ssl_keystore_password";
    private static final String BASE_PACKAGE_PROPERTY = "base_package";

    private int port;
    private String webRootFolder;
    private String webRootContextPath;
    private boolean sslEnabled = false;

    public WarpApplication () {
        this(8080);
    }

    public WarpApplication (int port) {
        this(port, false);
    }

    public WarpApplication(int port, boolean sslEnabled) {
        this.port = port;
        this.sslEnabled = sslEnabled;
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
        getLogger().info("Initializing Warp Components ...");
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
        getLogger().info("Warp Components initialized !!");
    }

    protected void initializeServer() {
        getLogger().info("Initializing Warp Server [port:" + port + "] ...");
        Server server;
        try {
            server = new Server();
            server.addConnector(sslEnabled? createHTTPSUnifiedConnector(server, port) : createHTTPConnector(server, port));
            HandlerCollection handlers = new HandlerCollection();
            handlers.addHandler(createResourcesHandler());
            handlers.addHandler(createControllersHandler());
            server.setHandler(handlers);
            server.start();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error initializing warp server", ex);
        }
        getLogger().info("Warp Server [port:" + port + "] initialized !!");

        try { server.join(); } catch (Exception ex) {
            getLogger().log(Level.SEVERE,"Warp server error", ex);
        }
    }

    private ServerConnector createHTTPConnector(Server server, int port) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        return connector;
    }

    private ServerConnector createHTTPSConnector(Server server, int port) {
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
        ServerConnector sslConnector = new ServerConnector(server, createSSLConnectionFactory(), new HttpConnectionFactory(https));
        sslConnector.setPort(port);
        return sslConnector;
    }

    private ServerConnector createHTTPSUnifiedConnector(Server server, int port) {
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(port);
        SecureRequestCustomizer src = new SecureRequestCustomizer();
        httpConfig.addCustomizer(src);
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        SslConnectionFactory sslConnectionFactory = createSSLConnectionFactory();
        ServerConnector portUnified = new ServerConnector(server, new OptionalSslConnectionFactory(sslConnectionFactory, HttpVersion.HTTP_1_1.asString()), sslConnectionFactory, httpConnectionFactory);
        portUnified.setPort(port);
        return portUnified;
    }

    private SslConnectionFactory createSSLConnectionFactory () {
        if (!hasProperty(SSL_KEYSTORE_NAME_PROPERTY)) {
            throw new RuntimeException("Property \"" + SSL_KEYSTORE_NAME_PROPERTY + "\" is needed for ssl support");
        }
        if (!hasProperty(SSL_KEYSTORE_PASSWORD_PROPERTY)) {
            throw new RuntimeException("Property \"" + SSL_KEYSTORE_PASSWORD_PROPERTY + "\" is needed for ssl support");
        }
        SslContextFactory sslContextFactory = new SslContextFactory.Server();
        String keyStoreName = getProperty(SSL_KEYSTORE_NAME_PROPERTY);
        URL keyStoreResource = getClass().getClassLoader().getResource(keyStoreName);
        sslContextFactory.setKeyStorePath(keyStoreResource.toExternalForm());
        sslContextFactory.setKeyStorePassword(getProperty(SSL_KEYSTORE_PASSWORD_PROPERTY));
        return new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString());
    }

    private ContextHandler createResourcesHandler() {
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
        return contextHandler;
    }

    private ServletHandler createControllersHandler() {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(WarpServlet.class), "/*");
        return handler;
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
