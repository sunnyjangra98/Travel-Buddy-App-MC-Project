package com.example.application.travelbuddyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.TravelViewHolder>{
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<travel> travelList;

    //getting the context and product list with constructor
    public TravelAdapter(Context mCtx, List<travel> travelList) {
        this.mCtx = mCtx;
        this.travelList = travelList;
    }

    @Override
    public TravelAdapter.TravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.travel_list_item, null);
        return new TravelAdapter.TravelViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TravelAdapter.TravelViewHolder holder, int position) {
        //getting the product of the specified position
        travel trvl = travelList.get(position);

        //binding the data with the viewholder views
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(trvl.getImage()));

        holder.textView_place.setText(trvl.getplace());
        holder.textView_host.setText(trvl.gethost());
        holder.textView_detail.setText(String.valueOf(trvl.getdetails()));
        holder.textView_goingno.setText(String.valueOf(trvl.getno_of_going()));

    }


    @Override
    public int getItemCount() {
        return travelList.size();
    }


    class TravelViewHolder extends RecyclerView.ViewHolder {

        TextView textView_host, textView_place, textView_detail, textView_goingno;
        ImageView imageView;

        public TravelViewHolder(View itemView) {
            super(itemView);

            textView_place =itemView.findViewById(R.id.travel_place);
            textView_host =itemView.findViewById(R.id.travel_host);
            textView_detail = itemView.findViewById(R.id.travel_details);
            textView_goingno = itemView.findViewById(R.id.travel_goingno);
            imageView = itemView.findViewById(R.id.travel_hostpic);

        }
    }
}
