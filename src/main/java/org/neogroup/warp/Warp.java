package org.neogroup.warp;

import org.neogroup.warp.data.DataConnection;
import org.neogroup.warp.data.DataSource;
import org.neogroup.warp.models.ModelManager;
import org.neogroup.warp.models.ModelQuery;
import org.neogroup.warp.views.View;
import org.neogroup.warp.views.ViewFactory;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Main warp framework
 * @author Luis Manuel Amengual
 */
public class Warp {

    private static WarpInstance instance;

    /**
     * Protected warp constructor
     */
    protected Warp() {
    }

    /**
     * Creates a new instance of warp
     * @return warp instance
     */
    public static WarpInstance createInstance () {
        return new WarpInstance();
    }

    /**
     * Set the main warp instance
     * @param instance warp instance
     */
    public static void setInstance (WarpInstance instance) {
        Warp.instance = instance;
    }

    /**
     * Get the warp instance
     * @return warp instance
     */
    public static WarpInstance getInstance () {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    /**
     * Initialize the warp instance
     */
    public static void init() {
        getInstance().init();
    }

    /**
     * Registers a controller class. Basically registers
     * all the routes that resides in the controller
     * @param controllerClass Controller class
     */
    public static void registerController(Class controllerClass) {
        getInstance().registerController(controllerClass);
    }

    /**
     * Registers a model manager
     * @param modelManagerClass model manager class
     */
    public static void registerModelManager(Class<? extends ModelManager> modelManagerClass) {
        getInstance().registerModelManager(modelManagerClass);
    }

    /**
     * Registers a view factory
     * @param viewFactoryClass view factory class
     */
    public static void registerViewFactory(Class<? extends ViewFactory> viewFactoryClass) {
        getInstance().registerViewFactory(viewFactoryClass);
    }

    /**
     * Registers a new data source
     * @param dataSourceClass data source class
     */
    public static void registerDataSource(Class<? extends DataSource> dataSourceClass) {
        getInstance().registerDataSource(dataSourceClass);
    }

    /**
     * Get the properties of the context
     * @return Properties
     */
    public static Properties getProperties() {
        return getInstance().getProperties();
    }

    /**
     * Get a property value
     * @param property name of the property
     * @param <R> casted type of response
     * @return value of the property
     */
    public static <R> R getProperty(String property) {
        return getInstance().getProperty(property);
    }

    /**
     * Indicates if a property exists
     * @param property name of the property
     * @return boolean
     */
    public static boolean hasProperty(String property) {
        return getInstance().hasProperty(property);
    }

    /**
     * Set a property value
     * @param property name of the property
     * @param value value of the property
     */
    public static void setProperty(String property, Object value) {
        getInstance().setProperty(property, value);
    }

    /**
     * Load properties from a classpath resource
     * @param resourceName name of the resource
     * @throws IOException
     */
    public static void loadPropertiesFromResource(String resourceName) {
        getInstance().loadPropertiesFromResource(resourceName);
    }

    /**
     * Load properties from a file
     * @param filename name of the file
     * @throws IOException
     */
    public static void loadPropertiesFromFile(String filename) {
        getInstance().loadPropertiesFromFile(filename);
    }

    /**
     * Get the main warp logger
     * @return Logger
     */
    public static Logger getLogger() {
        return getInstance().getLogger();
    }

    /**
     * Get a logger with a given name
     * @param name name of the logger
     * @return Logger
     */
    public static Logger getLogger(String name) {
        return getInstance().getLogger(name);
    }

    /**
     * Get a logger with a given class
     * @param clazz class to obtain the logger
     * @return Logger
     */
    public static Logger getLogger(Class<?> clazz) {
        return getInstance().getLogger(clazz);
    }

    /**
     * Get a bundle string
     * @param bundleName name of bundle
     * @param locale Locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    public static String getMessage(String bundleName, Locale locale, String key, Object... args) {
        return getInstance().getMessage(bundleName, locale, key, args);
    }

    /**
     * Handles a new request from a servlet
     * @param servletRequest servlet request
     * @param servletResponse servlet response
     * @throws ServletException exception
     * @throws IOException exception
     */
    protected static void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {

        getInstance().handleRequest(servletRequest, servletResponse);
    }

    /**
     * Obtains the warp context for a given http servlet thread
     * @return warp context for the servlet thread
     */
    public static WarpContext getContext() {
        return getInstance().getContext();
    }

    /**
     * Obtains the current thread request
     * @return Request
     */
    public static Request getRequest() {
        return getInstance().getRequest();
    }

    /**
     * Obtains the current thread response
     * @return Response
     */
    public static Response getResponse() {
        return getInstance().getResponse();
    }

    /**
     * Get an instance of the controller for a given controller class
     * @param controllerClass controller class
     * @param <C> Type of controller
     * @return instance of a controller
     */
    public static <C> C getController(Class<? extends C> controllerClass) {
        return getInstance().getController(controllerClass);
    }

    /**
     * Obtains a model manager for a given model manager class
     * @param modelManagerClass class of a model manager
     * @param <M> Type of model manager
     * @return instance of model manager
     */
    public static <M extends ModelManager> M getModelManager(Class<? extends M> modelManagerClass) {
        return getInstance().getModelManager(modelManagerClass);
    }

    /**
     * Creates a model
     * @param model model to create
     * @param params paramters
     * @param <M> type of model
     * @return created model
     */
    public static <M> M createModel(M model, Object... params) {
        return getInstance().createModel(model, params);
    }

    /**
     * Updates a model
     * @param model model to update
     * @param params parameters
     * @param <M> type of model
     * @return updated model
     */
    public static <M> M updateModel(M model, Object... params) {
        return getInstance().updateModel(model, params);
    }

    /**
     * Deletes a model
     * @param model model to delete
     * @param params parameters
     * @param <M> type of model
     * @return deleted model
     */
    public static <M> M deleteModel(M model, Object... params) {
        return getInstance().deleteModel(model, params);
    }

    /**
     * Retrieves model by its id
     * @param modelClass Model class to retrieve
     * @param id id of the model
     * @param params parameters
     * @param <M> type of model
     * @return model to retrieve
     */
    public static <M> M retrieveModel(Class<? extends M> modelClass, Object id, Object... params) {
        return getInstance().retrieveModel(modelClass, id, params);
    }

    /**
     * Retrieves all models
     * @param modelClass Model class to retrieve
     * @param params parameters
     * @param <M> type of model
     * @return collection of models
     */
    public static <M> Collection<M> retrieveModels(Class<? extends M> modelClass, Object... params) {
        return getInstance().retrieveModels(modelClass, params);
    }

    /**
     * Retrieve models by a query
     * @param modelClass Model class
     * @param query query for models
     * @param params parameters
     * @param <M> type of model
     * @return collection of models
     */
    public static <M> Collection<M> retrieveModels(Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return getInstance().retrieveModels(modelClass, query, params);
    }

    /**
     * Retrieve a view factory
     * @param viewFactoryClass view factory class
     * @param <F> type of view factory
     * @return instance of a view factory
     */
    public static <F extends ViewFactory> F getViewFactory(Class<? extends F> viewFactoryClass) {
        return getInstance().getViewFactory(viewFactoryClass);
    }

    /**
     * Retrieve a view factory by its name
     * @param name name of view factory
     * @param <F> type of view factory
     * @return instance of a view factory
     */
    public static <F extends ViewFactory> F getViewFactory(String name) {
        return getInstance().getViewFactory(name);
    }

    /**
     * Creates a view
     * @param name name of the view
     * @param <V> type of view
     * @return instance of view
     */
    public static <V extends View> V createView(String name) {
        return getInstance().createView(name);
    }

    /**
     * Creates a view from a certain view factory
     * @param viewFactoryName view factory name
     * @param viewName name of the view
     * @param <V> type of view
     * @return instance of view
     */
    public static <V extends View> V createView(String viewFactoryName, String viewName) {
        return getInstance().createView(viewFactoryName, viewName);
    }

    /**
     * Creates a view by a name and parameters
     * @param name name of the view
     * @param viewParameters parameters to pass to the view
     * @param <V> type of view
     * @return instance of a view
     */
    public static <V extends View> V createView(String name, Map<String, Object> viewParameters) {
        return getInstance().createView(name, viewParameters);
    }

    /**
     * Creates a view by a factory name and parameters
     * @param viewFactoryName name of view factory
     * @param viewName name of view
     * @param viewParameters parameters to pass to the view
     * @param <V> type of view
     * @return instance of a view
     */
    public static <V extends View> V createView(String viewFactoryName, String viewName, Map<String, Object> viewParameters) {
        return getInstance().createView(viewFactoryName, viewName, viewParameters);
    }

    /**
     * Get a connection
     * @return connection
     */
    public static DataConnection getConnection() {
        return getInstance().getConnection();
    }

    /**
     * Get a connection from a given data source name
     * @param dataSourceName name of data source
     * @return data connection
     */
    public static DataConnection getConnection(String dataSourceName) {
        return getInstance().getConnection(dataSourceName);
    }
}
