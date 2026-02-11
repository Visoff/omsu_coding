package ru.visoff;

public class Human {
    private String surname;
    private String name;
    private String patronymic;
    private Integer age;

    public Human(String surname, String name, String patronymic, Integer age)
            throws NullPointerException, IllegalArgumentException {
        if (surname == null)
            throw new NullPointerException("Surname can't be null");
        this.surname = surname;

        if (name == null)
            throw new NullPointerException("Name can't be null");
        this.name = name;

        if (patronymic == null)
            throw new NullPointerException("Patronymic can't be null");
        this.patronymic = patronymic;

        if (age == null)
            throw new NullPointerException("Age can't be null");
        if (age < 0)
            throw new IllegalArgumentException("Age can't be negative");
        this.age = age;
    }

    public Human() {
        this("Unknown", "Unknown", "Unknown", 0);
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Integer getAge() {
        return age;
    }

    public void setSurname(String surname) throws NullPointerException {
        if (surname == null)
            throw new NullPointerException("Surname can't be null");
        this.surname = surname;
    }

    public void setName(String name) throws NullPointerException {
        if (name == null)
            throw new NullPointerException("Name can't be null");
        this.name = name;
    }

    public void setPatronymic(String patronymic) throws NullPointerException {
        if (patronymic == null)
            throw new NullPointerException("Patronymic can't be null");
        this.patronymic = patronymic;
    }

    public void setAge(Integer age) throws NullPointerException, IllegalArgumentException {
        if (age == null)
            throw new NullPointerException("Age can't be null");
        if (age < 0)
            throw new IllegalArgumentException("Age can't be negative");
        this.age = age;
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Human))
            return false;

        Human human = (Human) other;
        return this.surname.equals(human.surname) &&
                this.name.equals(human.name) &&
                this.patronymic.equals(human.patronymic) &&
                this.age.equals(human.age);
    }

    @Override
    public int hashCode() {
        return surname.hashCode() + name.hashCode() + patronymic.hashCode() + age.hashCode();
    }
}
