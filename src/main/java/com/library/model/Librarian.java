package com.library.model;

public class Librarian extends Person {

    private String employeeId;

    public Librarian(int id, String name, String email, String employeeId) {
        super(id, name, email);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String getRole() {
        return "Librarian";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Employee ID: " + employeeId);
    }
}