package org.neogroup.warp.utils.json;

public abstract class JsonElement {

    @Override
    public String toString() {
        JsonWriter jsonWriter = new JsonWriter();
        return jsonWriter.write(this);
    }
}
