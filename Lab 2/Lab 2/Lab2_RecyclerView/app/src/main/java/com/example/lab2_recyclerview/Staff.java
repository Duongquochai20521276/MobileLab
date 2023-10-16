package com.example.lab2_recyclerview;

public class Staff extends Employee {
    private String name;

    public Staff(String name) {
        super(name);
    }

    public Staff() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isManager() {
        return false;
    }
}
