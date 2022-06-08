package com.example.fyp.SignUp;

import java.io.Serializable;

public class WorkerData extends UserData {
    private String phone;

    public WorkerData() {
    }

    public WorkerData(String fname, String lname, String email, String phone) {
        super(fname, "", email);
        this.setType("worker");
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
