package com.example.fyp.SellerFragments;

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

import com.example.fyp.R;
import com.example.fyp.Common.SettingsFragment;
import com.example.fyp.SignUp.LoginActivity;
import com.example.fyp.Models.WorkerData;
import com.example.fyp.Common.RatingFragment;
import com.example.fyp.Common.SupportFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerProfileFragment extends Fragment {
    TextView mShare,mSettings,mRate,mSupport;
    TextView mLogout,mFullName,mDisplayEmail;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SellerProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerProfileFragment newInstance(String param1, String param2) {
        SellerProfileFragment fragment = new SellerProfileFragment();
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
        return inflater.inflate(R.layout.fragment_seller_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFullName=view.findViewById(R.id.full_name);
        mDisplayEmail=view.findViewById(R.id.displayEmail);
        mShare=view.findViewById(R.id.share);
        mSettings=view.findViewById(R.id.settings);
        mRate=view.findViewById(R.id.rate);
        mSupport=view.findViewById(R.id.support);

        mLogout=view.findViewById(R.id.logout);

        mSettings.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("new fragment")
                    .replace(R.id.frameLayout, new SettingsFragment()).commit();
        });

        mShare.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
        });

        mRate.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("new fragment")
                    .replace(R.id.frameLayout, new RatingFragment()).commit();
        });

        mSupport.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.frameLayout, new SupportFragment()).commit();
        });

        mLogout.setOnClickListener(v -> {//Toast.makeText(requireActivity().getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            Intent intent = new Intent(requireActivity().getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        FirebaseFirestore.getInstance().collection("users")
                .document(mAuth.getCurrentUser().getUid()).
                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                WorkerData workerData = documentSnapshot.toObject(WorkerData.class);
                mFullName.setText(workerData.getFname());
                mDisplayEmail.setText(workerData.getEmail());
            }
        });
    }
}