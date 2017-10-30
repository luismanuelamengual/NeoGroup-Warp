package org.neogroup.warp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Warp {

    private static final WarpInstance INSTANCE = new WarpInstance();

    protected Warp() {
    }

    private static WarpInstance getInstance() {
        return INSTANCE;
    }

    public static void initialize() {
        getInstance().initialize();
    }

    public static void initialize(String basePackage) {
        getInstance().initialize(basePackage);
    }

    public static void processServletRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        getInstance().processServletRequest(servletRequest, servletResponse);
    }

    public static <C> C getController(Class<? extends C> controllerClass) {
        return getInstance().getController(controllerClass);
    }
}
