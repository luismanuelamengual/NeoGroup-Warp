package org.neogroup.warp;

import org.neogroup.util.Scanner;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.Request;
import org.neogroup.warp.controllers.Response;
import org.neogroup.warp.controllers.routing.*;
import org.neogroup.warp.models.ModelManager;
import org.neogroup.warp.models.ModelManagerComponent;
import org.neogroup.warp.models.ModelQuery;
import org.neogroup.warp.views.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WarpInstance {

    private static final String VIEWS_DEFAULT_FACTORY_NAME_PROPERTY_NAME = "views.default.factory.name";

    private final Properties properties;
    private final Map<Class, Object> controllers;
    private final Map<Class, ModelManager> managers;
    private final Map<Class, ModelManager> managersByModelClass;
    private final Map<Class, ViewFactory> viewFactories;
    private final Map<String, ViewFactory> viewFactoriesByName;
    private final Routes routes;
    private final Routes beforeRoutes;
    private final Routes afterRoutes;
    private final Routes notFoundRoutes;
    private final Routes errorRoutes;

    protected WarpInstance () {
        this(null);
    }

    protected WarpInstance (String basePackage) {

        this.properties = new Properties();
        this.controllers = new HashMap<>();
        this.routes = new Routes();
        this.beforeRoutes = new Routes();
        this.afterRoutes = new Routes();
        this.notFoundRoutes = new Routes();
        this.errorRoutes = new Routes();
        this.managers = new HashMap<>();
        this.managersByModelClass = new HashMap<>();
        this.viewFactories = new HashMap<>();
        this.viewFactoriesByName = new HashMap<>();
    }

    protected void initialize (String basePackage) {

        Scanner scanner = new Scanner();
        scanner.findClasses(cls -> {
            if ((basePackage == null || cls.getPackage().getName().startsWith(basePackage))) {

                try {

                    ControllerComponent controllerAnnotation = (ControllerComponent) cls.getAnnotation(ControllerComponent.class);
                    if (controllerAnnotation != null) {

                        Object controller = cls.newInstance();
                        controllers.put(cls, controller);

                        for (Field controllerField : cls.getDeclaredFields()) {

                            if (AbstractRoute.class.isAssignableFrom(controllerField.getType())) {

                                controllerField.setAccessible(true);
                                AbstractRoute route = (AbstractRoute)controllerField.get(controller);
                                Routes routesCollection = null;

                                if (route instanceof Route) {
                                    routesCollection = routes;
                                }
                                else if (route instanceof BeforeRoute) {
                                    routesCollection = beforeRoutes;
                                }
                                else if (route instanceof AfterRoute) {
                                    routesCollection = afterRoutes;
                                }
                                else if (route instanceof NotFoundRoute) {
                                    routesCollection = notFoundRoutes;
                                }
                                else if (route instanceof ErrorRoute) {
                                    routesCollection = errorRoutes;
                                }

                                if (routesCollection != null) {
                                    Get getAnnotation = controllerField.getAnnotation(Get.class);
                                    if (getAnnotation != null) {
                                        for (String path : getAnnotation.value()) {
                                            routesCollection.addRoute(new RouteEntry("GET", path, route));
                                        }
                                    }
                                    Post postAnnotation = controllerField.getAnnotation(Post.class);
                                    if (postAnnotation != null) {
                                        for (String path : postAnnotation.value()) {
                                            routesCollection.addRoute(new RouteEntry("POST", path, route));
                                        }
                                    }
                                    Put putAnnotation = controllerField.getAnnotation(Put.class);
                                    if (putAnnotation != null) {
                                        for (String path : putAnnotation.value()) {
                                            routesCollection.addRoute(new RouteEntry("PUT", path, route));
                                        }
                                    }
                                    Delete deleteAnnotation = controllerField.getAnnotation(Delete.class);
                                    if (deleteAnnotation != null) {
                                        for (String path : deleteAnnotation.value()) {
                                            routesCollection.addRoute(new RouteEntry("DELETE", path, route));
                                        }
                                    }
                                    Path pathAnnotation = controllerField.getAnnotation(Path.class);
                                    if (pathAnnotation != null) {
                                        for (String path : pathAnnotation.value()) {
                                            routesCollection.addRoute(new RouteEntry(null, path, route));
                                        }
                                    }
                                }
                            }
                        }
                        return true;
                    }

                    ModelManagerComponent managerAnnotation = (ModelManagerComponent)cls.getAnnotation(ModelManagerComponent.class);
                    if (managerAnnotation != null) {
                        if (ModelManager.class.isAssignableFrom(cls)) {
                            ModelManager modelManager = (ModelManager)cls.newInstance();
                            Type type = cls.getGenericSuperclass();
                            if(type instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) type;
                                Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                                Class modelClass = (Class)fieldArgTypes[0];
                                managersByModelClass.put(modelClass, modelManager);
                            }
                            managers.put(cls, modelManager);
                        }
                        return true;
                    }

                    ViewFactoryComponent viewFactoryComponent = (ViewFactoryComponent)cls.getAnnotation(ViewFactoryComponent.class);
                    if (viewFactoryComponent != null) {
                        if (ViewFactory.class.isAssignableFrom(cls)) {
                            ViewFactory viewFactory = (ViewFactory) cls.newInstance();
                            viewFactories.put(cls, viewFactory);
                            viewFactoriesByName.put(viewFactoryComponent.name(), viewFactory);
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        });
    }

    protected void processServletRequest (HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {

        Request request = new Request(servletRequest);
        Response response = new Response(servletResponse);
        Warp.setCurrentInstance(this);
        try {

            RouteEntry routeEntry = routes.findRoute(request);
            if (routeEntry != null) {

                RouteEntry beforeRouteEntry = beforeRoutes.findRoute(request);
                boolean executeRoute = true;
                if (beforeRouteEntry != null) {
                    executeRoute = ((BeforeRoute)beforeRouteEntry.getRoute()).handle(request, response);
                }

                if (executeRoute) {
                    Object routeResponse = ((Route)routeEntry.getRoute()).handle(request, response);

                    RouteEntry afterRouteEntry = afterRoutes.findRoute(request);
                    if (afterRouteEntry != null) {
                        routeResponse = ((AfterRoute)afterRouteEntry.getRoute()).handle(request, response, routeResponse);
                    }

                    if (routeResponse != null) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }
                }
            } else {

                RouteEntry notFoundRouteEntry = notFoundRoutes.findRoute(request);
                if (notFoundRouteEntry != null) {
                    Object routeResponse = ((NotFoundRoute)notFoundRouteEntry.getRoute()).handle(request, response);

                    if (routeResponse != null) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }
                }
                else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Route for path \"" + request.getPathInfo() + "\" not found !!");
                }
            }
        }
        catch (Throwable throwable) {

            try {
                RouteEntry errorRouteEntry = errorRoutes.findRoute(request);
                if (errorRouteEntry != null) {

                    Object routeResponse = ((ErrorRoute)errorRouteEntry.getRoute()).handle(request, response, throwable);

                    if (routeResponse != null) {
                        response.getWriter().print(routeResponse);
                        response.flushBuffer();
                    }

                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    throwable.printStackTrace(response.getWriter());
                }
            }
            catch (Throwable errorThrowable) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                errorThrowable.printStackTrace(response.getWriter());
            }
        }
        finally {
            Warp.setCurrentInstance(null);
        }
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

    public <C extends Object> C getController (Class<? extends C> controllerClass) {
        return (C)controllers.get(controllerClass);
    }

    public <M extends ModelManager> M getModelManager (Class<? extends M> modelManagerClass) {
        return (M)managers.get(modelManagerClass);
    }

    public <F extends ViewFactory> F getViewFactory (Class<? extends F> viewFactoryClass) {
        return (F)viewFactories.get(viewFactoryClass);
    }

    public <F extends ViewFactory> F getViewFactory (String name) {
        return (F)viewFactoriesByName.get(name);
    }

    public <V extends View> V createView (String name) {

        String viewFactoryName = null;
        if (viewFactories.size() == 1) {
            viewFactoryName = viewFactoriesByName.keySet().iterator().next();
        }
        else if (hasProperty(VIEWS_DEFAULT_FACTORY_NAME_PROPERTY_NAME)) {
            viewFactoryName = (String)getProperty(VIEWS_DEFAULT_FACTORY_NAME_PROPERTY_NAME);
        }
        return createView(viewFactoryName, name);
    }

    public <V extends View> V createView(String viewFactoryName, String viewName) {

        V view = null;
        ViewFactory viewFactory = viewFactoriesByName.get(viewFactoryName);
        if (viewFactory != null) {
            view = (V)viewFactory.createView(viewName);
        }
        if (view == null) {
            throw new ViewNotFoundException(MessageFormat.format("View \"" + viewName + " not found !!", viewName));
        }
        return view;
    }

    public <M extends Object> M createModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).create(model, params);
    }

    public <M extends Object> M updateModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).update(model, params);
    }

    public <M extends Object> M deleteModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).delete(model, params);
    }

    public <M extends Object> M retrieveModel(Class<? extends M> modelClass, Object id, Object... params) {
        return (M) managersByModelClass.get(modelClass).retrieve(id, params);
    }

    public <M extends Object> Collection<M> retrieveModels (Class<? extends M> modelClass, Object... params) {
        return retrieveModels(modelClass, null, params);
    }

    public <M extends Object> Collection<M> retrieveModels (Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return (Collection<M>) managersByModelClass.get(modelClass).retrieve(query, params);
    }
}
