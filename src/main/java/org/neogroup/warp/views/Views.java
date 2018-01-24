package org.neogroup.warp.views;

import org.neogroup.warp.WarpInstance;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Views {

    public static final String DEFAULT_VIEW_FACTORY_NAME_PROPERTY = "org.neogroup.warp.defaultViewfactoryName";
    private static final String DEFAULT_WARP_PARAMETER_NAME = "warp";

    private final WarpInstance warpInstance;
    private final Map<Class, ViewFactory> viewFactories;
    private final Map<String, ViewFactory> viewFactoriesByName;

    /**
     *
     * @param warpInstance
     */
    public Views(WarpInstance warpInstance) {
        this.warpInstance = warpInstance;
        this.viewFactories = new HashMap<>();
        this.viewFactoriesByName = new HashMap<>();
    }

    /**
     *
     * @param viewFactoryClass
     */
    public void registerViewFactory (Class<? extends ViewFactory> viewFactoryClass) {

        try {
            ViewFactory viewFactory = viewFactoryClass.getConstructor().newInstance();
            viewFactories.put(viewFactoryClass, viewFactory);

            ViewFactoryComponent viewFactoryComponent = viewFactoryClass.getAnnotation(ViewFactoryComponent.class);
            String viewFactoryName = null;
            if (viewFactoryComponent != null) {
                viewFactoryName = viewFactoryComponent.value();
                viewFactoriesByName.put(viewFactoryName, viewFactory);
            }

            warpInstance.getLogger().info("View factory \"" + viewFactoryClass.getName() + "\" registered !!" + (viewFactoryName != null?" [name=" + viewFactoryName + "]":""));
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering view factory \"" + viewFactoryClass.getName() + "\" !!", ex);
        }
    }

    /**
     *
     * @param viewFactoryClass
     * @param <F>
     * @return
     */
    public <F extends ViewFactory> F getViewFactory (Class<? extends F> viewFactoryClass) {
        return (F)viewFactories.get(viewFactoryClass);
    }

    /**
     *
     * @param name
     * @param <F>
     * @return
     */
    public <F extends ViewFactory> F getViewFactory (String name) {
        return (F)viewFactoriesByName.get(name);
    }

    /**
     *
     * @param name
     * @param <V>
     * @return
     */
    public <V extends View> V createView (String name) {
        return createView(name, (Map<String,Object>)null);
    }

    /**
     *
     * @param name
     * @param viewParameters
     * @param <V>
     * @return
     */
    public <V extends View> V createView (String name, Map<String, Object> viewParameters) {

        String viewFactoryName = null;

        if (viewFactories.isEmpty()) {
            throw new ViewFactoryNotFoundException("No View Factories where registered !!");
        }

        if (viewFactories.size() == 1) {
            viewFactoryName = viewFactoriesByName.keySet().iterator().next();
        }
        else if (warpInstance.hasProperty(DEFAULT_VIEW_FACTORY_NAME_PROPERTY)) {
            viewFactoryName = warpInstance.getProperty(DEFAULT_VIEW_FACTORY_NAME_PROPERTY);
        }
        else {
            throw new ViewFactoryNotFoundException("More than 1 view Factory is registered. Please set the property \"" + DEFAULT_VIEW_FACTORY_NAME_PROPERTY + "\" !!");
        }
        return createView(viewFactoryName, name, viewParameters);
    }

    /**
     *
     * @param viewFactoryName
     * @param viewName
     * @param <V>
     * @return
     */
    public <V extends View> V createView(String viewFactoryName, String viewName) {
        return createView(viewFactoryName, viewName, null);
    }

    /**
     *
     * @param viewFactoryName
     * @param viewName
     * @param viewParameters
     * @param <V>
     * @return
     */
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

        view.setParameter(DEFAULT_WARP_PARAMETER_NAME, warpInstance);
        for (String key : viewParameters.keySet()) {
            view.setParameter(key, viewParameters.get(key));
        }
        return view;
    }
}
