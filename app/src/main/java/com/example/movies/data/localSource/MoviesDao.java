package com.example.movies.data.localSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.data.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MoviesDao {



    @Query("SELECT * from favorite_movies")
    LiveData<List<Movie>> getFavoriteMovies();

    @Query("SELECT * from favorite_movies WHERE id=:id")
    LiveData<Movie> getMovieById(int id);

    @Insert
    Completable insertMovie(Movie movie);

    @Query("DELETE from favorite_movies WHERE id=:id")
    Completable deleteMovie(int id);


}
