package com.example.globalpharma.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.AllTheFrequencyItem;
import com.example.globalpharma.R;

import java.util.List;

public class AllTheFrequencyAdapter extends RecyclerView.Adapter<AllTheFrequencyAdapter.AllTheFrequencyViewHolder> {
    Context mContext;
    List<AllTheFrequencyItem> mAllTheFrequencyItems;

    public AllTheFrequencyAdapter(Context context, List<AllTheFrequencyItem> allTheFrequencyItems) {
        mContext = context;
        mAllTheFrequencyItems = allTheFrequencyItems;
    }

    @NonNull
    @Override
    public AllTheFrequencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AllTheFrequencyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.all_the_frequency_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllTheFrequencyViewHolder monthlyFrequencyAdapter, int i) {
        monthlyFrequencyAdapter.mTextView.setText(mAllTheFrequencyItems.get(i).getLabel());
    }

    @Override
    public int getItemCount() {
        return mAllTheFrequencyItems.size();
    }

    public class AllTheFrequencyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public AllTheFrequencyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_add_all_the);
        }
    }
}
