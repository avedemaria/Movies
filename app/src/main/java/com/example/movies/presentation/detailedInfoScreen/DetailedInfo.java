package com.example.movies.presentation.detailedInfoScreen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.data.Movie;
import com.example.movies.R;
import com.example.movies.data.remoteSource.ReviewCard;
import com.example.movies.data.remoteSource.Trailer;
import com.example.movies.presentation.adapters.ReviewAdapter;
import com.example.movies.presentation.adapters.TrailerAdapter;

import java.util.List;

public class DetailedInfo extends AppCompatActivity {
    private static final String MOVIE_KEY = "movie";
    private ImageView poster, starOn, starOff;
    private TextView title, year, description;

    private DetailedInfoViewModel viewModel;

    private RecyclerView recyclerViewTrailers, recyclerViewReviews;

    private TrailerAdapter trailerAdapter;

    private ReviewAdapter reviewAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initFields();

        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE_KEY);

        Glide.with(this).load(movie.getPoster().getUrl()).into(poster);

        title.setText(movie.getName());
        year.setText(String.valueOf(movie.getYear()));
        description.setText(movie.getDescription());


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTrailers.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        recyclerViewReviews.setLayoutManager(layoutManager1);

        trailerAdapter = new TrailerAdapter();
        recyclerViewTrailers.setAdapter(trailerAdapter);

        reviewAdapter = new ReviewAdapter();
        recyclerViewReviews.setAdapter(reviewAdapter);


        viewModel = new ViewModelProvider(this).get(DetailedInfoViewModel.class);
        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailersLD().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailerAdapter.setTrailers(trailers);
            }
        });


        viewModel.loadReviews(movie.getId());
        viewModel.getReviewsLD().observe(this, new Observer<List<ReviewCard>>() {
            @Override
            public void onChanged(List<ReviewCard> reviewCards) {
                reviewAdapter.setReviews(reviewCards);
            }
        });



        viewModel.getFavoriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if (movieFromDb != null) {
                    setViewByFavorite(true);
                    starOn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeFavoriteMovieFromDb(movie.getId());
                            setViewByFavorite(false);
                        }
                    });

                } else {
                    setViewByFavorite(false);
                    starOff.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.addFavoriteMovieToDb(movie);
                            setViewByFavorite(true);
                        }
                    });

                }
            }
        });


        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });


    }

    private void setViewByFavorite(boolean isFavorite) {
        if (isFavorite) {
            starOn.setVisibility(View.VISIBLE);
            starOff.setVisibility(View.GONE);
        } else {
            starOn.setVisibility(View.GONE);
            starOff.setVisibility(View.VISIBLE);
        }
    }


    private void initFields() {
        poster = findViewById(R.id.imageViewPosterDetailed);
        title = findViewById(R.id.textViewTitle);
        year = findViewById(R.id.textViewYear);
        description = findViewById(R.id.textViewDescription);
        recyclerViewTrailers = findViewById(R.id.recycleViewTrailers);
        recyclerViewReviews = findViewById(R.id.recycleViewReviews);
        starOn = findViewById(R.id.imageViewStar);
        starOff = findViewById(R.id.imageViewStarOff);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailedInfo.class);
        intent.putExtra(MOVIE_KEY, movie);
        return intent;
    }
}