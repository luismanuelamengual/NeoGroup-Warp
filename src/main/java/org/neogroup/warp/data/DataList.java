package org.neogroup.warp.data;

import java.util.*;
import java.util.function.Consumer;

public class DataList<T extends Object> implements DataElement,Iterable<T> {

    private List<T> elements;

    public DataList() {
        this.elements = new ArrayList<>();
    }

    public DataList add(T value) {
        elements.add(value);
        return this;
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public DataList clear() {
        elements.clear();
        return this;
    }

    @Override
    public Iterator<T> iterator() {
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
