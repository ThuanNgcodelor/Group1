package com.example.cp2396g11gr1.model.Area;

public class Area {
    private int id;
    private String name;

    public Area(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Area() {
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
