package com.example.fyp.UserFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Dashboards.SellerDashboard;
import com.example.fyp.Dashboards.UserDashboard;
import com.example.fyp.R;
import com.example.fyp.SignUp.LoginActivity;
import com.example.fyp.SignUp.UserData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    SwitchCompat mToogleButton;
    TextView mPostJob, mViewJob;
    TextView mSettings, mPayments, mShare, mRate, mSupport;
    TextView mLogout, mFullName,mDisplayEmail;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFullName = view.findViewById(R.id.full_name);
        mDisplayEmail=view.findViewById(R.id.displayEmail);
        mPostJob = view.findViewById(R.id.postjob);
        mViewJob = view.findViewById(R.id.viewJob);
        mSettings = view.findViewById(R.id.settings);
        mPayments = view.findViewById(R.id.payment);
        mShare = view.findViewById(R.id.share);
        mRate = view.findViewById(R.id.rate);
        mSupport = view.findViewById(R.id.support);
        mLogout = view.findViewById(R.id.logout);

        mPostJob.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profile, new JobsFragment()).commit();
            //Toast.makeText(requireActivity().getApplicationContext(), "Post Job", Toast.LENGTH_SHORT).show();
        });
        mViewJob.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profile, new displayJobs()).commit();
            //Toast.makeText(requireActivity().getApplicationContext(), "View Jobs", Toast.LENGTH_SHORT).show();
        });
        mSettings.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
        });
        mPayments.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Payments", Toast.LENGTH_SHORT).show();
        });
        mShare.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Share App", Toast.LENGTH_SHORT).show();
        });
        mRate.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Rate App", Toast.LENGTH_SHORT).show();
        });
        mSupport.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Support", Toast.LENGTH_SHORT).show();
        });
        mLogout.setOnClickListener(v -> {
            //Toast.makeText(requireActivity().getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Intent intent = new Intent(requireActivity().getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

//        mToogleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (buttonView.isChecked()) {
//                    Toast.makeText(requireActivity().getApplicationContext(), "Seller mode ON", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(requireActivity().getApplicationContext(), SellerDashboard.class);
//                    startActivity(intent);
//                }
//            }
//        });

        FirebaseFirestore.getInstance().collection("users")
                .document(mAuth.getCurrentUser().getUid()).
                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserData userData = documentSnapshot.toObject(UserData.class);
                mFullName.setText(userData.getFname() + " " + userData.getLname());
                mDisplayEmail.setText(userData.getEmail());
            }
        });
    }
}