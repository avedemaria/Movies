package com.example.movies.data.localSource;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movies.data.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DB_NAME = "movie.db";
    private static MovieDatabase movieDatabase = null;

    public static MovieDatabase getMovieDatabase(Application application) {
        if (movieDatabase == null) {
            movieDatabase = Room.databaseBuilder(application, MovieDatabase.class, DB_NAME)
                    .build();
        }

        return movieDatabase;
    }

    abstract MoviesDao moviesDao ();

}
