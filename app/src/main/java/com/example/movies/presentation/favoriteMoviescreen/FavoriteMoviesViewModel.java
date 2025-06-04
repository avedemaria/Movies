package com.example.movies.presentation.favoriteMoviescreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.data.Movie;
import com.example.movies.data.localSource.MovieDatabase;
import com.example.movies.data.localSource.MoviesDao;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private MoviesDao moviesDao;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MovieDatabase.getMovieDatabase(application).moviesDao();
    }


    public LiveData<List<Movie>> getFavoriteMovies() {
        return moviesDao.getFavoriteMovies();
    }


}
