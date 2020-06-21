package com.example.globalpharma.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.globalpharma.R;

public class PopUpQuantity extends AppCompatDialogFragment {
    private EditText mNumberPicker;
    private TextView mTextView;
    private PopUpQuantityListener mPopUpQuantityListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_pop_up_quantity, null);

        mNumberPicker = view.findViewById(R.id.np_quantity);
        mTextView = view.findViewById(R.id.text_medecine_form);

        builder.setView(view)
                .setTitle("Quantity")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPopUpQuantityListener.setQuantity(Integer.parseInt(mNumberPicker.getText().toString()));
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mPopUpQuantityListener = (PopUpQuantityListener)context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface PopUpQuantityListener{
        void setQuantity(int value);
    }
}
