package com.zys.bean;

import java.io.Serializable;

public class UserBean implements Serializable {
    private Integer Id;
    private String Name;
    private String Age;
    private String Sex;


    public UserBean() {
    }

    public UserBean(int id, String name, String age, String sex) {
        Id = id;
        Name = name;
        Age = age;
        Sex = sex;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Age='" + Age + '\'' +
                ", Sex='" + Sex + '\'' +
                '}';
    }
}
