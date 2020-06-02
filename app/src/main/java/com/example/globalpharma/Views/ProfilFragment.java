package com.example.globalpharma.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.globalpharma.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
  private  Button btn_singOut;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }


    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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

    Button Btn_modifier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_profil, container, false);

        Btn_modifier = (Button)v.findViewById(R.id.button_modifier);
         btn_singOut=v.findViewById(R.id.btn_SignOut);
           btn_singOut.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   signOut();
               }
           });
        Btn_modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(i);

            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
    }

}
