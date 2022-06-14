package com.example.fyp.Dashboards;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fyp.Chat.ChatMainActivity;
import com.example.fyp.R;
import com.example.fyp.SignUp.LoginActivity;
import com.example.fyp.UserFragments.HomeFragment;
import com.example.fyp.UserFragments.InboxFragment;
import com.example.fyp.UserFragments.JobsFragment;
import com.example.fyp.UserFragments.ProfileFragment;
import com.example.fyp.UserFragments.SearchFragment;
import com.example.fyp.UserFragments.displayJobs;
import com.example.fyp.databinding.UserBottomNavbarBinding;
import com.google.firebase.auth.FirebaseAuth;

public class UserDashboard extends AppCompatActivity {

    UserBottomNavbarBinding binding;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.user_bottom_navbar);

        if(mAuth.getCurrentUser()!=null&&mAuth.getCurrentUser().isEmailVerified()==false){
            Toast.makeText(this.getApplicationContext(), "Email not verified", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent=new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        binding = UserBottomNavbarBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.user_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.user_inbox:
                    replaceFragment(new ChatMainActivity());
                    break;
                case R.id.user_search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.user_jobs:
                    replaceFragment(new displayJobs());
                    break;
                case R.id.user_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

}