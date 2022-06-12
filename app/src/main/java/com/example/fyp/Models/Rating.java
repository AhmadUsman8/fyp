package com.example.fyp.Models;

import java.io.Serializable;

public class Rating implements Serializable {
    String feedback,uid;
    Float rating;

    public Rating(){ }

    public Rating(String uid, Float rating, String feedback) {
        this.uid = uid;
        this.rating = rating;
        this.feedback=feedback;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
