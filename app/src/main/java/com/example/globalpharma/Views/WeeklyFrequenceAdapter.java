package com.example.globalpharma.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.WeeklyFrequencyItem;
import com.example.globalpharma.R;

import java.util.List;

public class WeeklyFrequenceAdapter extends RecyclerView.Adapter<WeeklyFrequenceAdapter.DailyFrequencyViewHolder> {
    List<WeeklyFrequencyItem> mWeeklyFrequencyItems;
    Context mContext;

    public WeeklyFrequenceAdapter(List<WeeklyFrequencyItem> weeklyFrequencyItems, Context context) {
        mWeeklyFrequencyItems = weeklyFrequencyItems;
        mContext = context;
    }

    @NonNull
    @Override
    public DailyFrequencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.daily_frenquency_item, viewGroup, false);
        return new DailyFrequencyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyFrequencyViewHolder dailyFrequencyViewHolder, int i) {
        dailyFrequencyViewHolder.mTxtNameOfDay.setText(mWeeklyFrequencyItems.get(i).getNameOfDay());
    }

    @Override
    public int getItemCount() {
        return mWeeklyFrequencyItems.size();
    }

    public class DailyFrequencyViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtNameOfDay;
        public DailyFrequencyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtNameOfDay = itemView.findViewById(R.id.chk_status_day);
        }
    }
}
