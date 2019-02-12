package org.neogroup.warp;

import org.neogroup.warp.controllers.Controllers;
import org.neogroup.warp.data.*;
import org.neogroup.warp.properties.Properties;
import org.neogroup.warp.resources.Resource;
import org.neogroup.warp.resources.ResourceProxy;
import org.neogroup.warp.resources.Resources;
import org.neogroup.warp.views.View;
import org.neogroup.warp.views.ViewFactory;
import org.neogroup.warp.views.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class Warp {

    private static final String DEFAULT_LOGGER_NAME = "Warp";

    private static final Map<Long, WarpContext> contexts;

    static {
        contexts = new HashMap<>();
    }

    public static void registerController(Class controllerClass) {
        Controllers.registerController(controllerClass);
    }

    public static void registerViewFactory(String viewFactoryName, Class<? extends ViewFactory> viewFactoryClass) {
        Views.registerViewFactory(viewFactoryName, viewFactoryClass);
    }

    public static void registerDataSource(String dataSourceName, Class<? extends DataSource> dataSourceClass) {
        DataSources.registerDataSource(dataSourceName, dataSourceClass);
    }

    public static void registerResource(String resourceName, Class<? extends Resource> resourceClass) {
        Resources.register(resourceName, resourceClass);
    }

    public static <R> R getProperty(String property) {
        return (R) Properties.get(property);
    }

    public static boolean hasProperty(String property) {
        return Properties.has(property);
    }

    public static void setProperty(String property, Object value) {
        Properties.set(property, value);
    }

    public static Logger getLogger() {
        return getLogger(DEFAULT_LOGGER_NAME);
    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static String getMessage(String bundleName, Locale locale, String key, Object... args) {
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

    public static void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        long threadId = Thread.currentThread().getId();
        try {
            Request request = new Request(servletRequest);
            Response response = new Response(servletResponse);
            WarpContext context = new WarpContext(request, response);
            contexts.put(threadId, context);
            Controllers.handle(request, response);
        }
        finally {
            WarpContext context = contexts.remove(threadId);
            try { context.release(); } catch (Exception ex) {}
        }
    }

    public static <C> C getController(Class<? extends C> controllerClass) {
        return Controllers.get(controllerClass);
    }

    public static ResourceProxy getResource(String resourceName) {
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

    public static WarpContext getContext() {
        return contexts.get(Thread.currentThread().getId());
    }

    public static Request getRequest() {
        return getContext().getRequest();
    }

    public static Response getResponse() {
        return getContext().getResponse();
    }

    public static DataConnection getConnection() {
        return getContext().getConnection();
    }

    public static DataConnection getConnection(String dataSourceName) {
        return getContext().getConnection(dataSourceName);
    }

    public static DataTable getTable (String table) {
        return getConnection().getTable(table);
    }
}
