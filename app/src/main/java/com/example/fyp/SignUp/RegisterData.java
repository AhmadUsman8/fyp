package com.example.fyp.SignUp;


public class RegisterData {
    public String fname,lname,email;

    public RegisterData(){}

    public RegisterData(String fname,String lname,String email){
        //this.choice=choice;
        this.fname=fname;
        this.lname=lname;
        this.email=email;
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
