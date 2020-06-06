package com.example.globalpharma.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.HourItem;
import com.example.globalpharma.R;

import java.util.List;

public class HourAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    Context mContext;
    List<HourItem> mHourItems;

    public HourAdapter(Context context, List<HourItem> hourItems) {
        mContext = context;
        mHourItems = hourItems;
    }

    @NonNull
    @Override
    public HourAdapter.HourViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.hour_item, viewGroup, false);
        return new HourViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HourAdapter.HourViewHolder hourViewHolder, int i) {
        hourViewHolder.mTextHourValue.setText(mHourItems.get(i).getHourValue());
        hourViewHolder.mTextTitle.setText(mHourItems.get(i).getTitle());
        hourViewHolder.mTextMedicationForm.setText(mHourItems.get(i).getForm());
        hourViewHolder.mTextQuantity.setText(mHourItems.get(i).getQuantity());
    }

    @Override
    public void onViewAttachedToWindow(@NonNull HourViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return mHourItems.size();
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {
        TextView mTextHourValue;
        TextView mTextTitle;
        TextView mTextMedicationForm;
        TextView mTextQuantity;
        public HourViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextHourValue = itemView.findViewById(R.id.text_hour_value);
            mTextTitle = itemView.findViewById(R.id.text_hour_title);
            mTextMedicationForm = itemView.findViewById(R.id.text_form_medication_in_hour);
            mTextQuantity = itemView.findViewById(R.id.text_quantity_in_hour);
        }
    }
}
