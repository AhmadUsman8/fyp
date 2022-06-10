package com.example.fyp;

import java.io.Serializable;

public class CreateJob implements Serializable {
    String title,description,budget,time;
    String uid;

    public CreateJob(){}

    public CreateJob(String uid,String title,String description, String budget,String time){

        this.uid=uid;
        this.title=title;
        this.description=description;
        this.budget=budget;
        this.time=time;
    }

    public void setTime(String time) { this.time = time; }
    public void setUId(String uid){
        this.uid=uid;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setBudget(String budget){
        this.budget=budget;
    }

    public String getUId(){return uid;}
    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public String getBudget(){return budget;}
    public String getTime() { return time;}
}
