package com.mongodb.model;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/14.
 */
public class Student {
    @Id
    private String id;
    private String province;
    private int age;
    private List<String> subjects;
    private String name;

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
