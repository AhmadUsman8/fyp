package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fyp.SignUp.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfile extends AppCompatActivity {

    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        logout=(Button) findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(UserProfile.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}