package com.example.application.travelbuddyapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    private List<Reviews> reviewsList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, review_text, review_rating;
        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            review_text = (TextView) view.findViewById(R.id.review_text);
            review_rating = (TextView) view.findViewById(R.id.review_rating);
        }
    }

    public ReviewsAdapter(List<Reviews> moviesList) {
        this.reviewsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reviews reviews = reviewsList.get(position);
        holder.username.setText(reviews.getReview_username());
        //holder.review_text.setText(reviews);
        holder.review_rating.setText(String.valueOf(reviews.getReview_rating()));
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }
}