
package org.neogroup.warp.views;

/**
 * Exception for views not found
 */
public class ViewNotFoundException extends ViewException {

    /**
     * Constructor for the exception
     */
    public ViewNotFoundException() {
    }

    /**
     * Constructor for the exception
     * @param message
     */
    public ViewNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for the exception
     * @param message
     * @param cause
     */
    public ViewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for the exception
     * @param cause
     */
    public ViewNotFoundException(Throwable cause) {
        super(cause);
    }
}
