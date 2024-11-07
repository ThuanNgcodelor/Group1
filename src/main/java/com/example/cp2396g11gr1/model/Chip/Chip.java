package com.example.cp2396g11gr1.model.Chip;

public class Chip {
    private int id;
    private String name;

    public Chip(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Chip() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
