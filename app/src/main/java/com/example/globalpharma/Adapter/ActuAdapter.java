package com.example.globalpharma.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.actuSante;
import com.example.globalpharma.R;

import java.util.List;

public class ActuAdapter extends RecyclerView.Adapter<ActuAdapter.ActuViewHolder>{

    Context mContext;
    List<actuSante> mData;

    public ActuAdapter(Context context, List<actuSante> data) {
        mContext = context;
        mData = data;
    }


    @NonNull
    @Override
    public ActuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_actu, viewGroup,false);
        return new ActuViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ActuViewHolder holder, int position) {

        holder.txt_titre.setText(mData.get(position).getTitre());
        holder.txt_description.setText(mData.get(position).getDescription());
        holder.image_actu.setImageResource(mData.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ActuViewHolder extends RecyclerView.ViewHolder{

        TextView txt_titre, txt_description;
        ImageView image_actu;


        public ActuViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_titre = itemView.findViewById(R.id.txt_titre);
            txt_description = itemView.findViewById(R.id.txt_description);
            image_actu = itemView.findViewById(R.id.image_actu);
        }
    }
}
