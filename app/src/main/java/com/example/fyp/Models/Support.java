package com.example.fyp.Models;

import java.io.Serializable;

public class Support implements Serializable {
    String subject,support,uid;

    public Support(){};

    public Support(String uid,String subject,String support) {
        this.uid=uid;
        this.subject=subject;
        this.support = support;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
}
