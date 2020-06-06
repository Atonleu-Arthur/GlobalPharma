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

public class VilleAdapter extends RecyclerView.Adapter<VilleAdapter.VilleViewHolder> {

    Context mContext;
    List<Pharmacy_Location> mData;

    public VilleAdapter(Context mContext,List<Pharmacy_Location> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public VilleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout= LayoutInflater.from(mContext).inflate(R.layout.item_allnight_pharmacy,parent,false);
        return new VilleViewHolder(layout);

    }

    @Override
    public void onBindViewHolder(@NonNull VilleViewHolder holder, int position) {
        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.placename.setText(mData.get(position).getPharmacy().getPlaceName());
        holder.Vicinity.setText(mData.get(position).getPharmacy().getVicinoty());
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class VilleViewHolder extends RecyclerView.ViewHolder {
        TextView nomVille;
        RelativeLayout container;
        public RelativeLayout relativeLayout;
        public TextView placename;
        public TextView Vicinity;
        public VilleViewHolder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.container);
            nomVille=itemView.findViewById(R.id.NomVille);
            relativeLayout=itemView.findViewById(R.id.relativelayout);
            placename=itemView.findViewById(R.id.placeName);
            Vicinity=itemView.findViewById(R.id.Vicinity);
        }
    }
}
