package com.example.fyp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fyp.Adapters.WorkerAdapter;
import com.example.fyp.Models.WorkerData;
import com.example.fyp.Utilities.Constants;
import com.example.fyp.databinding.FragmentShowWorkerBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ShowWorker extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SERVICE = "service";

    private String service;
    private FragmentShowWorkerBinding binding;
    private ArrayList<WorkerData> workerList = new ArrayList<>();
    private WorkerAdapter workerAdapter = new WorkerAdapter(workerList);


    public ShowWorker() {
        // Required empty public constructor
    }

    public static ShowWorker newInstance(String service) {
        ShowWorker fragment = new ShowWorker();
        Bundle args = new Bundle();
        args.putString(ARG_SERVICE, service);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            service = getArguments().getString(ARG_SERVICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_worker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentShowWorkerBinding.bind(requireView());

        binding.showWorkerRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.showWorkerRecycler.setAdapter(workerAdapter);

        loadWorkers();
    }

    public void loadWorkers() {
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("type", "worker")
                .whereEqualTo(Constants.KEY_SERVICE, service)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.getDocuments().size()<1){
                            Toast.makeText(requireContext().getApplicationContext(),"No worker to show",Toast.LENGTH_SHORT).show();
                        }
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            workerList.add(queryDocumentSnapshots.getDocuments().get(i).toObject(WorkerData.class));
                            workerAdapter.notifyItemInserted(workerList.size() - 1);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext().getApplicationContext(), "Failed to load workers." + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}