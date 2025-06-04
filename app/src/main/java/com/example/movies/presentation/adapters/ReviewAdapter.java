package com.example.movies.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.data.remoteSource.ReviewCard;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView reviewText;

        private LinearLayout linearLayout;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.reviewerName);
            reviewText = itemView.findViewById(R.id.reviewText);
            linearLayout = itemView.findViewById(R.id.linearLayoutReview);
        }
    }

    private static final String TYPE_POSITIVE = "Позитивный";
    private static final String TYPE_NEGATIVE = "Негативный";

    private List<ReviewCard> reviews = new ArrayList<>();

    public void setReviews(List<ReviewCard> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_itemview,
                parent,
                false
        );
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder viewHolder, int position) {
        ReviewCard review = reviews.get(position);

        if (viewHolder.name != null) {
            viewHolder.name.setText(review.getName());
        }
        if (viewHolder.reviewText != null) {
            viewHolder.reviewText.setText(review.getReview());
        }

        String type = review.getType();
        int colorResId = android.R.color.background_light;

        if (type.equals(TYPE_POSITIVE)) {
            colorResId = R.color.green;
        } else if (type.equals(TYPE_NEGATIVE))
            colorResId = R.color.red;
        else {
            colorResId = R.color.orange;
        }

        int reviewColor = ContextCompat.getColor(viewHolder.itemView.getContext(), colorResId);
        viewHolder.linearLayout.setBackgroundColor(reviewColor);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}


