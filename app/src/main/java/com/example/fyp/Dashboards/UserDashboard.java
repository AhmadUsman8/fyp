package com.example.fyp.Dashboards;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fyp.R;
import com.example.fyp.UserFragments.HomeFragment;
import com.example.fyp.UserFragments.InboxFragment;
import com.example.fyp.UserFragments.JobsFragment;
import com.example.fyp.UserFragments.ProfileFragment;
import com.example.fyp.UserFragments.SearchFragment;
import com.example.fyp.UserFragments.displayJobs;
import com.example.fyp.databinding.UserBottomNavbarBinding;

public class UserDashboard extends AppCompatActivity {

    UserBottomNavbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.user_bottom_navbar);

        binding = UserBottomNavbarBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.user_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.user_inbox:
                    replaceFragment(new InboxFragment());
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