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

    public void registerViewFactory (Class<? extends ViewFactory> viewFactoryClass) {

        try {
            ViewFactory viewFactory = viewFactoryClass.getConstructor().newInstance();
            viewFactories.put(viewFactoryClass, viewFactory);
            viewFactoriesByName.put(viewFactory.getName(), viewFactory);
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering view factory \"" + viewFactoryClass.getName() + "\" !!", ex);
        }
    }

    public <F extends ViewFactory> F getViewFactory (Class<? extends F> viewFactoryClass) {
        return (F)viewFactories.get(viewFactoryClass);
    }

    public <F extends ViewFactory> F getViewFactory (String name) {
        return (F)viewFactoriesByName.get(name);
    }

    public <V extends View> V createView (String name) {
        return createView(name, (Map<String,Object>)null);
    }

    public <V extends View> V createView (String name, Map<String, Object> viewParameters) {

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
        return createView(viewFactoryName, name, viewParameters);
    }

    public <V extends View> V createView(String viewFactoryName, String viewName) {
        return createView(viewFactoryName, viewName, null);
    }

    public <V extends View> V createView(String viewFactoryName, String viewName, Map<String,Object> viewParameters) {

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
        for (String key : viewParameters.keySet()) {
            view.setParameter(key, viewParameters.get(key));
        }
        return view;
    }
}
