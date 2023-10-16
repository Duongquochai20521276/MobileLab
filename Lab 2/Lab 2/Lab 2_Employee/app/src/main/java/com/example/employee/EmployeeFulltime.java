package com.example.employee;

public class EmployeeFulltime extends Employee{
    public EmployeeFulltime() {
    }

    public EmployeeFulltime(String ID, String name) {
        super(ID, name);
    }

    @Override
    public double tinhLuong() {
        return 500.0;
    }

    @Override
    public String toString() {
        return super.toString() + "FullTime=" + tinhLuong();
    }
}
