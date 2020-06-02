package com.example.globalpharma.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Interface.OnListListener;
import com.example.globalpharma.Model.Ville;
import com.example.globalpharma.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class Pharmacy extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rcVilles;
    FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private  RecyclerView mFirestorelist;
    ValueEventListener listener;
    private String mParam1;
    private String mParam2;
    private ArrayList<Ville> mListes= new ArrayList<>();
    private OnListListener mOnListListener;
    public Pharmacy(ArrayList<Ville> villes,OnListListener onListListener)
    {
        this.mListes=villes;
        this.mOnListListener=onListListener;
    }

    public Pharmacy() {
        // Required empty public constructor
    }


    public static Pharmacy newInstance(String param1, String param2) {
        Pharmacy fragment = new Pharmacy();
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
        View view =inflater.inflate(R.layout.fragment_pharmacy, container, false);
        rcVilles=(RecyclerView)view.findViewById(R.id.RcVilles);

        firebaseFirestore=FirebaseFirestore.getInstance();
        Query query= firebaseFirestore.collection("ListeVille");
        FirestoreRecyclerOptions<Ville> options=new
                FirestoreRecyclerOptions.Builder<Ville>().setQuery(query,Ville.class ).build() ;
        adapter =  new FirestoreRecyclerAdapter<Ville,listeViewHolder>(options){

            @NonNull
            @Override
            public listeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View layout;
                layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ville,parent,false);
                return new listeViewHolder(layout);
            }


            @Override
            protected void onBindViewHolder(@NonNull listeViewHolder holder, int position, @NonNull Ville model) {
                holder.container.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_scale_animation));

                holder.nomVille.setText(model.getNom());
            }
        };

        rcVilles.setHasFixedSize(true);
        rcVilles.setLayoutManager(new LinearLayoutManager(getContext()));
        rcVilles.setAdapter(adapter);
        return view;
    }




    private class listeViewHolder extends RecyclerView.ViewHolder  {
        TextView nomVille;
        RelativeLayout container;
        OnListListener onListListener;
        public listeViewHolder(@NonNull View itemView) {
            super(itemView);
            nomVille=itemView.findViewById(R.id.NomVille);
            container=itemView.findViewById(R.id.container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GpsFragment fragment=new GpsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,fragment).commit();
                }
            });

        }


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
