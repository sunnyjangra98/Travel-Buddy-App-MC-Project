package com.example.application.travelbuddyapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.MyViewHolder> {
    private List<Requests> requestsList;
    private Context mCtx;

    private boolean multiSelect = false;
    private ArrayList<Requests> selectedItems = new ArrayList<Requests>();
    private android.support.v7.view.ActionMode.Callback actionModeCallbacks = new android.support.v7.view.ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
            multiSelect = true;
            menu.add(0,1,1,"Accept");
            menu.add(0,2,2,"Reject");
            return true;
        }
        @Override public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) { return false; }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
            if ( item.getItemId() == 1 )
            {
                for (Requests intItem : selectedItems) {
                    //String username = intItem.username;
                    //Toast.makeText(mCtx, "VALUES "+intItem.stay_city+" "+intItem.requested_stay_id+" "+intItem.user_id, Toast.LENGTH_SHORT).show();
                    updateStatus(intItem,"1");
                }
            }
            else if ( item.getItemId() == 2 )
            {
                for (Requests intItem : selectedItems) {
                    //String username = intItem.username;
                    //Toast.makeText(mCtx, "VALUES "+intItem.stay_city+" "+intItem.requested_stay_id+" "+ intItem.user_id, Toast.LENGTH_SHORT).show();
                    updateStatus(intItem,"-1");
                }
            }
            selectedItems.clear();
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
            multiSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, number_of_traveller, dateToStay, status;
        ImageView user_image;
        RelativeLayout frameLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            frameLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
            username = itemView.findViewById(R.id.requested_person_username);
            number_of_traveller = itemView.findViewById(R.id.number_of_traveller);
            dateToStay = itemView.findViewById(R.id.request_date);
            status = itemView.findViewById(R.id.status);
            user_image = (ImageView) itemView.findViewById(R.id.requested_person_image);
        }

        void selectItem(Requests item) {
            if (multiSelect) {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item);
                    frameLayout.setBackgroundColor(Color.WHITE);
                } else {
                    selectedItems.add(item);
                    frameLayout.setBackgroundColor(Color.LTGRAY);
                }
            }
        }

        void update(final Requests value) {
            if (selectedItems.contains(value)) {
                frameLayout.setBackgroundColor(Color.LTGRAY);
            } else {
                frameLayout.setBackgroundColor(Color.WHITE);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);
                    selectItem(value);
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectItem(value);
                }
            });
        }
    }

    public RequestsAdapter(Context mCtx,List<Requests> requestsList) { this.requestsList = requestsList;this.mCtx=mCtx; }

    @Override
    public RequestsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_item, parent, false);
        return new RequestsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Requests requests = requestsList.get(position);
        holder.username.setText(requests.getUsername());
        holder.dateToStay.setText(requests.getDateToStay());
        holder.number_of_traveller.setText(requests.getNumberOfTraveller());
        holder.status.setText(requests.getStatus());
        holder.update(requestsList.get(position));
    }

    public void updateStatus(Requests req, String s)
    {
        //Toast.makeText(mCtx, "VALUES "+req.requested_stay_id+"  "+req.stay_city, Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Stay").child(req.stay_city);
        databaseReference.child(req.requested_stay_id).child("requests").child(req.user_id).child("status").setValue(s);
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(req.user_id);
        databaseReference2.child("requestedStay").child(req.stay_city).child(req.requested_stay_id).child("status").setValue(s);
        //Log.d("TAG","VALUES "+req.user_id+"  "+req.stay_city);
        Toast.makeText(mCtx, "VALUES "+req.requested_stay_id+"  "+req.stay_city+" "+req.user_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() { return requestsList.size(); }
}