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
 * @todo Agregar comentarios
 * @todo Hacer la documentación
 * @todo Hacer metodos halt redirect como sparks (puede quedar para la siguiente version)
 * @todo Agregar eventos (Puede quedar para siguiente version)
 * @todo Crear un ModelManager con una implementación usando DataSources usando Attribute y Entity (siguiente version)
 */
public class Warp {

    private static WarpInstance instance;

    protected Warp() {
    }

    public static WarpInstance createInstance () {
        return new WarpInstance();
    }

    public static void setInstance (WarpInstance instance) {
        Warp.instance = instance;
    }

    public static WarpInstance getInstance () {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    public static void registerComponents(String basePackage) {
        getInstance().registerComponents(basePackage);
    }

    public static void registerController(Class controllerClass) {
        getInstance().registerController(controllerClass);
    }

    public static void registerModelManager(Class<? extends ModelManager> modelManagerClass) {
        getInstance().registerModelManager(modelManagerClass);
    }

    public static void registerViewFactory(Class<? extends ViewFactory> viewFactoryClass) {
        getInstance().registerViewFactory(viewFactoryClass);
    }

    public static void registerDataSource(Class<? extends DataSource> dataSourceClass) {
        getInstance().registerDataSource(dataSourceClass);
    }

    public static Properties getProperties() {
        return getInstance().getProperties();
    }

    public static <R> R getProperty(String property) {
        return getInstance().getProperty(property);
    }

    public static boolean hasProperty(String property) {
        return getInstance().hasProperty(property);
    }

    public static void setProperty(String property, Object value) {
        getInstance().setProperty(property, value);
    }

    public static void loadPropertiesFromResource(String resourceName) {
        getInstance().loadPropertiesFromResource(resourceName);
    }

    public static void loadPropertiesFromFile(String filename) {
        getInstance().loadPropertiesFromFile(filename);
    }

    public static Logger getLogger() {
        return getInstance().getLogger();
    }

    public static Logger getLogger(String name) {
        return getInstance().getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getInstance().getLogger(clazz);
    }

    public static String getMessage(String bundleName, Locale locale, String key, Object... args) {
        return getInstance().getMessage(bundleName, locale, key, args);
    }

    public static WarpContext getContext() {
        return getInstance().getContext();
    }

    public static Request getRequest() {
        return getInstance().getRequest();
    }

    public static Response getResponse() {
        return getInstance().getResponse();
    }

    public static void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {

        getInstance().handleRequest(servletRequest, servletResponse);
    }

    public static <C> C getController(Class<? extends C> controllerClass) {
        return getInstance().getController(controllerClass);
    }

    public static <M extends ModelManager> M getModelManager(Class<? extends M> modelManagerClass) {
        return getInstance().getModelManager(modelManagerClass);
    }

    public static <M> M createModel(M model, Object... params) {
        return getInstance().createModel(model, params);
    }

    public static <M> M updateModel(M model, Object... params) {
        return getInstance().updateModel(model, params);
    }

    public static <M> M deleteModel(M model, Object... params) {
        return getInstance().deleteModel(model, params);
    }

    public static <M> M retrieveModel(Class<? extends M> modelClass, Object id, Object... params) {
        return getInstance().retrieveModel(modelClass, id, params);
    }

    public static <M> Collection<M> retrieveModels(Class<? extends M> modelClass, Object... params) {
        return getInstance().retrieveModels(modelClass, params);
    }

    public static <M> Collection<M> retrieveModels(Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return getInstance().retrieveModels(modelClass, query, params);
    }

    public static <F extends ViewFactory> F getViewFactory(Class<? extends F> viewFactoryClass) {
        return getInstance().getViewFactory(viewFactoryClass);
    }

    public static <F extends ViewFactory> F getViewFactory(String name) {
        return getInstance().getViewFactory(name);
    }

    public static <V extends View> V createView(String name) {
        return getInstance().createView(name);
    }

    public static <V extends View> V createView(String viewFactoryName, String viewName) {
        return getInstance().createView(viewFactoryName, viewName);
    }

    public static <V extends View> V createView(String name, Map<String, Object> viewParameters) {
        return getInstance().createView(name, viewParameters);
    }

    public static <V extends View> V createView(String viewFactoryName, String viewName, Map<String, Object> viewParameters) {
        return getInstance().createView(viewFactoryName, viewName, viewParameters);
    }

    public static DataConnection getConnection() {
        return getInstance().getConnection();
    }

    public static DataConnection getConnection(String dataConnectionName) {
        return getInstance().getConnection(dataConnectionName);
    }
}
