package com.abhishekwagh.assignment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.abhishekwagh.assignment.EntryViewModel;
import com.abhishekwagh.assignment.R;
import com.abhishekwagh.assignment.adapters.EntryListsAdapter;
import com.abhishekwagh.assignment.db.AppDatabase;
import com.abhishekwagh.assignment.db.Entry;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class EntryListsFragment extends Fragment {

    RecyclerView recyclerView;
    EntryListsAdapter entryListAdapter;
    EditText search;
    List<Entry> entryList;
    ArrayList<Entry> alContactModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        entryListAdapter = new EntryListsAdapter(this.getContext());

        AppDatabase db = AppDatabase.getDbInstance(this.getContext());
        entryList = db.entryDao().getAllEntries();
        entryListAdapter.setEntryList(entryList);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    //    EntryViewModel entryViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);
      //  alContactModels = new ArrayList<>();

//        EntryViewModel.getList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ContactsModel ->
//        {
////            System.out.println("Size: " + Entry.size());
////            Entry contactsModelObj;
//            for (int i = 0; i < ContactsModel.size(); i++) {
//                contactsModelObj = ContactsModel.get(i);
//                contactsModelObj.getName();
//                alContactModels.add(contactsModelObj);
//            }



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(entryListAdapter);

        search = view.findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }

        }

        );

    }

    void filter(String text){
        List<Entry> temp = new ArrayList<>();
        for(Entry d: entryList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        entryListAdapter.setEntryList(temp);
    }



}