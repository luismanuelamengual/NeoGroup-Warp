package org.neogroup.warp.utils.json;

public abstract class Json {

    public static JsonArray array() {
        return new JsonArray();
    }

    public static JsonObject object() {
        return new JsonObject();
    }
}
