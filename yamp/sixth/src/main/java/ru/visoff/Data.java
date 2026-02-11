package ru.visoff;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Data implements Iterable<Integer> {
    public String name;
    public Group[] data;

    public Data(String name, Group... data) throws NullPointerException {
        if (name == null)
            throw new NullPointerException("Name can't be null");
        if (data == null)
            throw new NullPointerException("Data can't be null");
        for (Group g : data) {
            if (g == null)
                throw new NullPointerException("Group can't be null");
        }
        this.name = name;
        this.data = data.clone();
    }

    public Data() {
        this("Unknown", new Group[0]);
    }

    public String getName() {
        return name;
    }

    public Group[] getData() {
        return data.clone();
    }

    public void setName(String name) throws NullPointerException {
        if (name == null)
            throw new NullPointerException("Name can't be null");
        this.name = name;
    }

    public void setData(Group[] data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Data can't be null");
        this.data = data.clone();
    }

    public int size() {
        return data.length;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int groupIndex = 0;
            private int elementIndex = 0;
            private Integer[] currentGroup = (data.length > 0) ? data[0].getData() : new Integer[0];

            @Override
            public boolean hasNext() {
                while (groupIndex < data.length && (currentGroup == null || elementIndex >= currentGroup.length)) {
                    groupIndex++;
                    if (groupIndex < data.length) {
                        currentGroup = data[groupIndex].getData();
                        elementIndex = 0;
                    }
                }
                return groupIndex < data.length && elementIndex < currentGroup.length;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentGroup[elementIndex++];
            }
        };
    }
}
