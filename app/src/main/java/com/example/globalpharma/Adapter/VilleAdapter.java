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

public class VilleAdapter extends RecyclerView.Adapter<VilleAdapter.VilleViewHolder> {

    Context mContext;
    List<Ville> mData;

    public VilleAdapter(Context mContext,List<Ville> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public VilleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout= LayoutInflater.from(mContext).inflate(R.layout.item_ville,parent,false);
        return new VilleViewHolder(layout);

    }

    @Override
    public void onBindViewHolder(@NonNull VilleViewHolder holder, int position) {
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.nomVille.setText(mData.get(position).getNom());
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class VilleViewHolder extends RecyclerView.ViewHolder {
        TextView nomVille;
        RelativeLayout container;
        public VilleViewHolder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.container);
            nomVille=itemView.findViewById(R.id.NomVille);
        }
    }
}
