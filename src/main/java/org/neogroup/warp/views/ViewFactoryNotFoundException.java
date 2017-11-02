package org.neogroup.warp.views;

public class ViewFactoryNotFoundException extends ViewException {

    public ViewFactoryNotFoundException() {
    }

    public ViewFactoryNotFoundException(String message) {
        super(message);
    }

    public ViewFactoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewFactoryNotFoundException(Throwable cause) {
        super(cause);
    }
}
