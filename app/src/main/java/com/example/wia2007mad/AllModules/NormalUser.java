package com.example.wia2007mad.AllModules;

public class NormalUser {
    private String username,email,phonenumber,imageurl;


    public NormalUser(){};
    public NormalUser(String username, String email, String phonenumber, String imageurl) {
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.imageurl = imageurl;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
