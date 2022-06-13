package com.example.fyp.Dashboards;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fyp.R;
import com.example.fyp.SellerFragments.SellerHomeFragment;
import com.example.fyp.SellerFragments.SellerInboxFragment;
import com.example.fyp.SellerFragments.SellerManageJobsFragment;
import com.example.fyp.SellerFragments.SellerProfileFragment;
import com.example.fyp.SignUp.LoginActivity;
import com.example.fyp.UserFragments.InboxFragment;
import com.example.fyp.databinding.SellerBottomNavbarBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SellerDashboard extends AppCompatActivity {

    SellerBottomNavbarBinding binding;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.seller_bottom_navbar);

        if(mAuth.getCurrentUser()!=null&&mAuth.getCurrentUser().isEmailVerified()==false){
            Toast.makeText(this.getApplicationContext(), "Check verification email", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent=new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        binding = SellerBottomNavbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new SellerHomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.user_home:
                    replaceFragment(new SellerHomeFragment());
                    break;
                case R.id.user_inbox:
                    replaceFragment(new SellerInboxFragment());
                    break;
                case R.id.user_jobs:
                    replaceFragment(new SellerManageJobsFragment());
                    break;
                case R.id.user_profile:
                    replaceFragment(new SellerProfileFragment());
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