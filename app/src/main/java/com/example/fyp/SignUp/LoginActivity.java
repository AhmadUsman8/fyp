package com.example.fyp.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Dashboards.SellerDashboard;
import com.example.fyp.R;
import com.example.fyp.Dashboards.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{
    TextView createNewAccount,forgotPassword;
    Button btnLogin;
    private EditText inputEmail,inputPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createNewAccount = (TextView) findViewById(R.id.createNewAccount);
        forgotPassword=(TextView) findViewById(R.id.forgotPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        mAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, com.example.fyp.SignUp.forgotPassword.class);
                startActivity(intent);
            }
        });
    }
    private void loginUser(){
        String email=inputEmail.getText().toString().trim();
        String password=inputPassword.getText().toString().trim();

        if (email.isEmpty()){
            inputEmail.setError("Please enter email");
            inputEmail.requestFocus();
            return;
        }
        if (!email.contains("@gmail.com")){
            inputEmail.setError("Incorrect");
            inputEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Incorrect");
            inputEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            inputPassword.setError("Please enter password");
            inputPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            inputPassword.setError("Incorrect");
            inputPassword.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()) {
                    FirebaseUser validEmail = FirebaseAuth.getInstance().getCurrentUser();
                    if (!validEmail.isEmailVerified()) {
                        validEmail.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                    }

                    else {
                        Intent intent = new Intent(LoginActivity.this, UserDashboard.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"Incorrect credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}