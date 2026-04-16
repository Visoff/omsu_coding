package ru.visoff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Flat implements Serializable {
    private static final long serialVersionUID = 1L;

    private int number;
    private double area;
    private List<Person> owners;

    public Flat(int number, double area, List<Person> owners) {
        if (owners == null) throw new NullPointerException("Owners list can't be null");
        this.number = number;
        this.area = area;
        this.owners = new ArrayList<>(owners);
    }

    public Flat() {
        this(0, 0.0, new ArrayList<>());
    }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }
    public List<Person> getOwners() { return new ArrayList<>(owners); }
    public void setOwners(List<Person> owners) {
        if (owners == null) throw new NullPointerException("Owners list can't be null");
        this.owners = new ArrayList<>(owners);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flat)) return false;
        Flat flat = (Flat) o;
        return number == flat.number &&
               Double.compare(flat.area, area) == 0 &&
               owners.equals(flat.owners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, area, owners);
    }
}
