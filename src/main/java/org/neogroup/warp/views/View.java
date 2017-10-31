
package org.neogroup.warp.views;

/**
 * Class that represents a view that can be rendered to a string
 */
public abstract class View {

    /**
     * Set a parameter value
     * @param name name of parameter
     * @param value value of parameter
     */
    public abstract void setParameter (String name, Object value);

    /**
     * Get the parameter value
     * @param name name of parameter
     * @return value
     */
    public abstract Object getParameter (String name);

    /**
     * Renders the view to a string
     * @return string
     */
    public abstract String render ();

    /**
     * Renders the view as string
     * @return string representation of the view
     */
    @Override
    public String toString() {
        return render();
    }
}
