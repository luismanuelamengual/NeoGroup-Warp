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
}
