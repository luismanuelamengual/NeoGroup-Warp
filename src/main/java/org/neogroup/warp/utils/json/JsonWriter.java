package org.neogroup.warp.utils.json;

import java.io.StringWriter;

public class JsonWriter {

    private static final char JSON_ARRAY_START_CHAR = '[';
    private static final char JSON_ARRAY_END_CHAR = ']';
    private static final char JSON_OBJECT_START_CHAR = '{';
    private static final char JSON_OBJECT_END_CHAR = '}';
    private static final char JSON_DOUBLE_COLON_CHAR = '"';
    private static final char JSON_ELEMENT_SEPARATOR_CHAR = ',';
    private static final char JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR = ':';

    public String write(JsonElement jsonElement) {
        StringWriter writer = new StringWriter();
        write(jsonElement, writer);
        return writer.toString();
    }

    private void write (Object object, StringWriter writer) {
        if (object instanceof JsonObject) {
            writeObject((JsonObject)object, writer);
        }
        else if (object instanceof JsonArray) {
            writeArray((JsonArray)object, writer);
        }
        else if ((object instanceof Number) || (object instanceof Boolean)) {
            writer.append(object.toString());
        }
        else {
            writer.append(JSON_DOUBLE_COLON_CHAR);
            writer.append(object.toString());
            writer.append(JSON_DOUBLE_COLON_CHAR);
        }
    }

    private void writeObject (JsonObject jsonObject, StringWriter writer) {
        writer.append(JSON_OBJECT_START_CHAR);
        boolean isFirst = true;
        for (String key : jsonObject.keys()) {
            if (!isFirst) {
                writer.append(JSON_ELEMENT_SEPARATOR_CHAR);
            }
            writer.append(JSON_DOUBLE_COLON_CHAR);
            writer.append(key);
            writer.append(JSON_DOUBLE_COLON_CHAR);
            writer.append(JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR);
            write(jsonObject.get(key), writer);
            isFirst = false;
        }
        writer.append(JSON_OBJECT_END_CHAR);
    }

    private void writeArray (JsonArray jsonObject, StringWriter writer) {
        writer.append(JSON_ARRAY_START_CHAR);
        boolean isFirst = true;
        for (Object object : jsonObject.getElements()) {
            if (!isFirst) {
                writer.append(JSON_ELEMENT_SEPARATOR_CHAR);
            }
            write(object, writer);
            isFirst = false;
        }
        writer.append(JSON_ARRAY_END_CHAR);
    }
}
