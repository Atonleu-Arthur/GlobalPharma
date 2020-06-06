package com.example.globalpharma.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.Pharmacy_Location;
import com.example.globalpharma.R;

import java.util.List;

public class allNightAdapter extends RecyclerView.Adapter<allNightAdapter.VilleViewHolder> {

    Context mContext;
    List<Pharmacy_Location> mData;
    private UserListRecyclerClickListener mClickListener;


    public allNightAdapter(Context mContext, List<Pharmacy_Location> mData,UserListRecyclerClickListener clickListener)
    {
        this.mContext = mContext;
        this.mData = mData;
        mClickListener = clickListener;

    }
    @NonNull
    @Override
    public VilleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout= LayoutInflater.from(mContext).inflate(R.layout.item_allnightgpsfragment,parent,false);
        return new VilleViewHolder(layout,mClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull VilleViewHolder holder, int position) {
        holder.placename.setText(mData.get(position).getPharmacy().getPlaceName());
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class VilleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView placename;
        UserListRecyclerClickListener mClickListener;

        public VilleViewHolder(@NonNull View itemView, UserListRecyclerClickListener clickListener) {
            super(itemView);
            placename=itemView.findViewById(R.id.placeName);
            mClickListener = clickListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mClickListener.onUserClicked(getAdapterPosition());

        }
    }
    public interface UserListRecyclerClickListener{
        void onUserClicked(int position);
    }
}
