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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    Button mButtonInvite;
    CardView mCdv1,mCdv2,mCdv3,mCdv4,mCdv5,mCdv6,mCdv7,mCdv8,mCdv9,mCdv10;

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
        mButtonInvite=view.findViewById(R.id.buttonInvite);
        mCdv1=view.findViewById(R.id.cdv1);
        mCdv2=view.findViewById(R.id.cdv2);
        mCdv3=view.findViewById(R.id.cdv3);
        mCdv4=view.findViewById(R.id.cdv4);
        mCdv5=view.findViewById(R.id.cdv5);
        mCdv6=view.findViewById(R.id.cdv6);
        mCdv7=view.findViewById(R.id.cdv7);
        mCdv8=view.findViewById(R.id.cdv8);
        mCdv9=view.findViewById(R.id.cdv9);
        mCdv10=view.findViewById(R.id.cdv10);


        mButtonInvite.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Stay tuned!", Toast.LENGTH_SHORT).show();
        });

        mCdv1.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Electrician", Toast.LENGTH_SHORT).show();
        });
        mCdv2.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Mechanic", Toast.LENGTH_SHORT).show();
        });
        mCdv3.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Car Wash", Toast.LENGTH_SHORT).show();
        });
        mCdv4.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Cleaning", Toast.LENGTH_SHORT).show();
        });
        mCdv5.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Design", Toast.LENGTH_SHORT).show();
        });
        mCdv6.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Home repair", Toast.LENGTH_SHORT).show();
        });
        mCdv7.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Laundry", Toast.LENGTH_SHORT).show();
        });
        mCdv8.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Construction", Toast.LENGTH_SHORT).show();
        });
        mCdv9.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Painter", Toast.LENGTH_SHORT).show();
        });
        mCdv10.setOnClickListener(v->{
            Toast.makeText(requireActivity().getApplicationContext(), "Carpenter", Toast.LENGTH_SHORT).show();
        });
    }
}