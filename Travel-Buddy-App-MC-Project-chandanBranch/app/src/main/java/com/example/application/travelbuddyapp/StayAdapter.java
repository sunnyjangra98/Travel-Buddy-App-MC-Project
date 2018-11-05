package com.example.application.travelbuddyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StayAdapter extends RecyclerView.Adapter<StayAdapter.StayViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Stay> stayList;


    //getting the context and product list with constructor
    public StayAdapter(Context mCtx, List<Stay> stayList) {
        this.mCtx = mCtx;
        this.stayList = stayList;
    }

    @Override
    public StayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.stay_list_item, null);
        return new StayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StayViewHolder holder, int position) {
        //getting the product of the specified position
        Stay stay = stayList.get(position);

        //binding the data with the viewholder views
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(stay.getImage()));

        holder.textView_stay.setText(stay.getStay_name());
        holder.textView_person.setText(stay.getStay_person());
        holder.textView_brief.setText(String.valueOf(stay.getBrief()));
        holder.textView_rating.setText(String.valueOf(stay.getRating()));

    }


    @Override
    public int getItemCount() {
        return stayList.size();
    }


    class StayViewHolder extends RecyclerView.ViewHolder {

        TextView textView_person, textView_stay, textView_brief, textView_rating;
        ImageView imageView;

        public StayViewHolder(View itemView) {
            super(itemView);

            textView_stay =itemView.findViewById(R.id.textView_stay);
            textView_person =itemView.findViewById(R.id.textView_person);
            textView_brief = itemView.findViewById(R.id.stay_brief);
            textView_rating = itemView.findViewById(R.id.rating_text);
            imageView = itemView.findViewById(R.id.travel_hostpic);

        }
    }
}