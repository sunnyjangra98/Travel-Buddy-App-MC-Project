package com.example.application.travelbuddyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class StayAdapter extends RecyclerView.Adapter<StayAdapter.StayViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Stay> stayList;
    private OnItemClickListner mListner;

    //getting the context and product list with constructor
    public StayAdapter(Context mCtx, List<Stay> stayList) {
        this.mCtx = mCtx;
        this.stayList = stayList;
    }

    public interface OnItemClickListner{
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }

    @Override
    public StayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.stay_list_item, null);
        return new StayViewHolder(view, mListner);
    }

    @Override
    public void onBindViewHolder(StayViewHolder holder, int position) {
        //getting the product of the specified position
        Stay stay = stayList.get(position);

        //binding the data with the viewholder view
        holder.textView_stay.setText(stay.getStay_name());
        holder.textView_person.setText(stay.getStay_person());
        holder.textView_brief.setText(String.valueOf(stay.gethostDate()));
        holder.textView_rating.setText(String.valueOf(stay.getRating()));
        Glide.with(mCtx).load(stay.getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return stayList.size();
    }


    class StayViewHolder extends RecyclerView.ViewHolder {

        TextView textView_person, textView_stay, textView_brief, textView_rating;
        ImageView imageView;

        public StayViewHolder(View itemView, final OnItemClickListner listner) {
            super(itemView);

            textView_stay = itemView.findViewById(R.id.textView_stay);
            textView_person = itemView.findViewById(R.id.textView_person);
            textView_brief = itemView.findViewById(R.id.stay_brief);
            textView_rating = itemView.findViewById(R.id.rating_text);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listner.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}