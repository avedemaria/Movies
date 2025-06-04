package com.example.movies.data.remoteSource;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @SerializedName("kp")
    private double kpRating;
    @SerializedName("imdb")
    private double IMDBRating;

    public Rating(double kpRating, double IMDBRating) {
        this.kpRating = kpRating;
        this.IMDBRating = IMDBRating;
    }

    public double getKpRating() {
        return kpRating;
    }

    public double getIMDBRating() {
        return IMDBRating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "kpRating='" + kpRating + '\'' +
                ", IMDBRating='" + IMDBRating + '\'' +
                '}';
    }
}
