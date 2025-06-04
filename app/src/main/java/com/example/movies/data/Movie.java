package com.example.movies.data;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.movies.data.remoteSource.Poster;
import com.example.movies.data.remoteSource.Rating;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity (tableName = "favorite_movies")
public class Movie implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("year")
    private int year;
    @SerializedName("description")
    private String description;
    @Embedded
    @SerializedName("poster")
    private Poster poster;
    @Embedded
    @SerializedName("rating")
    private Rating rating;

    public Movie(int id, String name, int year, String description, Poster poster, Rating rating) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.description = description;
        this.poster = poster;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public Poster getPoster() {
        return poster;
    }

    public Rating getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", poster=" + poster +
                ", rating=" + rating +
                '}';
    }
}
