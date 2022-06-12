package com.example.fyp.Models;

import java.io.Serializable;

public class Support implements Serializable {
    String support,uid;

    public Support(){};

    public Support(String uid,String support) {
        this.uid=uid;
        this.support = support;
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
