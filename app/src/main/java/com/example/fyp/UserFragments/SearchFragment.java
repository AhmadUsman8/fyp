
package com.example.fyp.UserFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp.R;
import com.example.fyp.ShowWorker;

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
        list.add("Car Wash");
        list.add("Cleaning");
        list.add("Design");
        list.add("Home repait");
        list.add("Laundry");
        list.add("Constructor");
        list.add("Painter");
        list.add("Carpenter");
        list.add("Other");

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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, ShowWorker.newInstance(list.get(i)))
                        .addToBackStack("main")
                        .commit();
            }
        });

    }
}

