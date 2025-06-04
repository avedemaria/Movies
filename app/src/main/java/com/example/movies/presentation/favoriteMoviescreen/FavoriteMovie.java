package com.example.movies.presentation.favoriteMoviescreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.data.Movie;
import com.example.movies.R;
import com.example.movies.presentation.adapters.MoviesAdapter;
import com.example.movies.presentation.detailedInfoScreen.DetailedInfo;

import java.util.List;

public class FavoriteMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.favoriteMovies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.favoriteMovies);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        MoviesAdapter moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);

        FavoriteMoviesViewModel viewModel = new ViewModelProvider(this)
                .get(FavoriteMoviesViewModel.class);

        viewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });

        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.onMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = DetailedInfo.newIntent(FavoriteMovie.this, movie);
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent (Context context) {
        Intent intent = new Intent (context, FavoriteMovie.class);
        return intent;
    }
}