package org.neogroup.warp.data.writers;

import org.neogroup.warp.data.DataArray;
import org.neogroup.warp.data.DataElement;
import org.neogroup.warp.data.DataObject;

import java.io.StringWriter;

public class JsonDataWriter extends DataWriter {

    private static final char JSON_ARRAY_START_CHAR = '[';
    private static final char JSON_ARRAY_END_CHAR = ']';
    private static final char JSON_OBJECT_START_CHAR = '{';
    private static final char JSON_OBJECT_END_CHAR = '}';
    private static final char JSON_DOUBLE_COLON_CHAR = '"';
    private static final char JSON_ELEMENT_SEPARATOR_CHAR = ',';
    private static final char JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR = ':';

    @Override
    public String write(DataElement element) {
        StringWriter writer = new StringWriter();
        write(element, writer);
        return writer.toString();
    }

    private void write (Object object, StringWriter writer) {
        if (object instanceof DataObject) {
            writeObject((DataObject)object, writer);
        }
        else if (object instanceof DataArray) {
            writeArray((DataArray)object, writer);
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

    private void writeObject (DataObject dataObject, StringWriter writer) {
        writer.append(JSON_OBJECT_START_CHAR);
        boolean isFirst = true;
        for (String key : dataObject.keys()) {
            if (!isFirst) {
                writer.append(JSON_ELEMENT_SEPARATOR_CHAR);
            }
            writer.append(JSON_DOUBLE_COLON_CHAR);
            writer.append(key);
            writer.append(JSON_DOUBLE_COLON_CHAR);
            writer.append(JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR);
            write(dataObject.get(key), writer);
            isFirst = false;
        }
        writer.append(JSON_OBJECT_END_CHAR);
    }

    private void writeArray (DataArray dataObject, StringWriter writer) {
        writer.append(JSON_ARRAY_START_CHAR);
        boolean isFirst = true;
        for (Object object : dataObject) {
            if (!isFirst) {
                writer.append(JSON_ELEMENT_SEPARATOR_CHAR);
            }
            write(object, writer);
            isFirst = false;
        }
        writer.append(JSON_ARRAY_END_CHAR);
    }
}
