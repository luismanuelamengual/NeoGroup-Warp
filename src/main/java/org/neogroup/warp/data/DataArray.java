package org.neogroup.warp.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class DataArray extends DataElement implements Iterable {

    private List<Object> elements;

    public DataArray() {
        elements = new ArrayList<>();
    }

    public DataArray add(String value) {
        elements.add(value);
        return this;
    }

    public DataArray add(int value) {
        elements.add(value);
        return this;
    }

    public DataArray add(float value) {
        elements.add(value);
        return this;
    }

    public DataArray add(double value) {
        elements.add(value);
        return this;
    }

    public DataArray add(boolean value) {
        elements.add(value);
        return this;
    }

    public DataArray add(DataElement value) {
        elements.add(value);
        return this;
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public DataArray clear() {
        elements.clear();
        return this;
    }

    @Override
    public Iterator iterator() {
        return elements.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        elements.forEach(action);
    }

    @Override
    public Spliterator spliterator() {
        return elements.spliterator();
    }
}
