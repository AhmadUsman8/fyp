package com.example.fyp.UserFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyp.Models.CreateJob;
import com.example.fyp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobsFragment extends Fragment {

    EditText mTitle, mDescription, mBudget, mTime, mService;
    Button mCreateJob;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JobsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobsFragment newInstance(String param1, String param2) {
        JobsFragment fragment = new JobsFragment();
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
        return inflater.inflate(R.layout.fragment_jobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle = view.findViewById(R.id.title);
        mDescription = view.findViewById(R.id.description);
        mBudget = view.findViewById(R.id.budget);
        mTime=view.findViewById(R.id.time);
        mCreateJob = view.findViewById(R.id.createJob);
        mService= view.findViewById(R.id.service);

        mCreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJob();
            }
        });
    }

    private void postJob() {
        if (mTitle.getText().toString().trim().isEmpty()) {
            mTitle.setError("Please enter title");
            mTitle.requestFocus();
            return;
        }
        if (mDescription.getText().toString().trim().isEmpty()) {
            mDescription.setError("Please enter description");
            mDescription.requestFocus();
            return;
        }
        if (mBudget.getText().toString().trim().isEmpty()) {
            mBudget.setError("Please enter budget");
            mBudget.requestFocus();
            return;
        }
        if (mTime.getText().toString().trim().isEmpty()) {
            mTime.setError("Please enter time");
            mTime.requestFocus();
            return;
        }
        if (mService.getText().toString().trim().isEmpty()) {
            mService.setError("Please enter service");
            mService.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        CreateJob job = new CreateJob(mUser.getUid(), mTitle.getText().toString().trim(), mDescription.getText().toString().trim(), mBudget.getText().toString().trim(),mTime.getText().toString().trim(),mService.getText().toString().trim());

        fstore.collection("jobs").add(job).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(requireActivity().getApplicationContext(), "Job Posted", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                JobsFragment.this.getActivity().getSupportFragmentManager().popBackStackImmediate();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

}