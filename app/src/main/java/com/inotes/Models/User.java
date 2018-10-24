package com.inotes.Models;

public class User {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String usertype;
    public String phone;
    public String course;
    //public String usertype;

    public User(){

    }

    public User(String name, String usertype, String phone,String course) {
        this.name = name;
        this.usertype=usertype;
        this.phone = phone;
        this.course=course;
    }

    public User(String name, String usertype, String phone) {
        this.name = name;
        this.usertype=usertype;
        this.phone = phone;
    }
}