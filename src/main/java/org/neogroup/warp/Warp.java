package org.neogroup.warp;

import org.neogroup.warp.models.ModelManager;
import org.neogroup.warp.models.ModelQuery;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Warp {

    private static WarpInstance instance;
    private static Map<Long, WarpInstance> instances;

    static {
        instances = new HashMap<>();
    }

    protected Warp() {
    }

    public static WarpInstance createInstance () {
        return createInstance(null);
    }

    public static WarpInstance createInstance (String basePackage) {
        return new WarpInstance(basePackage);
    }

    protected static void setCurrentInstance (WarpInstance instance) {
        long currentThreadId = Thread.currentThread().getId();
        if (instance != null) {
            instances.put(currentThreadId, instance);
        }
        else {
            instances.remove(currentThreadId);
        }
    }

    protected static WarpInstance getCurrentInstance () {
        return instances.get(Thread.currentThread().getId());
    }

    public static WarpInstance getInstance() {
        WarpInstance instance = getCurrentInstance();
        if (instance == null) {
            instance = Warp.instance;
            if (instance == null) {
                instance = createInstance();
                Warp.instance = instance;
            }
        }
        return instance;
    }

    protected static void processServletRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        getInstance().processServletRequest(servletRequest, servletResponse);
    }

    public static <C> C getController(Class<? extends C> controllerClass) {
        return getInstance().getController(controllerClass);
    }

    public static <M extends ModelManager> M getModelManager(Class<? extends M> modelManagerClass) {
        return getInstance().getModelManager(modelManagerClass);
    }

    public static <M> M create(M model, Object... params) {
        return getInstance().create(model, params);
    }

    public static <M> M update(M model, Object... params) {
        return getInstance().update(model, params);
    }

    public static <M> M delete(M model, Object... params) {
        return getInstance().delete(model, params);
    }

    public static <M> Collection<M> retrieve(Class<? extends M> modelClass, Object... params) {
        return getInstance().retrieve(modelClass, params);
    }

    public static <M> Collection<M> retrieve(String modelName, Object... params) {
        return getInstance().retrieve(modelName, params);
    }

    public static <M> Collection<M> retrieve(Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return getInstance().retrieve(modelClass, query, params);
    }

    public static <M> Collection<M> retrieve(String modelName, ModelQuery query, Object... params) {
        return getInstance().retrieve(modelName, query, params);
    }
}
