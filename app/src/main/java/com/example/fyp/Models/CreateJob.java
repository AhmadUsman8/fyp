package com.example.fyp.Models;

import java.io.Serializable;

public class CreateJob implements Serializable {
    String id,title,description,budget,time;
    String uid,service;

    public CreateJob(){}

    public CreateJob(String id,String uid,String title,String description, String budget,String time,String service){
        this.id=id;
        this.service=service;
        this.uid=uid;
        this.title=title;
        this.description=description;
        this.budget=budget;
        this.time=time;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
