package ru.visoff;

public class Group {
    public Integer id;
    public Integer[] data;

    public Group(Integer id, Integer... data) throws NullPointerException, IllegalArgumentException {
        if (id == null)
            throw new NullPointerException("Id can't be null");
        if (id < 0)
            throw new IllegalArgumentException("Id can't be negative");
        if (data == null)
            throw new NullPointerException("Data can't be null");
        this.id = id;
        this.data = data.clone();
    }

    public Group() {
        this(0);
    }

    public Integer[] getData() {
        return data.clone();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) throws NullPointerException, IllegalArgumentException {
        if (id == null)
            throw new NullPointerException("Id can't be null");
        if (id < 0)
            throw new IllegalArgumentException("Id can't be negative");
        this.id = id;
    }

    public void setData(Integer[] data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Data can't be null");
        this.data = data.clone();
    }

    public int size() {
        return data.length;
    }
}
