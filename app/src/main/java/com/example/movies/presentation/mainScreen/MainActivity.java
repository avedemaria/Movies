package com.example.movies.presentation.mainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.example.movies.presentation.detailedInfoScreen.DetailedInfo;
import com.example.movies.presentation.favoriteMoviescreen.FavoriteMovie;
import com.example.movies.presentation.adapters.MoviesAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private RecyclerView recyclerViewMovies;

    private MoviesAdapter moviesAdapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initFields();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewMovies.setLayoutManager(layoutManager);
        moviesAdapter = new MoviesAdapter();
        recyclerViewMovies.setAdapter(moviesAdapter);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getMoviesLD().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                moviesAdapter.setMovies(movies);
            }
        });
        viewModel.getIsProgressVisible().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isProgressVisible) {
                if (isProgressVisible) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


        moviesAdapter.setOnReachEndListener(new MoviesAdapter.onReachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadMovies();
            }
        });

        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.onMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = DetailedInfo.newIntent(MainActivity.this, movie);
                startActivity(intent);
            }
        });

    }


    private void initFields() {
        progressBar = findViewById(R.id.progressBar);
        recyclerViewMovies = findViewById(R.id.recycleViewMovies);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_favMovies) {
            Intent intent = FavoriteMovie.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}