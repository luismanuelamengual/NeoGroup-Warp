
package org.neogroup.warp.views;

/**
 * Exception for views
 */
public class ViewException extends RuntimeException {

    /**
     * Constructor for a view exception
     */
    public ViewException() {
    }

    /**
     * Constructor for a view exception
     * @param message message
     */
    public ViewException(String message) {
        super(message);
    }

    /**
     * Constructor for a view exception
     * @param message message
     * @param cause cause
     */
    public ViewException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for a view exception
     * @param cause cause
     */
    public ViewException(Throwable cause) {
        super(cause);
    }
}
