package com.example.globalpharma.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Adapter.Allnight_Adapter;
import com.example.globalpharma.Model.Ville;
import com.example.globalpharma.R;

import java.util.ArrayList;
import java.util.List;


public class Allnight_Pharmacy extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Allnight_Pharmacy() {
        // Required empty public constructor
    }

    Allnight_Adapter allnight_adapter;
    RecyclerView recyclerView;
    List<Ville> locationList;

    public static Allnight_Pharmacy newInstance(String param1, String param2) {
        Allnight_Pharmacy fragment = new Allnight_Pharmacy();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_allnight__pharmacy, container, false);
        recyclerView= (RecyclerView)v.findViewById(R.id.recyclertest);
        locationList=new ArrayList<Ville>();
        locationList.add(new Ville("Bamenda"));
        allnight_adapter=new Allnight_Adapter(getContext(),locationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(allnight_adapter);
        return v;
    }

}
