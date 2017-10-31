package org.neogroup.warp;

import org.neogroup.util.Scanner;
import org.neogroup.warp.controllers.ControllerComponent;
import org.neogroup.warp.controllers.Request;
import org.neogroup.warp.controllers.Response;
import org.neogroup.warp.controllers.routing.*;
import org.neogroup.warp.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WarpInstance {

    private final Map<Class, Object> controllers;
    private final Map<Class, Object> managers;
    private final Map<String, ModelManager> managersByModelName;
    private final Routes routes;
    private final Routes beforeRoutes;
    private final Routes afterRoutes;
    private final Routes notFoundRoutes;
    private final Routes errorRoutes;

    protected WarpInstance () {
        this(null);
    }

    protected WarpInstance (String basePackage) {

        this.controllers = new HashMap<>();
        this.routes = new Routes();
        this.beforeRoutes = new Routes();
        this.afterRoutes = new Routes();
        this.notFoundRoutes = new Routes();
        this.errorRoutes = new Routes();
        this.managers = new HashMap<>();
        this.managersByModelName = new HashMap<>();
        initialize(basePackage);
    }

    private void initialize (String basePackage) {

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
                        ModelManager modelManager = (ModelManager)cls.newInstance();
                        if (CustomModelManager.class.isAssignableFrom(cls)) {
                            CustomModelManager customModelManager = (CustomModelManager)modelManager;
                            managersByModelName.put(customModelManager.getModelName(), customModelManager);
                        }
                        else if (ModelManager.class.isAssignableFrom(cls)) {
                            Type type = cls.getGenericSuperclass();
                            if(type instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) type;
                                Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                                Class modelClass = (Class)fieldArgTypes[0];
                                managersByModelName.put(modelClass.getName(), modelManager);
                            }
                        }
                        managers.put(cls, modelManager);
                        return true;
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

    public <C extends Object> C getController (Class<? extends C> controllerClass) {
        return (C)controllers.get(controllerClass);
    }

    public <M extends ModelManager> M getModelManager (Class<? extends M> modelManagerClass) {
        return (M)managers.get(modelManagerClass);
    }

    private String getModelName(Object model) {
        return (model instanceof CustomModel)? ((CustomModel)model).getModelName() : model.getClass().getName();
    }

    public <M extends Object> M create(M model, Object... params) {
        return (M)managersByModelName.get(getModelName(model)).create(model, params);
    }

    public <M extends Object> M update(M model, Object... params) {
        return (M)managersByModelName.get(getModelName(model)).update(model, params);
    }

    public <M extends Object> M delete(M model, Object... params) {
        return (M)managersByModelName.get(getModelName(model)).delete(model, params);
    }

    public <M extends Object> Collection<M> retrieve (Class<? extends M> modelClass, Object... params) {
        return retrieve(modelClass, null, params);
    }

    public <M extends Object> Collection<M> retrieve (Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return retrieve(modelClass.getName(), query, params);
    }

    public <M extends Object> Collection<M> retrieve (String modelName, Object... params) {
        return retrieve(modelName, null, params);
    }

    public <M extends Object> Collection<M> retrieve (String modelName, ModelQuery query, Object... params) {
        return (Collection<M>)managersByModelName.get(modelName).retrieve(query, params);
    }
}
