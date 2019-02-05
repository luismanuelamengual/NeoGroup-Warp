package org.neogroup.warp.data;

import org.neogroup.warp.data.writers.JsonDataWriter;

public abstract class DataElement {

    private static JsonDataWriter jsonWriter = new JsonDataWriter();

    @Override
    public String toString() {
        return jsonWriter.write(this);
    }
}
