package org.neogroup.warp.data;

import java.util.Arrays;
import java.util.List;

public abstract class Data {

    public static DataObject object() {
        return new DataObject();
    }

    public static List<Object> list(Object... objects) {
        return Arrays.asList(objects);
    }
}
