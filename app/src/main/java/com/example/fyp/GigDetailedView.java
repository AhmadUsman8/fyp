package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fyp.Chat.ChatActivity;
import com.example.fyp.Models.CreateJob;
import com.example.fyp.Models.UserData;
import com.example.fyp.databinding.FragmentGigDetailedViewBinding;
import com.example.fyp.Utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GigDetailedView extends BottomSheetDialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "gig";


    private CreateJob createJob;
    private FragmentGigDetailedViewBinding binding;

    public GigDetailedView() {
        // Required empty public constructor
    }

    public static GigDetailedView newInstance(CreateJob cjob) {
        GigDetailedView fragment = new GigDetailedView();
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
        return inflater.inflate(R.layout.fragment_gig_detailed_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentGigDetailedViewBinding.bind(requireView());

        binding.Title.setText(createJob.getTitle());
        binding.Description.setText(createJob.getDescription());
        binding.Budget.setText(createJob.getBudget());
        binding.Time.setText(createJob.getTime());

        binding.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("users")
                        .document(createJob.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            UserData userData=task.getResult().toObject(UserData.class);
                            Intent intent=new Intent(requireContext(), ChatActivity.class);
                            intent.putExtra(Constants.KEY_USER,userData);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}