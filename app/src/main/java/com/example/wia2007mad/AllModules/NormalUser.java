package com.example.wia2007mad.AllModules;

public class NormalUser {
    private String uid,username,email,phone_number,imageurl,role;


    public NormalUser(){};
    public NormalUser(String uid,String username, String email, String phone_number, String imageurl,String role) {
        this.uid=uid;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.imageurl = imageurl;
        this.role=role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
