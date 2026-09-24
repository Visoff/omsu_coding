package ru.visoff;

import java.util.Objects;

public class Human {
    private String surname;
    private String name;
    private String patronymic;
    private int age;
    private Sex sex;

    public Human(String surname, String name, String patronymic, int age, Sex sex) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.sex = sex;
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

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Human{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Human human = (Human) o;
        return age == human.age && Objects.equals(surname, human.surname) && Objects.equals(name, human.name)
                && Objects.equals(patronymic, human.patronymic) && sex == human.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic, age, sex);
    }
}
