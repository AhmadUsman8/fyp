package com.example.fyp.UserFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Common.RatingFragment;
import com.example.fyp.Common.SupportFragment;
import com.example.fyp.R;
import com.example.fyp.Common.SettingsFragment;
import com.example.fyp.SignUp.LoginActivity;
import com.example.fyp.Models.UserData;
import com.example.fyp.Utilities.Constants;
import com.example.fyp.Utilities.PreferenceManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    TextView mSettings, mShare, mRate, mSupport;
    TextView mLogout, mFullName, mDisplayEmail;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PreferenceManager preferenceManager;

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
        mDisplayEmail = view.findViewById(R.id.displayEmail);
        mSettings = view.findViewById(R.id.settings);
        mShare = view.findViewById(R.id.share);
        mRate = view.findViewById(R.id.rate);
        mSupport = view.findViewById(R.id.support);
        mLogout = view.findViewById(R.id.logout);
        preferenceManager=new PreferenceManager(requireContext().getApplicationContext());
        mSettings.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.frameLayout, new SettingsFragment()).commit();
            //Toast.makeText(requireActivity().getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
        });
        mShare.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Share App", Toast.LENGTH_SHORT).show();
        });
        mRate.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.frameLayout, new RatingFragment()).commit();
        });
        mSupport.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.frameLayout, new SupportFragment()).commit();
            //Toast.makeText(requireActivity().getApplicationContext(), "Support", Toast.LENGTH_SHORT).show();
        });
        mLogout.setOnClickListener(v -> {
            preferenceManager.clear();
            //Toast.makeText(requireActivity().getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Intent intent = new Intent(requireActivity().getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

//        FirebaseFirestore.getInstance().collection("users")
//                .document(mAuth.getCurrentUser().getUid()).
//                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                UserData userData = documentSnapshot.toObject(UserData.class);
//                mFullName.setText(userData.getFname() + " " + userData.getLname());
//                mDisplayEmail.setText(userData.getEmail());
//            }
//        });

        mFullName.setText(preferenceManager.getString(Constants.KEY_USER_NAME));
        mDisplayEmail.setText(preferenceManager.getString(Constants.KEY_USER_EMAIL));

    }
}