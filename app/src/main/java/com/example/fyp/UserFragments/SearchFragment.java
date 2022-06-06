
package com.example.fyp.UserFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp.R;

import java.util.ArrayList;
import java.util.Collections;


public class SearchFragment extends Fragment {

    SearchView mSearchView;
    ListView mListView;
    ArrayList<String> list=new ArrayList<String>();
    ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView=(SearchView) view.findViewById(R.id.searchView);
        mListView=(ListView) view.findViewById(R.id.listView);

        list.add("Electrician");
        list.add("Mechanic");
        list.add("Plumber");
        list.add("Car detailing");
        list.add("Consultants");
        list.add("Blacksmith");
        list.add("Food delivery");
        list.add("Builder");
        list.add("Clothing");
        list.add("Makeup");
        list.add("Insurance coverage");
        list.add("Carpenter");
        list.add("Painter");
        list.add("Interior designer");

        Collections.sort(list);

        adapter=new ArrayAdapter<>(requireActivity().getApplicationContext(), android.R.layout.simple_list_item_1,list);
        mListView.setAdapter(adapter);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}

