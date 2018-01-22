package org.neogroup.warp.data;

import java.sql.Connection;

/**
 *
 */
public abstract class DataSource {

    /**
     *
     * @return
     */
    protected abstract Connection requestConnection();
}
