package com.example.movies.data.remoteSource;

import com.google.gson.annotations.SerializedName;

public class VideosResponse {
    @SerializedName("videos")
    private TrailersList trailersList;


    public VideosResponse(TrailersList trailersList) {
        this.trailersList = trailersList;
    }

    public TrailersList getTrailersList() {
        return trailersList;
    }

    @Override
    public String toString() {
        return "VideosResponse{" +
                "trailersList=" + trailersList +
                '}';
    }
}
