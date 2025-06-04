package com.example.movies.presentation.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    static class MoviesViewHolder extends RecyclerView.ViewHolder {
        private final ImageView poster;
        private final TextView textViewKpRating, textViewIMDBRating;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.imageViewPoster);
            textViewKpRating = itemView.findViewById(R.id.textViewKpRating);
            textViewIMDBRating = itemView.findViewById(R.id.textViewIMDBRating);
        }
    }

    private onReachEndListener onReachEndListener;

    private onMovieClickListener onMovieClickListener;

    public void setOnMovieClickListener(MoviesAdapter.onMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setOnReachEndListener(MoviesAdapter.onReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }
    private List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.itemview_movie,
                parent,
                false
        );
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull MoviesViewHolder viewHolder,int position){
        Movie movie = movies.get(position);

        Glide.with(viewHolder.itemView).
                load(movie.getPoster().getUrl()).
                into(viewHolder.poster);

        double ratingKp = movie.getRating().getKpRating();
        double ratingImdb = movie.getRating().getIMDBRating();

        int backgroundIdKp;

        if (ratingKp>7) {
            backgroundIdKp = R.drawable.circle_green;
        } else if (ratingKp>5) {
            backgroundIdKp = R.drawable.circle_yellow;
        } else {
            backgroundIdKp = R.drawable.circle_red;
        }

        Drawable backgroundKp = ContextCompat.getDrawable(viewHolder.itemView.getContext(), backgroundIdKp);
        viewHolder.textViewKpRating.setBackground(backgroundKp);

        int backgroundIdImdb;
        if (ratingImdb>7) {
            backgroundIdImdb = R.drawable.circle_green;
        } else if (ratingImdb>5) {
            backgroundIdImdb = R.drawable.circle_yellow;
        } else {
            backgroundIdImdb = R.drawable.circle_red;
        }


        Drawable backgroundImdb = ContextCompat.getDrawable(viewHolder.itemView.getContext(), backgroundIdImdb);
        viewHolder.textViewIMDBRating.setBackground(backgroundImdb);

        viewHolder.textViewKpRating.setText(String.valueOf(ratingKp).substring(0, 3)+ "\n   KP  ");
        viewHolder.textViewIMDBRating.setText(ratingImdb + "\nImdB");

        if (position >= movies.size()-10 && onReachEndListener!=null) {
            onReachEndListener.onReachEnd();
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onMovieClick(movie);
                }
            }
        });
    }

    interface onReachEndListener {
        void onReachEnd();
    }

    interface onMovieClickListener {
        void onMovieClick(Movie movie);
    }

    @Override
    public int getItemCount () {
        return movies.size();
    }
}

