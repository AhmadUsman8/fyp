package com.example.fyp.UserFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fyp.R;
import com.example.fyp.ShowWorker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    Button mButtonInvite;
    CardView mElectrician, mMechanic, mCarWash, mCleaning,
            mDesign, mHomeRepair, mLaundry,
            mConstruction, mPainter, mCarpenter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonInvite = view.findViewById(R.id.buttonInvite);
        mElectrician = view.findViewById(R.id.cdv1);
        mMechanic = view.findViewById(R.id.cdv2);
        mCarWash = view.findViewById(R.id.cdv3);
        mCleaning = view.findViewById(R.id.cdv4);
        mDesign = view.findViewById(R.id.cdv5);
        mHomeRepair = view.findViewById(R.id.cdv6);
        mLaundry = view.findViewById(R.id.cdv7);
        mConstruction = view.findViewById(R.id.cdv8);
        mPainter = view.findViewById(R.id.cdv9);
        mCarpenter = view.findViewById(R.id.cdv10);


        mButtonInvite.setOnClickListener(v -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Stay tuned!", Toast.LENGTH_SHORT).show();
        });

        mElectrician.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Electrician"))
                    .addToBackStack("main")
                    .commit();
        });
        mMechanic.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Mechanic"))
                    .addToBackStack("main")
                    .commit();
        });
        mCarWash.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Car Wash"))
                    .addToBackStack("main")
                    .commit();
        });
        mCleaning.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Cleaning"))
                    .addToBackStack("main")
                    .commit();
        });
        mDesign.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Design"))
                    .addToBackStack("main")
                    .commit();
        });
        mHomeRepair.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Home repair"))
                    .addToBackStack("main")
                    .commit();
        });
        mLaundry.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Laundry"))
                    .addToBackStack("main")
                    .commit();
        });
        mConstruction.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Construction"))
                    .addToBackStack("main")
                    .commit();
        });
        mPainter.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Painter"))
                    .addToBackStack("main")
                    .commit();
        });
        mCarpenter.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, ShowWorker.newInstance("Carpenter"))
                    .addToBackStack("main")
                    .commit();
        });
    }
}