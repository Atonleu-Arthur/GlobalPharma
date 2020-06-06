package com.example.globalpharma.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Adapter.Find_Pharmacy_Adapter;
import com.example.globalpharma.Adapter.VilleAdapter;
import com.example.globalpharma.Model.Pharmacy_Location;
import com.example.globalpharma.Model.UserLocation;
import com.example.globalpharma.Model.Ville;
import com.example.globalpharma.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;


public class Find_Allnight_Pharmacy extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    private Find_Pharmacy_Adapter find_pharmacy_adapter;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Pharmacy_Location> pharmacy_locationArrayList;
    private VilleAdapter villeAdapter;
    private List<Ville> villeList;
    private Button ViewAllnihtPharmac;
    private ArrayList<UserLocation> mUserLocations = new ArrayList<>();

    public Find_Allnight_Pharmacy() {
        // Required empty public constructor
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
        pharmacy_locationArrayList=new ArrayList<Pharmacy_Location>();
        ViewAllnihtPharmac=(Button)v.findViewById(R.id.btnAllnihtPharmacy);

        recyclerView= (RecyclerView)v.findViewById(R.id.rcallnightPharmacy);

        ShowAllnightPharmacy();

        GetFind_Pharmacy(recyclerView);
       return  v;
    }

    public void InitRecyclerView()
    {
        /*pharmacy_locationArrayList.add(new Pharmacy_Location(null,null,new Pharmacy("Bepanda","douncb",true,"656655"),new Ville("Bonnjo")));
        find_pharmacy_adapter=new Find_Pharmacy_Adapter(getContext(),pharmacy_locationArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(find_pharmacy_adapter);*/

      /*  pharmacy_locationArrayList=new ArrayList<Pharmacy_Location>();
        pharmacy_locationArrayList.add(new Pharmacy_Location(new Pharmacy("Doula","Derire fosto")));*/
        villeAdapter= new VilleAdapter(getContext(),pharmacy_locationArrayList);
        recyclerView.setAdapter(villeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    public void GetDocument()
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference pharmacy_locationRef=db.collection("Pharmacy_Location");
        Query queryliste=null;
        queryliste=pharmacy_locationRef;

        queryliste.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                       // Log.d(TAG, document.getId() + " => " + document.getData());
                       // pharmacy_locationArrayList.add(document.getData());
                        Pharmacy_Location pharmacy_location=document.toObject(Pharmacy_Location.class);
                        pharmacy_locationArrayList.add(pharmacy_location);
                        Toast.makeText(getContext(), "Pahrmacie de garde", Toast.LENGTH_SHORT).show();
                        find_pharmacy_adapter.notifyDataSetChanged();

                    }
                } else {
                    Log.d(TAG, "Error lors de l'obtention des documents: ", task.getException());
                    Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void ShowAllnightPharmacy(){
        ViewAllnihtPharmac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GpsFragment fragment=new GpsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fragment).commit();
            }
        });
    }

    public void GetFind_Pharmacy(RecyclerView recyclerView)
    {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Pharmacy_Location")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Pharmacy_Location pharmacy_location =document.toObject(Pharmacy_Location.class);
                                if(pharmacy_location!=null && pharmacy_location.getPharmacy().isDegarde()==true)
                                {
                                    pharmacy_locationArrayList.add(pharmacy_location);
                                }
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setHasFixedSize(true);
                            villeAdapter= new VilleAdapter(getContext(),pharmacy_locationArrayList);
                            recyclerView.setAdapter(villeAdapter);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}