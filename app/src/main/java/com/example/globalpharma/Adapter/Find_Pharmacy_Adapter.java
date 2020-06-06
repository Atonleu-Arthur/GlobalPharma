package com.example.globalpharma.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.Pharmacy_Location;
import com.example.globalpharma.R;

import java.util.List;

public class Find_Pharmacy_Adapter extends RecyclerView.Adapter<Find_Pharmacy_Adapter.FindViewHolder> {
    Context mContext;
    List<Pharmacy_Location> mData;

    public Find_Pharmacy_Adapter(Context mContext,List<Pharmacy_Location> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout= LayoutInflater.from(mContext).inflate(R.layout.item_allnight_pharmacy,parent,false);
        return new FindViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull FindViewHolder holder, int position) {
        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.placename.setText(mData.get(position).getPharmacy().getPlaceName());
        holder.Vicinity.setText(mData.get(position).getPharmacy().getVicinoty());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class FindViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayout;
        public TextView placename;
        public TextView Vicinity;
        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.relativelayout);
            placename=itemView.findViewById(R.id.placeName);
            Vicinity=itemView.findViewById(R.id.Vicinity);
        }
    }
}
