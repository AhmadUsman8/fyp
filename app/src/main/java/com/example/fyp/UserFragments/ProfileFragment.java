package com.example.fyp.UserFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fyp.Common.PrivacyPolicy;
import com.example.fyp.Common.RatingFragment;
import com.example.fyp.Common.SupportFragment;
import com.example.fyp.R;
import com.example.fyp.Common.SettingsFragment;
import com.example.fyp.SignUp.LoginActivity;
import com.example.fyp.Utilities.Constants;
import com.example.fyp.Utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    TextView mSettings, mPrivacyPolicy, mRate, mSupport, mInvite;
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
        mPrivacyPolicy = view.findViewById(R.id.privacyPolicy);
        mRate = view.findViewById(R.id.rate);
        mSupport = view.findViewById(R.id.support);
        mInvite = view.findViewById(R.id.invite);
        mLogout = view.findViewById(R.id.logout);

        preferenceManager = new PreferenceManager(requireContext().getApplicationContext());
        mSettings.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.frameLayout, new SettingsFragment()).commit();
        });
        mPrivacyPolicy.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.frameLayout, new PrivacyPolicy()).commit();
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
        });
        mInvite.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
            dialog.setTitle("Invite Friends")
                    .setMessage("Invite your friends to fastest way of getting things done.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        });
        mLogout.setOnClickListener(v -> {
            preferenceManager.clear();
            mAuth.signOut();
            Intent intent = new Intent(requireActivity().getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        mFullName.setText(preferenceManager.getString(Constants.KEY_USER_NAME));
        mDisplayEmail.setText(preferenceManager.getString(Constants.KEY_USER_EMAIL));

    }
}