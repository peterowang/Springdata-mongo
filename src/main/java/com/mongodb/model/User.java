package com.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by BFD-593 on 2017/9/13.
 * 数据结构
 * {
 * "_id" : ObjectId("59b8fde09c0fa0696ad624b9"),
 * "name" : "liu",
 * "age" : 1.0,
 * "sex" : "女"
 * }
 */
@Document(collection = "user")//不写也可以，默认是class名开头小写驼峰规则
public class User {
    @Id
    private long id;//用id注解标识,就会将mongodb中的_id字段映射到实体的id属性中
    @Field("name")//不写也可以
    private String name;
    @Field("age")
    private String age;
    @Field("sex")
    private String sex;
    @Transient//默认情况下所有的私有字段都映射到文档，该注解标识的字段从存储在数据库中的字段列中排除（即该字段不保存到 mongodb）
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
