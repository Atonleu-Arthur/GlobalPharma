package com.example.globalpharma.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.DefaultFrequencyItem;
import com.example.globalpharma.R;

import java.util.List;

public class DefaultFrequencyAdapter extends RecyclerView.Adapter<DefaultFrequencyAdapter.DefaultFrequencyViewHolder> {
    List<DefaultFrequencyItem> mDefaultFrequencyItems;
    Context mContext;

    public DefaultFrequencyAdapter(List<DefaultFrequencyItem> defaultFrequencyItems, Context context) {
        mDefaultFrequencyItems = defaultFrequencyItems;
        mContext = context;
    }

    @NonNull
    @Override
    public DefaultFrequencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.default_frequency_item, viewGroup, false);
        return new DefaultFrequencyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultFrequencyViewHolder defaultFrequencyViewHolder, int i) {
        defaultFrequencyViewHolder.mTextView.setText(mDefaultFrequencyItems.get(i).getLabel());
    }

    @Override
    public int getItemCount() {
        return mDefaultFrequencyItems.size();
    }

    public class DefaultFrequencyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public DefaultFrequencyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_default);
        }
    }
}
