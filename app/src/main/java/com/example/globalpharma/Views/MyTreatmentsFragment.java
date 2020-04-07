package com.example.globalpharma.Views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.globalpharma.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTreatmentsFragment extends Fragment {


    public MyTreatmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_treatments, container, false);
    }

}
