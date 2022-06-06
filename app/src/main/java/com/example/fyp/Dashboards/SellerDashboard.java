package com.example.fyp.Dashboards;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fyp.R;
import com.example.fyp.SellerFragments.SellerHomeFragment;
import com.example.fyp.SellerFragments.SellerInboxFragment;
import com.example.fyp.SellerFragments.SellerManageJobsFragment;
import com.example.fyp.SellerFragments.SellerProfileFragment;
import com.example.fyp.UserFragments.InboxFragment;
import com.example.fyp.databinding.SellerBottomNavbarBinding;

public class SellerDashboard extends AppCompatActivity {

    SellerBottomNavbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.seller_bottom_navbar);

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