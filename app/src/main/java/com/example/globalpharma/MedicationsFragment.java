package com.example.globalpharma;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MedicationsFragment extends Fragment {

    private MedicationsViewModel mViewModel;
    private Button mBtnAdd;
    private RecyclerView mRv;

    public static MedicationsFragment newInstance() {
        return new MedicationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medications_fragment, container, false);

        mBtnAdd = view.findViewById(R.id.btn_add);
        mRv = view.findViewById(R.id.rv_medicines);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MedicationsViewModel.class);


        // TODO: Use the ViewModel
    }

}
