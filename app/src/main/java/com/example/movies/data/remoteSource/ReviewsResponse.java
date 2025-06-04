package com.example.movies.data.remoteSource;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {

    @SerializedName("docs")
    private List<ReviewCard> reviews;

    public ReviewsResponse(List<ReviewCard> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewCard> getReviews() {
        return reviews;
    }
}
