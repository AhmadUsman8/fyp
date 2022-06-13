package com.example.fyp.Common;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyp.R;
import com.example.fyp.Models.UserData;
import com.example.fyp.UserFragments.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    EditText mOldPassword,mPassword, mConfirmPassword;
    Button mUpdate, mCancel;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static final String TAG = "TAG";
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOldPassword=view.findViewById(R.id.oldPassword);
        mPassword = view.findViewById(R.id.updatePassword);
        mConfirmPassword = view.findViewById(R.id.confirmUpdatedPassword);
        mUpdate = view.findViewById(R.id.updateButton);
        mCancel = view.findViewById(R.id.cancelButton);


        mUpdate.setOnClickListener(v -> {
            updateFunc();
        });

        mCancel.setOnClickListener(v -> {
            this.getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragment")
                    .replace(R.id.frameLayout, new ProfileFragment()).commit();
        });

    }

    private void updateFunc() {
        String oldpassword = mOldPassword.getText().toString();
        String password = mPassword.getText().toString();
        String conpassword = mConfirmPassword.getText().toString();

        if (oldpassword.isEmpty()) {
            mOldPassword.setError("Can't be empty");
            mOldPassword.requestFocus();
            return;
        }
        if (oldpassword.length() < 6) {
            mOldPassword.setError("At least 6 characters long");
            mOldPassword.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            mPassword.setError("At least 6 characters long");
            mPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            mPassword.setError("At least 6 characters long");
            mPassword.requestFocus();
            return;
        }
        if (!conpassword.equals(password)) {
            mConfirmPassword.setError("Password doesn't match");
            mConfirmPassword.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Processing...");
        progressDialog.show();


        AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(mAuth.getCurrentUser().getEmail()), oldpassword);
        mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mUser.updatePassword(conpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext().getApplicationContext(),"Password Updated",Toast.LENGTH_SHORT).show();
                                SettingsFragment.this.getActivity().getSupportFragmentManager().popBackStackImmediate();
                                Log.d(TAG, "Password updated");
                            } else {
                                Log.d(TAG, "Error password not updated");
                                Toast.makeText(getContext().getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
