package com.example.employee;

import java.io.Serializable;

public class Employee implements Serializable {
    private String ID;
    private String name;

    public Employee() {

    }

    public Employee(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double tinhLuong() {
        return 0.0;
    }

    public String toString() {
        return this.getID() + " - "
                + this.getName() + " -->";
    }
}
