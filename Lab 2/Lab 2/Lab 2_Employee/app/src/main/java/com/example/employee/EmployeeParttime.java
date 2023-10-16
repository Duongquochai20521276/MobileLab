package com.example.employee;

public class EmployeeParttime extends Employee{
    public EmployeeParttime() {
    }

    public EmployeeParttime(String ID, String name) {
        super(ID, name);
    }

    @Override
    public double tinhLuong() {
        return 150.0;
    }

    @Override
    public String toString() {
        return super.toString() + "PartTime=" + tinhLuong();
    }
}
