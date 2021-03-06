
package org.neogroup.warp.views;

/**
 * Represents a view factory, responsible of creating views
 * @param <V> type of views the factory creates
 */
public abstract class ViewFactory<V extends View> {

    /**
     * Creates a view
     * @param viewName name of the view
     * @return create view
     * @throws ViewException
     */
    public abstract V createView(String viewName) throws ViewException;
}
