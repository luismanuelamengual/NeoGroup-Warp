package org.neogroup.warp.data;

public abstract class Data {

    public static DataObject object() {
        return new DataObject();
    }

    public static DataCollection collection() {
        return new DataCollection();
    }
}
