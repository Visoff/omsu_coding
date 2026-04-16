package ru.visoff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class House implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cadastralNumber;
    private String address;
    private Person elder;
    private List<Flat> flats;

    public House(String cadastralNumber, String address, Person elder, List<Flat> flats) {
        if (cadastralNumber == null) throw new NullPointerException("Cadastral number can't be null");
        if (address == null) throw new NullPointerException("Address can't be null");
        if (elder == null) throw new NullPointerException("Elder can't be null");
        if (flats == null) throw new NullPointerException("Flats list can't be null");
        this.cadastralNumber = cadastralNumber;
        this.address = address;
        this.elder = elder;
        this.flats = new ArrayList<>(flats);
    }

    public House() {
        this("0000", "Unknown", new Person(), new ArrayList<>());
    }

    public String getCadastralNumber() { return cadastralNumber; }
    public void setCadastralNumber(String cadastralNumber) {
        if (cadastralNumber == null) throw new NullPointerException("Cadastral number can't be null");
        this.cadastralNumber = cadastralNumber;
    }
    public String getAddress() { return address; }
    public void setAddress(String address) {
        if (address == null) throw new NullPointerException("Address can't be null");
        this.address = address;
    }
    public Person getElder() { return elder; }
    public void setElder(Person elder) {
        if (elder == null) throw new NullPointerException("Elder can't be null");
        this.elder = elder;
    }
    public List<Flat> getFlats() { return new ArrayList<>(flats); }
    public void setFlats(List<Flat> flats) {
        if (flats == null) throw new NullPointerException("Flats list can't be null");
        this.flats = new ArrayList<>(flats);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) return false;
        House house = (House) o;
        return cadastralNumber.equals(house.cadastralNumber) &&
               address.equals(house.address) &&
               elder.equals(house.elder) &&
               flats.equals(house.flats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cadastralNumber, address, elder, flats);
    }
}
