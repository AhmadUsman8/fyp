package com.example.fyp.Models;

public class WorkerData extends UserData {
    private String id,phone,service;

    public WorkerData() {
    }

    public WorkerData(String id,String fname, String lname, String email, String phone, String service) {
        super(id,fname, "", email);
        this.setType("worker");
        this.id=id;
        this.phone = phone;
        this.service=service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
