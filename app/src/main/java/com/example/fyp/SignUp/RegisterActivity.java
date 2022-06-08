package com.example.fyp.SignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView alreadyHaveAcc;
    Button btnRegister, btnRegisterSP;
    private EditText firstName, lastName, inputEmail, inputPassword, confirmPassword;
    FirebaseAuth mAuth;

    public static final String TAG = "TAG";
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        alreadyHaveAcc = (TextView) findViewById(R.id.alreadyHaveAccount);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegisterSP = (Button) findViewById(R.id.btnRegisterSP);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        btnRegisterSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,RegisterSP.class);
                startActivity(intent);
                finish();
            }
        });

        alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerUser(){
        String fname=firstName.getText().toString().trim();
        String lname=lastName.getText().toString().trim();
        String email=inputEmail.getText().toString().trim();
        String password=inputPassword.getText().toString().trim();
        String conpassword=confirmPassword.getText().toString().trim();


        if (fname.isEmpty()){
            firstName.setError("Please enter first name");
            firstName.requestFocus();
            return;
        }
        if (lname.isEmpty()){
            lastName.setError("Please enter last name");
            lastName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            inputEmail.setError("Please enter email");
            inputEmail.requestFocus();
            return;
        }
        if (!email.contains("@gmail.com")){
            inputEmail.setError("Please enter valid email address");
            inputEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Please enter valid email address");
            inputEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            inputPassword.setError("Please enter password");
            inputPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            inputPassword.setError("At least 6 characters long");
            inputPassword.requestFocus();
            return;
        }
        if(!conpassword.equals(password)){
            confirmPassword.setError("Password doesn't match");
            confirmPassword.requestFocus();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
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
                                    Toast.makeText(RegisterActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            UserData userData=new UserData(fname,lname,email);
                            fstore.collection("users")
                                    .document(userID).set(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: user Profile is created for " + userID);
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
}