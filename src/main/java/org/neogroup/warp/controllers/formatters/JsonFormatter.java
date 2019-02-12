package org.neogroup.warp.controllers.formatters;

import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.DataElement;
import org.neogroup.warp.data.DataObject;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class JsonFormatter extends Formatter {

    private static final String JSON_NULL = "null";
    private static final char JSON_ARRAY_START_CHAR = '[';
    private static final char JSON_ARRAY_END_CHAR = ']';
    private static final char JSON_OBJECT_START_CHAR = '{';
    private static final char JSON_OBJECT_END_CHAR = '}';
    private static final char JSON_DOUBLE_COLON_CHAR = '"';
    private static final char JSON_ELEMENT_SEPARATOR_CHAR = ',';
    private static final char JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR = ':';

    @Override
    public String format(Object object) {
        StringWriter writer = new StringWriter();
        write(object, writer);
        return writer.toString();
    }

    private void write (Object object, StringWriter writer) {
        if (object == null) {
            writeNull(writer);
        } else if ((object instanceof Number)) {
            writeNumber((Number) object, writer);
        } else if (object instanceof Boolean) {
            writeBoolean((Boolean) object, writer);
        } else if (object instanceof CharSequence) {
            writeString((CharSequence) object, writer);
        } else if (object instanceof DataElement) {
            writeDataElement((DataElement) object, writer);
        } else if (object.getClass().isArray()) {
            writeArray((Object[]) object, writer);
        } else if (object instanceof Iterable) {
            writeIterable((Iterable) object, writer);
        } else if (object instanceof Map) {
            writeMap((Map) object, writer);
        } else if (object instanceof Date) {
            writeDate((Date) object, writer);
        } else {
            writeObject(object, writer);
        }
    }

    private void writeNull (StringWriter writer) {
        writer.append(JSON_NULL);
    }

    private void writeNumber (Number number, StringWriter writer) {
        writer.append(number.toString());
    }

    private void writeBoolean (Boolean bool, StringWriter writer) {
        writer.append(bool.toString());
    }

    private void writeDate (Date date, StringWriter writer) {
        writer.append(JSON_DOUBLE_COLON_CHAR);
        writer.append(date.toString());
        writer.append(JSON_DOUBLE_COLON_CHAR);
    }

    private void writeString (CharSequence string, StringWriter writer) {
        writer.append(JSON_DOUBLE_COLON_CHAR);
        writer.append(string);
        writer.append(JSON_DOUBLE_COLON_CHAR);
    }

    private void writeObject(Object object, StringWriter writer) {
        try {
            Class objectClass = object.getClass();
            Field[] declaredFields = objectClass.getDeclaredFields();
            writer.append(JSON_OBJECT_START_CHAR);
            boolean isFirst = true;
            for (Field field : declaredFields) {
                if (!isFirst) {
                    writer.append(JSON_ELEMENT_SEPARATOR_CHAR);
                }
                String fieldName = field.getName();
                Object fieldValue = null;
                if (field.isAccessible()) {
                    fieldValue = field.get(object);
                } else {
                    String fieldMethodName = (Boolean.class.isAssignableFrom(field.getType())? "is" : "get") + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method fieldMethod = objectClass.getDeclaredMethod(fieldMethodName);
                    if (fieldMethod != null) {
                        fieldValue = fieldMethod.invoke(object);
                    }
                }
                if (fieldValue != null) {
                    writer.append(JSON_DOUBLE_COLON_CHAR);
                    writer.append(fieldName);
                    writer.append(JSON_DOUBLE_COLON_CHAR);
                    writer.append(JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR);
                    write(fieldValue, writer);
                    isFirst = false;
                }
            }
            writer.append(JSON_OBJECT_END_CHAR);
        }
        catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private void writeDataElement (DataElement dataElement, StringWriter writer) {
        if (dataElement instanceof DataObject) {
            DataObject dataObject = (DataObject)dataElement;
            writer.append(JSON_OBJECT_START_CHAR);
            boolean isFirst = true;
            Set<String> keys = dataObject.properties();
            for (String key : keys) {
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
        else if (dataElement instanceof DataCollection) {
            writeIterable((Iterable)dataElement, writer);
        }
        else {
            writeObject(dataElement, writer);
        }
    }

    private void writeMap (Map map, StringWriter writer) {
        writer.append(JSON_OBJECT_START_CHAR);
        boolean isFirst = true;
        for (Object key : map.keySet()) {
            if (!isFirst) {
                writer.append(JSON_ELEMENT_SEPARATOR_CHAR);
            }
            writer.append(JSON_DOUBLE_COLON_CHAR);
            writer.append(key.toString());
            writer.append(JSON_DOUBLE_COLON_CHAR);
            writer.append(JSON_OBJECT_KEY_VALUE_SEPARATOR_CHAR);
            write(map.get(key), writer);
            isFirst = false;
        }
        writer.append(JSON_OBJECT_END_CHAR);
    }

    private void writeArray(Object[] objects, StringWriter writer) {
        writer.append(JSON_ARRAY_START_CHAR);
        boolean isFirst = true;
        for (Object object : objects) {
            if (!isFirst) {
                writer.append(JSON_ELEMENT_SEPARATOR_CHAR);
            }
            write(object, writer);
            isFirst = false;
        }
        writer.append(JSON_ARRAY_END_CHAR);
    }

    private void writeIterable(Iterable iterable, StringWriter writer) {
        writer.append(JSON_ARRAY_START_CHAR);
        boolean isFirst = true;
        for (Object object : iterable) {
            if (!isFirst) {
                writer.append(JSON_ELEMENT_SEPARATOR_CHAR);
            }
            write(object, writer);
            isFirst = false;
        }
        writer.append(JSON_ARRAY_END_CHAR);
    }
}