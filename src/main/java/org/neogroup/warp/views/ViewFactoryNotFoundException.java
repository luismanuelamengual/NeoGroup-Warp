package org.neogroup.warp.views;

/**
 *
 */
public class ViewFactoryNotFoundException extends ViewException {

    /**
     *
     */
    public ViewFactoryNotFoundException() {
    }

    /**
     *
     * @param message
     */
    public ViewFactoryNotFoundException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public ViewFactoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public ViewFactoryNotFoundException(Throwable cause) {
        super(cause);
    }
}
