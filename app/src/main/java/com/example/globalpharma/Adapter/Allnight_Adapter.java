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

import com.example.globalpharma.Model.Ville;
import com.example.globalpharma.R;

import java.util.List;

public class Allnight_Adapter extends RecyclerView.Adapter<Allnight_Adapter.AllViewsHolder> {
    Context mContext;
    List<Ville> mData;

    public Allnight_Adapter(Context mContext,List<Ville> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public Allnight_Adapter.AllViewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout= LayoutInflater.from(mContext).inflate(R.layout.item_allnight_pharmacy,parent,false);
        return new Allnight_Adapter.AllViewsHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull Allnight_Adapter.AllViewsHolder holder, int position) {
        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.placename.setText(mData.get(position).getNom());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AllViewsHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayout;
        public TextView placename;
        public TextView Vicinity;
        public AllViewsHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.relativelayout);
            placename=itemView.findViewById(R.id.placeName);
            Vicinity=itemView.findViewById(R.id.Vicinity);
        }
    }
}
