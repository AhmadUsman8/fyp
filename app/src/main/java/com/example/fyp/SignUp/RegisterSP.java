package com.example.fyp.SignUp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Models.WorkerData;
import com.example.fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterSP extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView alreadyHaveAcc;
    Button btnRegister;
    private EditText name, inputEmail, phoneNo, inputPassword, confirmPassword;
    Spinner service;
    String[] list = { "Electrician", "Mechanic",
            "Car Wash", "Cleaning", "Design", "Home repair",
            "Laundry", "Construction", "Painter", "Carpenter", "Other"};
    FirebaseAuth mAuth;

    public static final String TAG = "TAG";
    FirebaseFirestore fstore;
    String serviceProviderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sp);

        alreadyHaveAcc = (TextView) findViewById(R.id.alreadyHaveAccount);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        name = (EditText) findViewById(R.id.name);
        service = (Spinner) findViewById(R.id.services);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        phoneNo = (EditText) findViewById(R.id.inputPhoneNo);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        service.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, list);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        service.setAdapter(ad);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                registerServiceProvider();
            }
        });

        alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSP.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerServiceProvider() {
        String mName = name.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String phone = phoneNo.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String conpassword = confirmPassword.getText().toString().trim();

        if (mName.isEmpty()) {
            name.setError("Please enter name");
            name.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            inputEmail.setError("Please enter email");
            inputEmail.requestFocus();
            return;
        }
        if (!email.contains("@gmail.com")) {
            inputEmail.setError("Please enter valid email address");
            inputEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Please enter valid email address");
            inputEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            inputPassword.setError("Please enter password");
            inputPassword.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            phoneNo.setError("Please enter phone number");
            phoneNo.requestFocus();
            return;
        }
        if (password.length() < 6) {
            inputPassword.setError("At least 6 characters long");
            inputPassword.requestFocus();
            return;
        }
        if (!conpassword.equals(password)) {
            confirmPassword.setError("Password doesn't match");
            confirmPassword.requestFocus();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(RegisterSP.this);
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: " + e.getMessage());
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser fuser = mAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegisterSP.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(RegisterSP.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            serviceProviderID = mAuth.getCurrentUser().getUid();
                            WorkerData workerData = new WorkerData(serviceProviderID,mName, null, email, phone, (String) service.getSelectedItem());
                            fstore.collection("users")
                                    .document(serviceProviderID).set(workerData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: service provider profile is created for " + serviceProviderID);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    })
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}