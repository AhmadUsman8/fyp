package com.example.fyp.SellerFragments;

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
import com.example.fyp.SignUp.WorkerData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerProfileFragment extends Fragment {
    TextView mUserRequests,mManageServices,mEarnings;
    TextView mMyProfile,mPayments,mSettings,mRate,mSupport;
    TextView mLogout,mFullName,mDisplayEmail;
    SwitchCompat mToogleButton;
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
        mManageServices=view.findViewById(R.id.manageServices);
        mUserRequests=view.findViewById(R.id.userRequests);
        mEarnings=view.findViewById(R.id.earnings);
        mMyProfile=view.findViewById(R.id.myProfile);
        mPayments=view.findViewById(R.id.payment);
        mSettings=view.findViewById(R.id.settings);
        mRate=view.findViewById(R.id.rate);
        mSupport=view.findViewById(R.id.support);

        mLogout=view.findViewById(R.id.logout);

        mToogleButton=view.findViewById(R.id.toggleButton);

        mUserRequests.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "User Requests", Toast.LENGTH_SHORT).show();
        });
        mManageServices.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Manage Services", Toast.LENGTH_SHORT).show();
        });
        mEarnings.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Earnings", Toast.LENGTH_SHORT).show();
        });
        mMyProfile.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
        });
        mPayments.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Payments", Toast.LENGTH_SHORT).show();
        });
        mSettings.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        mToogleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    Toast.makeText(requireActivity().getApplicationContext(), "User mode ON", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(requireActivity().getApplicationContext(), UserDashboard.class);
                    startActivity(intent);
                }
            }
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