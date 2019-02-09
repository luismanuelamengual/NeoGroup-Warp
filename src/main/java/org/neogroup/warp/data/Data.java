package org.neogroup.warp.data;

public abstract class Data {

    public static DataList list() {
        return new DataList();
    }

    public static DataObject object() {
        return new DataObject();
    }
}
