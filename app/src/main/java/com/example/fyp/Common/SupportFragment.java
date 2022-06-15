package com.example.fyp.Common;

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

import com.example.fyp.Models.Support;
import com.example.fyp.R;
import com.example.fyp.SellerFragments.SellerProfileFragment;
import com.example.fyp.Models.UserData;
import com.example.fyp.UserFragments.ProfileFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupportFragment extends Fragment {

    EditText mSubject,mSupport;
    Button mSubmit, mCancel;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SupportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SupportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SupportFragment newInstance(String param1, String param2) {
        SupportFragment fragment = new SupportFragment();
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
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubject = view.findViewById(R.id.subject);
        mSupport = view.findViewById(R.id.editText);
        mSubmit = view.findViewById(R.id.submitButton);
        mCancel = view.findViewById(R.id.cancelButton);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportQueries();
            }
        });

        mCancel.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.frameLayout, new ProfileFragment()).commit();
        });

    }

    private void supportQueries() {
        if (mSupport.getText().toString().trim().isEmpty()) {
            mSupport.setError("This field can't be empty");
            mSupport.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        Support query = new Support(mUser.getUid(),mSubject.getText().toString().trim(), mSupport.getText().toString().trim());
        fstore.collection("support").add(query).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                UserData userData = new UserData();
                if (userData.getType().equals("user")) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack("fragment1")
                            .replace(R.id.frameLayout, new ProfileFragment()).commit();
                    SupportFragment.this.getActivity().getSupportFragmentManager().popBackStackImmediate();

                } else {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack("fragment2")
                            .replace(R.id.frameLayout, new SellerProfileFragment()).commit();
                    SupportFragment.this.getActivity().getSupportFragmentManager().popBackStackImmediate();

                }
                Toast.makeText(requireActivity().getApplicationContext(), "Your query has been received", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                SupportFragment.this.getActivity().getSupportFragmentManager().popBackStackImmediate();
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