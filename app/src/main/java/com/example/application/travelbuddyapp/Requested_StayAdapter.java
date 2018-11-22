package com.example.application.travelbuddyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class Requested_StayAdapter extends RecyclerView.Adapter<Requested_StayAdapter.MyViewHolder>{

    private List<Requested_Stay> requestedList;
    private Context mCtx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView stay_name, city, stay_person, status;
        public MyViewHolder(View itemView) {
            super(itemView);
            stay_name = itemView.findViewById(R.id.requested_stay_name);
            city = itemView.findViewById(R.id.requested_city);
            stay_person = itemView.findViewById(R.id.host_name);
            status = itemView.findViewById(R.id.status);
        }
    }

    public Requested_StayAdapter(Context mCtx, List<Requested_Stay> requestsList) { this.requestedList = requestsList; this.mCtx=mCtx; }

    @Override
    public Requested_StayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_stay_list_item, parent, false);
        return new Requested_StayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Requested_Stay requests = requestedList.get(position);
        holder.stay_name.setText(requests.getRequested_stay_name());
        holder.city.setText(requests.getRequested_city());
        holder.stay_person.setText(requests.getRequested_stay_person());
        holder.status.setText(requests.getRequested_status());
    }

    @Override
    public int getItemCount() { return requestedList.size(); }
}
