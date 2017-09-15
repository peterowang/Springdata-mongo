package com.mongodb.model;

import org.springframework.data.annotation.Id;

/**
 * Created by BFD-593 on 2017/9/13.
 * 数据结构
 * {
 *  "_id" : ObjectId("59b8fde09c0fa0696ad624b9"),
 *  "name" : "liu",
 *  "age" : 1.0,
 *  "sex" : "女"
 * }
 */
public class User {
    @Id
    private String id;
    private String name;
    private String age;
    private String sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
