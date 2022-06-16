package com.example.fyp.UserFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fyp.Adapters.GigAdapter;
import com.example.fyp.Models.CreateJob;
import com.example.fyp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class displayJobs extends Fragment {

    FloatingActionButton mCreate;
    RecyclerView recyclerView;
    GigAdapter gigAdapter;
    ArrayList<CreateJob> jobArrayList=new ArrayList<>();
    FirebaseFirestore fstore=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public displayJobs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment displayJobs.
     */
    // TODO: Rename and change types and number of parameters
    public static displayJobs newInstance(String param1, String param2) {
        displayJobs fragment = new displayJobs();
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
        return inflater.inflate(R.layout.fragment_display_jobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCreate = view.findViewById(R.id.create);
        recyclerView=view.findViewById(R.id.recyclerview);
        gigAdapter=new GigAdapter(jobArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(gigAdapter);
        loadJobs();

        mCreate.setOnClickListener(v -> {

            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("randomName")
                    .replace(R.id.frameLayout, new JobsFragment()).commit();
        });
    }

    public void loadJobs(){
        this.jobArrayList.clear();
        fstore.collection("jobs").whereEqualTo("uid",mAuth.getCurrentUser().getUid()).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CreateJob job = queryDocumentSnapshots.getDocuments().get(i).toObject(CreateJob.class);
                    jobArrayList.add(job);
                    gigAdapter.notifyItemInserted(jobArrayList.size()-1);
                }
            }
        });

    }
}