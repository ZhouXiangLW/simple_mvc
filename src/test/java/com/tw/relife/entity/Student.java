package com.tw.relife.entity;

public class Student {
    private String name;
    private Integer height;

    public Student(String name, Integer height) {
        this.name = name;
        this.height = height;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public Student setHeight(Integer height) {
        this.height = height;
        return this;
    }
}
