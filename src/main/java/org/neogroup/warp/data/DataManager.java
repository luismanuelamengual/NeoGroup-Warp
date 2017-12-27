package org.neogroup.warp.data;

import java.sql.Connection;

public abstract class DataManager {

    public abstract Connection getConnection();
}
