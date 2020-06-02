package com.example.globalpharma.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.R;

public class Find_Pharmacy_Adapter extends RecyclerView.Adapter<Find_Pharmacy_Adapter.FindViewHolder> {


    @NonNull
    @Override
    public Find_Pharmacy_Adapter.FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Find_Pharmacy_Adapter.FindViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FindViewHolder extends RecyclerView.ViewHolder {
         RelativeLayout relativeLayout;
          TextView placeName;
          TextView vacinty;
        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName=itemView.findViewById(R.id.placeName);
            vacinty=itemView.findViewById(R.id.Vicinity);
            relativeLayout=itemView.findViewById(R.id.relativelayout);
        }
    }
}
