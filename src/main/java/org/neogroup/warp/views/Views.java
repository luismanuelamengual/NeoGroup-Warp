package org.neogroup.warp.views;

import org.neogroup.warp.Warp;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Views {

    private static final String VIEWS_DEFAULT_FACTORY_NAME_PROPERTY_NAME = "views.default.factory.name";

    private final Map<Class, ViewFactory> viewFactories;
    private final Map<String, ViewFactory> viewFactoriesByName;

    public Views() {
        this.viewFactories = new HashMap<>();
        this.viewFactoriesByName = new HashMap<>();
    }

    public void registerViewFactory (ViewFactory viewFactory) {

        Class viewFactoryClass = viewFactory.getClass();
        ViewFactoryComponent viewFactoryComponent = (ViewFactoryComponent)viewFactoryClass.getAnnotation(ViewFactoryComponent.class);
        viewFactories.put(viewFactory.getClass(), viewFactory);
        if (viewFactoryComponent != null) {
            viewFactoriesByName.put(viewFactoryComponent.name(), viewFactory);
        }
    }

    public <F extends ViewFactory> F getViewFactory (Class<? extends F> viewFactoryClass) {
        return (F)viewFactories.get(viewFactoryClass);
    }

    public <F extends ViewFactory> F getViewFactory (String name) {
        return (F)viewFactoriesByName.get(name);
    }

    public <V extends View> V createView (String name) {

        String viewFactoryName = null;

        if (viewFactories.isEmpty()) {
            throw new ViewFactoryNotFoundException("No View Factories where registered !!");
        }
        if (viewFactories.size() == 1) {
            viewFactoryName = viewFactoriesByName.keySet().iterator().next();
        }
        else if (Warp.hasProperty(VIEWS_DEFAULT_FACTORY_NAME_PROPERTY_NAME)) {
            viewFactoryName = (String)Warp.getProperty(VIEWS_DEFAULT_FACTORY_NAME_PROPERTY_NAME);
        }
        else {
            throw new ViewFactoryNotFoundException("More than 1 view Factory is registered. Please set the property \"" + VIEWS_DEFAULT_FACTORY_NAME_PROPERTY_NAME + "\" !!");
        }
        return createView(viewFactoryName, name);
    }

    public <V extends View> V createView(String viewFactoryName, String viewName) {

        if (viewFactoryName == null) {
            throw new ViewFactoryNotFoundException("View Factory Name is required");
        }
        ViewFactory viewFactory = viewFactoriesByName.get(viewFactoryName);
        if (viewFactory == null) {
            throw new ViewFactoryNotFoundException("No view factory found with name \"" + viewFactoryName + "\" !!");
        }
        V view = (V)viewFactory.createView(viewName);
        if (view == null) {
            throw new ViewNotFoundException(MessageFormat.format("View \"" + viewName + " not found !!", viewName));
        }
        return view;
    }
}
