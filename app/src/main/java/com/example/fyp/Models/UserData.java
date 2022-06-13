package com.example.fyp.Models;


import java.io.Serializable;

public class UserData implements Serializable {
    private String id,fname,lname,email,type="user";

    public UserData(){}

    public UserData(String id,String fname, String lname, String email) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
