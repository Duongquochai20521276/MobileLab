package com.example.lab2_customadapter;

public class Staff extends Employee {
    private String name;

    public Staff() {
    }

    public Staff(String name) {
        super(name);
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
