package org.neogroup.warp;

import org.neogroup.util.Scanner;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.Controllers;
import org.neogroup.warp.data.DataConnection;
import org.neogroup.warp.data.DataSource;
import org.neogroup.warp.data.DataSourceComponent;
import org.neogroup.warp.data.DataSources;
import org.neogroup.warp.models.ModelManager;
import org.neogroup.warp.models.ModelManagerComponent;
import org.neogroup.warp.models.ModelQuery;
import org.neogroup.warp.models.Models;
import org.neogroup.warp.views.View;
import org.neogroup.warp.views.ViewFactory;
import org.neogroup.warp.views.ViewFactoryComponent;
import org.neogroup.warp.views.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

/**
 * Warp instance with the base framework functionality
 * @author Luis Manuel Amengual
 */
public class WarpInstance {

    private static final String DEFAULT_LOGGER_NAME = "Warp";
    private static final String DEFAULT_PROPERTIES_RESOURCE_NAME = "warp.properties";
    private static final String BASE_PACKAGE_PROPERTY = "org.neogroup.warp.basePackage";

    private final Properties properties;
    private final Controllers controllers;
    private final Models models;
    private final Views views;
    private final DataSources dataSources;
    private final Map<Long, WarpContext> contexts;

    /**
     * Base constructor for the warp instance
     */
    protected WarpInstance () {

        this.properties = new Properties();
        this.controllers = new Controllers(this);
        this.models = new Models(this);
        this.views = new Views(this);
        this.dataSources = new DataSources(this);
        this.contexts = new HashMap<>();
    }

    /**
     * Initialize the warp instance
     */
    public void init() {

        getLogger().info("Initializing Warp ...");
        try {
            Warp.loadPropertiesFromResource(DEFAULT_PROPERTIES_RESOURCE_NAME);
        }
        catch (Exception ex) {
            Warp.getLogger().warn("Unable to load properties from resource \"" + DEFAULT_PROPERTIES_RESOURCE_NAME + "\" !!", ex);
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

                    ModelManagerComponent managerAnnotation = (ModelManagerComponent)cls.getAnnotation(ModelManagerComponent.class);
                    if (managerAnnotation != null) {
                        if (ModelManager.class.isAssignableFrom(cls)) {
                            registerModelManager(cls);
                            return true;
                        }
                    }

                    ViewFactoryComponent viewFactoryComponent = (ViewFactoryComponent)cls.getAnnotation(ViewFactoryComponent.class);
                    if (viewFactoryComponent != null) {
                        if (ViewFactory.class.isAssignableFrom(cls)) {
                            registerViewFactory(cls);
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
        getLogger().info("Warp initialized !!");
    }

    /**
     * Registers a controller class. Basically registers
     * all the routes that resides in the controller
     * @param controllerClass Controller class
     */
    public void registerController(Class controllerClass) {
        controllers.registerController(controllerClass);
    }

    /**
     * Registers a model manager
     * @param modelManagerClass model manager class
     */
    public void registerModelManager(Class<? extends ModelManager> modelManagerClass) {
        models.registerModelManager(modelManagerClass);
    }

    /**
     * Registers a view factory
     * @param viewFactoryClass view factory class
     */
    public void registerViewFactory(Class<? extends ViewFactory> viewFactoryClass) {
        views.registerViewFactory(viewFactoryClass);
    }

    /**
     * Registers a new data source
     * @param dataSourceClass data source class
     */
    public void registerDataSource(Class<? extends DataSource> dataSourceClass) {
        dataSources.registerDataSource(dataSourceClass);
    }

    /**
     * Get the properties of the context
     * @return Properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Get a property value
     * @param property name of the property
     * @param <R> casted type of response
     * @return value of the property
     */
    public <R> R getProperty(String property) {
        return (R)this.properties.get(property);
    }

    /**
     * Indicates if a property exists
     * @param property name of the property
     * @return boolean
     */
    public boolean hasProperty(String property) {
        return this.properties.containsKey(property);
    }

    /**
     * Set a property value
     * @param property name of the property
     * @param value value of the property
     */
    public void setProperty(String property, Object value) {
        this.properties.put(property, value);
    }

    /**
     * Load properties from a classpath resource
     * @param resourceName name of the resource
     * @throws IOException
     */
    public void loadPropertiesFromResource (String resourceName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            this.properties.load(in);
        }
        catch (IOException exception) {
            throw new RuntimeException("Error reading properties resource file", exception);
        }
    }

    /**
     * Load properties from a file
     * @param filename name of the file
     * @throws IOException
     */
    public void loadPropertiesFromFile (String filename) {
        try (FileInputStream in = new FileInputStream(filename)) {
            this.properties.load(in);
        }
        catch (IOException exception) {
            throw new RuntimeException("Error reading properties file", exception);
        }
    }

    /**
     * Get the main warp logger
     * @return Logger
     */
    public Logger getLogger() {
        return LoggerFactory.getLogger(DEFAULT_LOGGER_NAME);
    }

    /**
     * Get a logger with a given name
     * @param name name of the logger
     * @return Logger
     */
    public Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    /**
     * Get a logger with a given class
     * @param clazz class to obtain the logger
     * @return Logger
     */
    public Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Get a bundle string
     * @param bundleName name of bundle
     * @param locale Locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    public String getMessage(String bundleName, Locale locale, String key, Object... args) {
        String value = null;
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        if (bundle != null && bundle.containsKey(key)) {
            value = MessageFormat.format(bundle.getString(key), args);
        }
        else {
            value = "{" + key + "}";
            getLogger().warn("Message key \"" + key + "\" not found in bundle \"" + bundleName + "\" and locale \"" + locale + "\"");
        }
        return value;
    }

    /**
     * Handles a new request from a servlet
     * @param servletRequest servlet request
     * @param servletResponse servlet response
     * @throws ServletException exception
     * @throws IOException exception
     */
    protected void handleRequest (HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {

        long threadId = Thread.currentThread().getId();
        try {
            Request request = new Request(servletRequest);
            Response response = new Response(servletResponse);
            WarpContext context = new WarpContext(request, response);
            contexts.put(threadId, context);
            controllers.handle(request, response);
        }
        finally {
            WarpContext context = contexts.remove(threadId);
            try { context.release(); } catch (Exception ex) {}
        }
    }

    /**
     * Obtains the warp context for a given http servlet thread
     * @return warp context for the servlet thread
     */
    public WarpContext getContext() {
        return contexts.get(Thread.currentThread().getId());
    }

    /**
     * Obtains the current thread request
     * @return Request
     */
    public Request getRequest() {
        return getContext().getRequest();
    }

    /**
     * Obtains the current thread response
     * @return Response
     */
    public Response getResponse() {
        return getContext().getResponse();
    }

    /**
     * Get an instance of the controller for a given controller class
     * @param controllerClass controller class
     * @param <C> Type of controller
     * @return instance of a controller
     */
    public <C> C getController(Class<? extends C> controllerClass) {
        return controllers.getController(controllerClass);
    }

    /**
     * Obtains a model manager for a given model manager class
     * @param modelManagerClass class of a model manager
     * @param <M> Type of model manager
     * @return instance of model manager
     */
    public <M extends ModelManager> M getModelManager(Class<? extends M> modelManagerClass) {
        return models.getModelManager(modelManagerClass);
    }

    /**
     * Creates a model
     * @param model model to create
     * @param params paramters
     * @param <M> type of model
     * @return created model
     */
    public <M> M createModel(M model, Object... params) {
        return models.createModel(model, params);
    }

    /**
     * Updates a model
     * @param model model to update
     * @param params parameters
     * @param <M> type of model
     * @return updated model
     */
    public <M> M updateModel(M model, Object... params) {
        return models.updateModel(model, params);
    }

    /**
     * Deletes a model
     * @param model model to delete
     * @param params parameters
     * @param <M> type of model
     * @return deleted model
     */
    public <M> M deleteModel(M model, Object... params) {
        return models.deleteModel(model, params);
    }

    /**
     * Retrieves model by its id
     * @param modelClass Model class to retrieve
     * @param id id of the model
     * @param params parameters
     * @param <M> type of model
     * @return model to retrieve
     */
    public <M> M retrieveModel(Class<? extends M> modelClass, Object id, Object... params) {
        return models.retrieveModel(modelClass, id, params);
    }

    /**
     * Retrieves all models
     * @param modelClass Model class to retrieve
     * @param params parameters
     * @param <M> type of model
     * @return collection of models
     */
    public <M> Collection<M> retrieveModels(Class<? extends M> modelClass, Object... params) {
        return models.retrieveModels(modelClass, params);
    }

    /**
     * Retrieve models by a query
     * @param modelClass Model class
     * @param query query for models
     * @param params parameters
     * @param <M> type of model
     * @return collection of models
     */
    public <M> Collection<M> retrieveModels(Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return models.retrieveModels(modelClass, query, params);
    }

    /**
     * Retrieve a view factory
     * @param viewFactoryClass view factory class
     * @param <F> type of view factory
     * @return instance of a view factory
     */
    public <F extends ViewFactory> F getViewFactory(Class<? extends F> viewFactoryClass) {
        return views.getViewFactory(viewFactoryClass);
    }

    /**
     * Retrieve a view factory by its name
     * @param name name of view factory
     * @param <F> type of view factory
     * @return instance of a view factory
     */
    public <F extends ViewFactory> F getViewFactory(String name) {
        return views.getViewFactory(name);
    }

    /**
     * Creates a view
     * @param name name of the view
     * @param <V> type of view
     * @return instance of view
     */
    public <V extends View> V createView(String name) {
        return views.createView(name);
    }

    /**
     * Creates a view from a certain view factory
     * @param viewFactoryName view factory name
     * @param viewName name of the view
     * @param <V> type of view
     * @return instance of view
     */
    public <V extends View> V createView(String viewFactoryName, String viewName) {
        return views.createView(viewFactoryName, viewName);
    }

    /**
     * Creates a view by a name and parameters
     * @param name name of the view
     * @param viewParameters parameters to pass to the view
     * @param <V> type of view
     * @return instance of a view
     */
    public <V extends View> V createView(String name, Map<String, Object> viewParameters) {
        return views.createView(name, viewParameters);
    }

    /**
     * Creates a view by a factory name and parameters
     * @param viewFactoryName name of view factory
     * @param viewName name of view
     * @param viewParameters parameters to pass to the view
     * @param <V> type of view
     * @return instance of a view
     */
    public <V extends View> V createView(String viewFactoryName, String viewName, Map<String, Object> viewParameters) {
        return views.createView(viewFactoryName, viewName, viewParameters);
    }

    /**
     * Get a connection
     * @return connection
     */
    public DataConnection getConnection() {
        return dataSources.getConnection();
    }

    /**
     * Get a connection from a given data source name
     * @param dataSourceName name of data source
     * @return data connection
     */
    public DataConnection getConnection(String dataSourceName) {
        return dataSources.getConnection(dataSourceName);
    }
}
