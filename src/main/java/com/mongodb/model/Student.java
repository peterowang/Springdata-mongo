package com.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/14.
 * {
 * "_id" : ObjectId("59ba473240b5bdf4e5481d4b"),
 * "province" : "海南",
 * "age" : 21.0,
 * "subjects" : [
 * {
 * "name" : "语文",
 * "score" : 53.0
 * },
 * {
 * "name" : "数学",
 * "score" : 27.0
 * },
 * {
 * "name" : "英语",
 * "score" : 35.0
 * }
 * ],
 * "name" : "刘雨"
 * }
 */
@Document(collection = "student")
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
