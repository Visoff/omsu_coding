package ru.visoff;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthDate;

    public Person(String surname, String name, String patronymic, LocalDate birthDate) {
        if (surname == null) throw new NullPointerException("Surname can't be null");
        if (name == null) throw new NullPointerException("Name can't be null");
        if (patronymic == null) throw new NullPointerException("Patronymic can't be null");
        if (birthDate == null) throw new NullPointerException("Birth date can't be null");
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public Person() {
        this("Unknown", "Unknown", "Unknown", LocalDate.of(2000, 1, 1));
    }

    public String getSurname() { return surname; }
    public void setSurname(String surname) {
        if (surname == null) throw new NullPointerException("Surname can't be null");
        this.surname = surname;
    }
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null) throw new NullPointerException("Name can't be null");
        this.name = name;
    }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) {
        if (patronymic == null) throw new NullPointerException("Patronymic can't be null");
        this.patronymic = patronymic;
    }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) throw new NullPointerException("Birth date can't be null");
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return surname.equals(person.surname) &&
               name.equals(person.name) &&
               patronymic.equals(person.patronymic) &&
               birthDate.equals(person.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic, birthDate);
    }
}
