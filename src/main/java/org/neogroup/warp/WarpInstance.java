package org.neogroup.warp;

import org.neogroup.util.Scanner;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.Controllers;
import org.neogroup.warp.models.ModelManager;
import org.neogroup.warp.models.ModelManagerComponent;
import org.neogroup.warp.models.ModelQuery;
import org.neogroup.warp.models.Models;
import org.neogroup.warp.views.View;
import org.neogroup.warp.views.ViewFactory;
import org.neogroup.warp.views.ViewFactoryComponent;
import org.neogroup.warp.views.Views;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

public class WarpInstance {

    private final Properties properties;
    private final Controllers controllers;
    private final Models models;
    private final Views views;

    protected WarpInstance () {

        this.properties = new Properties();
        this.controllers = new Controllers();
        this.models = new Models();
        this.views = new Views();
    }

    protected void initialize (String basePackage) {

        Scanner scanner = new Scanner();
        scanner.findClasses(cls -> {
            if ((basePackage == null || cls.getPackage().getName().startsWith(basePackage))) {

                try {

                    ControllerComponent controllerAnnotation = (ControllerComponent) cls.getAnnotation(ControllerComponent.class);
                    if (controllerAnnotation != null) {
                        Object controller = cls.newInstance();
                        controllers.registerController(controller);
                        return true;
                    }

                    ModelManagerComponent managerAnnotation = (ModelManagerComponent)cls.getAnnotation(ModelManagerComponent.class);
                    if (managerAnnotation != null) {
                        if (ModelManager.class.isAssignableFrom(cls)) {
                            ModelManager modelManager = (ModelManager)cls.newInstance();
                            models.registerModelManager(modelManager);
                            return true;
                        }
                    }

                    ViewFactoryComponent viewFactoryComponent = (ViewFactoryComponent)cls.getAnnotation(ViewFactoryComponent.class);
                    if (viewFactoryComponent != null) {
                        if (ViewFactory.class.isAssignableFrom(cls)) {
                            ViewFactory viewFactory = (ViewFactory) cls.newInstance();
                            views.registerViewFactory(viewFactory);
                            return true;
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        });
    }

    /**
     * Get the properties of the context
     * @return
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

    public void handleRequest (HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        controllers.handle(servletRequest, servletResponse);
    }

    public <C> C getController(Class<? extends C> controllerClass) {
        return controllers.getController(controllerClass);
    }

    public <M extends ModelManager> M getModelManager(Class<? extends M> modelManagerClass) {
        return models.getModelManager(modelManagerClass);
    }

    public <M> M createModel(M model, Object... params) {
        return models.createModel(model, params);
    }

    public <M> M updateModel(M model, Object... params) {
        return models.updateModel(model, params);
    }

    public <M> M deleteModel(M model, Object... params) {
        return models.deleteModel(model, params);
    }

    public <M> M retrieveModel(Class<? extends M> modelClass, Object id, Object... params) {
        return models.retrieveModel(modelClass, id, params);
    }

    public <M> Collection<M> retrieveModels(Class<? extends M> modelClass, Object... params) {
        return models.retrieveModels(modelClass, params);
    }

    public <M> Collection<M> retrieveModels(Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return models.retrieveModels(modelClass, query, params);
    }

    public <F extends ViewFactory> F getViewFactory(Class<? extends F> viewFactoryClass) {
        return views.getViewFactory(viewFactoryClass);
    }

    public <F extends ViewFactory> F getViewFactory(String name) {
        return views.getViewFactory(name);
    }

    public <V extends View> V createView(String name) {
        return views.createView(name);
    }

    public <V extends View> V createView(String viewFactoryName, String viewName) {
        return views.createView(viewFactoryName, viewName);
    }
}
