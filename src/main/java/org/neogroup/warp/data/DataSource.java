package org.neogroup.warp.data;

import java.sql.Connection;

public abstract class DataSource {

    protected abstract Connection requestConnection();
}
