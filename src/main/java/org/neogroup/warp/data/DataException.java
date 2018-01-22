package org.neogroup.warp.data;

/**
 *
 */
public class DataException extends RuntimeException {

    /**
     *
     */
    public DataException() {
    }

    /**
     *
     * @param message
     */
    public DataException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public DataException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
