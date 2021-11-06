package org.neogroup.warp;

import org.neogroup.warp.controllers.Controllers;
import org.neogroup.warp.data.*;
import org.neogroup.warp.http.Request;
import org.neogroup.warp.http.Response;
import org.neogroup.warp.messages.Messages;
import org.neogroup.warp.properties.Properties;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceProxy;
import org.neogroup.warp.resources.Resources;
import org.neogroup.warp.views.View;
import org.neogroup.warp.views.ViewFactory;
import org.neogroup.warp.views.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;

public abstract class Warp {

    private static final Logger mainLogger = LoggerFactory.getLogger(Warp.class);

    public static void registerController(String basePath, Class controllerClass) {
        Controllers.registerController(basePath, controllerClass);
    }

    public static void registerViewFactory(String viewFactoryName, Class<? extends ViewFactory> viewFactoryClass) {
        Views.registerViewFactory(viewFactoryName, viewFactoryClass);
    }

    public static void registerDataSource(String dataSourceName, Class<? extends DataSource> dataSourceClass) {
        DataSources.registerDataSource(dataSourceName, dataSourceClass);
    }

    public static void registerResource(String resourceName, Class<? extends Resource> resourceClass) {
        registerResource(resourceName, resourceClass, false);
    }

    public static void registerResource(String resourceName, Class<? extends Resource> resourceClass, boolean publish) {
        Resources.register(resourceName, resourceClass, publish);
    }

    public static String getProperty(String property) {
        return Properties.get(property);
    }

    public static String getProperty(String property, String defaultValue) {
        return Properties.get(property, defaultValue);
    }

    public static boolean hasProperty(String property) {
        return Properties.has(property);
    }

    public static void setProperty(String property, String value) {
        Properties.set(property, value);
    }

    public static Logger getLogger() {
        return mainLogger;
    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static String getMessage(String key, Object... args) {
        return Messages.get(key, args);
    }

    public static String getMessage(String bundleName, String key, Object... args) {
        return Messages.get(bundleName, key, args);
    }

    public static String getMessage(Locale locale, String key, Object... args) {
        return Messages.get(locale, key, args);
    }

    public static String getMessage(String bundleName, Locale locale, String key, Object... args) {
        return Messages.get(bundleName, locale, key, args);
    }

    public static <C> C getController(Class<? extends C> controllerClass) {
        return Controllers.get(controllerClass);
    }

    public static <M> ResourceProxy<M> getResource(Class<M> modelClass) {
        return Resources.get(modelClass);
    }

    public static ResourceProxy<DataObject> getResource(String resourceName) {
        return Resources.get(resourceName);
    }

    public static <F extends ViewFactory> F getViewFactory(Class<? extends F> viewFactoryClass) {
        return Views.getViewFactory(viewFactoryClass);
    }

    public static <F extends ViewFactory> F getViewFactory(String name) {
        return Views.getViewFactory(name);
    }

    public static <V extends View> V createView(String name) {
        return Views.createView(name);
    }

    public static <V extends View> V createView(String viewFactoryName, String viewName) {
        return Views.createView(viewFactoryName, viewName);
    }

    public static <V extends View> V createView(String name, Map<String, Object> viewParameters) {
        return Views.createView(name, viewParameters);
    }

    public static <V extends View> V createView(String viewFactoryName, String viewName, Map<String, Object> viewParameters) {
        return Views.createView(viewFactoryName, viewName, viewParameters);
    }

    public static Request getRequest() {
        return WarpContext.getCurrent().getRequest();
    }

    public static Response getResponse() {
        return WarpContext.getCurrent().getResponse();
    }

    public static DataConnection getConnection() {
        return WarpContext.getCurrent().getConnection();
    }

    public static DataConnection getConnection(String dataSourceName) {
        return WarpContext.getCurrent().getConnection(dataSourceName);
    }

    public static DataTable getTable (String table) {
        return getConnection().getTable(table);
    }
}
