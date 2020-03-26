package com.example.globalpharma.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.globalpharma.Model.ActuAdapter;
import com.example.globalpharma.Model.Task;
import com.example.globalpharma.Model.TaskAdapter;
import com.example.globalpharma.Model.actuSante;
import com.example.globalpharma.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView ActuRecyclerview;
    ActuAdapter mActuAdapter;
    List<actuSante> mData;

    RecyclerView TaskRecyclerview;
    TaskAdapter mTaskAdapter;
    List<Task> mDataTask;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ActuRecyclerview = (RecyclerView)view.findViewById(R.id.actu_rv);
        TaskRecyclerview = (RecyclerView)view.findViewById(R.id.tache_rv);

        initRecyclerActu();
        initRecyclerTask();


        return view;
    }

    public void initRecyclerActu(){
        mData = new ArrayList<>();

        mData.add(new actuSante("Diabete","un composé produit par la flore intestinale aurait des effets protecteurs",R.drawable.image_actu));
        mData.add(new actuSante("Diabete","un composé produit par la flore intestinale aurait des effets protecteurs",R.drawable.image_actu));
        mData.add(new actuSante("Diabete","un composé produit par la flore intestinale aurait des effets protecteurs",R.drawable.image_actu));
        mData.add(new actuSante("Diabete","un composé produit par la flore intestinale aurait des effets protecteurs",R.drawable.image_actu));
        mData.add(new actuSante("Diabete","un composé produit par la flore intestinale aurait des effets protecteurs",R.drawable.image_actu));
        mData.add(new actuSante("Diabete","un composé produit par la flore intestinale aurait des effets protecteurs",R.drawable.image_actu));
        mData.add(new actuSante("Diabete","un composé produit par la flore intestinale aurait des effets protecteurs",R.drawable.image_actu));

        mActuAdapter = new ActuAdapter(getActivity(), mData);
        ActuRecyclerview.setAdapter(mActuAdapter);
        ActuRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), HORIZONTAL, false));

    }

    public void initRecyclerTask(){
        mDataTask = new ArrayList<Task>();

        mDataTask.add(new Task("Pharmacies de gardes","24h",R.drawable.ic_rx));
        mDataTask.add(new Task("Post de medicaments","Deux réponses de post",R.drawable.ic_search_chat));
        mDataTask.add(new Task("Pharmacies à proximité","jjfshkjhkjhfkhjf",R.drawable.ic_rx));

        mTaskAdapter = new TaskAdapter(getActivity(), mDataTask);
        TaskRecyclerview.setAdapter(mTaskAdapter);
        TaskRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), HORIZONTAL, false));

    }

}
