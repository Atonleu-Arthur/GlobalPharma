package com.example.globalpharma.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.Pharmacy_Location;
import com.example.globalpharma.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Find_Pharmacy extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore firebaseFirestore;

    public Find_Pharmacy() {
        // Required empty public constructor
    }


    public static Find_Pharmacy newInstance(String param1, String param2) {
        Find_Pharmacy fragment = new Find_Pharmacy();
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
        View v = inflater.inflate(R.layout.fragment_find__pharmacy, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("ListeVille");
        FirestoreRecyclerOptions<Pharmacy_Location> options = new
                FirestoreRecyclerOptions.Builder<Pharmacy_Location>().setQuery(query, Pharmacy_Location.class).build();
        adapter = new FirestoreRecyclerAdapter<Pharmacy_Location, FindViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FindViewHolder holder, int position, @NonNull Pharmacy_Location model) {

            }

            @NonNull
            @Override
            public FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }


        };

        return v;
    }
    public class FindViewHolder extends RecyclerView.ViewHolder {
        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}