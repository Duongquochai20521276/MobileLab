package com.example.lab2_customadapter;

public class Manager extends Employee {
    private String name;

    public Manager() {

    }

    public Manager(String name) {
        this.name = name;
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
        return true;
    }
}
