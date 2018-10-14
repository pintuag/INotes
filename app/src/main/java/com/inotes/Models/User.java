package com.inotes.Models;

public class User {

    public String name, usertype, phone;
    //public String usertype;

    public User(){

    }

    public User(String name, String usertype, String phone) {
        this.name = name;
        this.usertype=usertype;
        this.phone = phone;
    }
}