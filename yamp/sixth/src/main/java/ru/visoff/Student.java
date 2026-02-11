package ru.visoff;

public class Student extends Human {
    private String faculty;

    Student(String surname, String name, String patronymic, Integer age, String faculty) throws NullPointerException, IllegalArgumentException {
        super(surname, name, patronymic, age);
        if (faculty == null)
            throw new NullPointerException("Faculty can't be null");
        this.faculty = faculty;
    }

    Student() {
        this("Unknown", "Unknown", "Unknown", 0, "Unknown");
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) throws NullPointerException {
        if (faculty == null)
            throw new NullPointerException("Faculty can't be null");
        this.faculty = faculty;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Student))
            return false;
        if (!super.equals(other))
            return false;

        Student student = (Student) other;
        return this.faculty.equals(student.faculty);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + faculty.hashCode();
    }
}
