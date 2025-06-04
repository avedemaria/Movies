package com.example.movies.data.remoteSource;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewCard implements Serializable {

    @SerializedName("review")
    private String review;
    @SerializedName("author")
    private String name;
    @SerializedName("type")
    private String type;


    public ReviewCard(String review, String name, String type) {
        this.review = review;
        this.name = name;
        this.type = type;
    }

    public String getReview() {
        return review;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
