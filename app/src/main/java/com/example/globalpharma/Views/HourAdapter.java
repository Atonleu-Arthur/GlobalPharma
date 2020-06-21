package com.example.globalpharma.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.HourItem;
import com.example.globalpharma.R;

import java.util.List;

public class HourAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    private OnItemClickListener mDeleteListener;
    private Boolean isDeletion = false;
    Context mContext;
    List<HourItem> mHourItems;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mDeleteListener = listener;
    }

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
        ImageView mImageDelete;
        ImageView mImageEdit;
        public HourViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextHourValue = itemView.findViewById(R.id.text_hour_value);
            mTextTitle = itemView.findViewById(R.id.text_hour_title);
            mTextMedicationForm = itemView.findViewById(R.id.text_form_medication_in_hour);
            mTextQuantity = itemView.findViewById(R.id.text_quantity_in_hour);
            mImageDelete = itemView.findViewById(R.id.img_delete_hour);
            mImageEdit = itemView.findViewById(R.id.img_edit_hour);

            mImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDeletion = true;
                    if(mDeleteListener != null && isDeletion == true){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mDeleteListener.onDeleteClick(position);
                        }
                    }
                }
            });

            mImageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDeletion = false;
                    if(mDeleteListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && isDeletion == false) {
                            mDeleteListener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }
}
