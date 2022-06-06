package com.example.fyp.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    EditText inputEmail;
    TextView loginPage;
    Button reset;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail=(EditText) findViewById(R.id.inputEmail);
        loginPage=(TextView) findViewById(R.id.loginPage);
        reset=(Button) findViewById(R.id.reset);
        mAuth=FirebaseAuth.getInstance();

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgotPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email=inputEmail.getText().toString().trim();

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
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPassword.this, "Reset link sent to your email", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(forgotPassword.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}