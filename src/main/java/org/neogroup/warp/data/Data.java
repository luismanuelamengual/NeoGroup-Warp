package org.neogroup.warp.data;

public abstract class Data {

    public static DataArray array() {
        return new DataArray();
    }

    public static DataObject object() {
        return new DataObject();
    }
}
