package br.edu.ifsp.domain.entities.user;

public class Faculty extends User{
    private String department;

    public Faculty() {
    }

    public Faculty(String institutionalId, String name, String email, String phone, String department) {
        this(institutionalId, 0, name, email, phone, department);
    }

    public Faculty(String institutionalId, Integer numberOfBooksCheckedOut, String name, String email, String phone, String department) {
        super(institutionalId, numberOfBooksCheckedOut, name, email, phone);
        this.department = department;
    }

    @Override
    public String getUserType() {
        return "Funcionário";
    }

    @Override
    public int getLimitOfBooksToCheckOut() {
        return 5;
    }

    @Override
    public int getCheckoutTimeLimitInDays() {
        return 30;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "department='" + department + '\'' +
                "} " + super.toString();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
