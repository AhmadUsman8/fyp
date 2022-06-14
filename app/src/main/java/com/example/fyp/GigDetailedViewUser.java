package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fyp.Chat.ChatActivity;
import com.example.fyp.Models.CreateJob;
import com.example.fyp.Models.UserData;
import com.example.fyp.Utilities.Constants;
import com.example.fyp.databinding.FragmentGigDetailedViewUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GigDetailedViewUser extends BottomSheetDialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "gig";


    private CreateJob createJob;
    private FragmentGigDetailedViewUserBinding binding;

    public GigDetailedViewUser() {
        // Required empty public constructor
    }

    public static GigDetailedViewUser newInstance(CreateJob cjob) {
        GigDetailedViewUser fragment = new GigDetailedViewUser();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, cjob);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            createJob = (CreateJob) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gig_detailed_view_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentGigDetailedViewUserBinding.bind(requireView());

        binding.Title.setText(createJob.getTitle());
        binding.Description.setText(createJob.getDescription());
        binding.Budget.setText(createJob.getBudget());
        binding.Time.setText(createJob.getTime());
        binding.Service.setText(createJob.getService());

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.Title.setEnabled(true);
                binding.Description.setEnabled(true);
                binding.Budget.setEnabled(true);
                binding.Time.setEnabled(true);
                binding.Service.setEnabled(true);
                binding.saveButton.setEnabled(true);
                binding.editButton.setEnabled(false);
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> updated = new HashMap<>();
                updated.put("budget", binding.Budget.getText().toString().trim());
                updated.put("description", binding.Description.getText().toString().trim());
                updated.put("service", binding.Service.getText().toString().trim());
                updated.put("time", binding.Time.getText().toString().trim());
                updated.put("title", binding.Title.getText().toString().trim());


                FirebaseFirestore.getInstance().collection("jobs").document(createJob.getId())
                        .update(updated).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.Title.setEnabled(false);
                        binding.Description.setEnabled(false);
                        binding.Budget.setEnabled(false);
                        binding.Time.setEnabled(false);
                        binding.Service.setEnabled(false);
                        binding.saveButton.setEnabled(false);
                        binding.editButton.setEnabled(true);
                        Toast.makeText(requireContext().getApplicationContext(), "Job updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}