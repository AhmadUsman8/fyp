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

public class ProfileFragment extends Fragment {
    SwitchCompat mToogleButton;
    TextView mPostJob,mViewJob;
    TextView mSettings,mPayments,mShare,mRate,mSupport;
    TextView mLogout;


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

        mPostJob=view.findViewById(R.id.postjob);
        mViewJob=view.findViewById(R.id.viewJob);
        mSettings=view.findViewById(R.id.settings);
        mPayments=view.findViewById(R.id.payment);
        mShare=view.findViewById(R.id.share);
        mRate=view.findViewById(R.id.rate);
        mSupport=view.findViewById(R.id.support);
        mLogout =view.findViewById(R.id.logout);

        mToogleButton=view.findViewById(R.id.toggleButton);

        mPostJob.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Post Job", Toast.LENGTH_SHORT).show();
        });
        mViewJob.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "View Jobs", Toast.LENGTH_SHORT).show();
        });
        mSettings.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
        });
        mPayments.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Payments", Toast.LENGTH_SHORT).show();
        });
        mShare.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Share App", Toast.LENGTH_SHORT).show();
        });
        mRate.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Rate App", Toast.LENGTH_SHORT).show();
        });
        mSupport.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Support", Toast.LENGTH_SHORT).show();
        });
        mLogout.setOnClickListener(v -> {
            //Toast.makeText(requireActivity().getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(requireActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        mToogleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    Toast.makeText(requireActivity().getApplicationContext(), "Seller mode ON", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(requireActivity().getApplicationContext(), SellerDashboard.class);
                    startActivity(intent);
                }
            }
        });
    }
}