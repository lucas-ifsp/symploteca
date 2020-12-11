package br.edu.ifsp.domain.entities.user;

public class Student extends User{
    private String course;

    public Student() {
    }

    public Student(String institutionalId, String name, String email, String phone, String course) {
        this(institutionalId, 0, name, email, phone, course);
    }

    public Student(String institutionalId, Integer numberOfBooksCheckedOut, String name, String email, String phone, String course) {
        super(institutionalId, numberOfBooksCheckedOut, name, email, phone);
        this.course = course;
    }

    @Override
    public String getUserType() {
        return "Estudante";
    }

    @Override
    public int getLimitOfBooksToCheckOut() {
        return 3;
    }

    @Override
    public int getCheckoutTimeLimitInDays() {
        return 7;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "course='" + course + '\'' +
                "} " + super.toString();
    }
}
