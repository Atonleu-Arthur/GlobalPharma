package com.example.globalpharma.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.MonthlyFrequencyItem;
import com.example.globalpharma.R;

import java.util.List;

public class MonthlyFrequencyAdapter extends RecyclerView.Adapter<MonthlyFrequencyAdapter.MonthlyFrequencyViewHolder> {
    Context mContext;
    List<MonthlyFrequencyItem> mMonthlyFrequencyItems;

    public MonthlyFrequencyAdapter(Context context, List<MonthlyFrequencyItem> monthlyFrequencyItems) {
        mContext = context;
        mMonthlyFrequencyItems = monthlyFrequencyItems;
    }

    @NonNull
    @Override
    public MonthlyFrequencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MonthlyFrequencyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.monthly_frequency_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyFrequencyViewHolder monthlyFrequencyAdapter, int i) {
        monthlyFrequencyAdapter.mTextView.setText(mMonthlyFrequencyItems.get(i).getLabel());
    }

    @Override
    public int getItemCount() {
        return mMonthlyFrequencyItems.size();
    }

    public class MonthlyFrequencyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public MonthlyFrequencyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_day_taking);
        }
    }
}
