package org.neogroup.warp.data;

import java.util.*;
import java.util.function.Consumer;

public class DataCollection implements DataElement,Iterable {

    private List<Object> elements;

    public DataCollection() {
        this.elements = new ArrayList<>();
    }

    public DataCollection add(Object value) {
        elements.add(value);
        return this;
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public DataCollection clear() {
        elements.clear();
        return this;
    }

    @Override
    public Iterator<Object> iterator() {
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
