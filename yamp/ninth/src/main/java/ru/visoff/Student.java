package ru.visoff;

import java.util.Objects;

public class Student extends Human {
    private String university;
    private String faculty;
    private String speciality;

    public Student(String surname, String name, String patronymic, int age, Sex sex, String university, String faculty,
            String speciality) {
        super(surname, name, patronymic, age, sex);
        this.university = university;
        this.faculty = faculty;
        this.speciality = speciality;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "Student{" +
                "university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", speciality='" + speciality + '\'' +
                ", " + super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Student student = (Student) o;
        return Objects.equals(university, student.university) && Objects.equals(faculty, student.faculty)
                && Objects.equals(speciality, student.speciality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), university, faculty, speciality);
    }
}
