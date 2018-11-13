package com.inotes.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.inotes.Adapters.NotesListAdapter;
import com.inotes.Adapters.SemesterNotesAdapter;
import com.inotes.Models.NotesName;
import com.inotes.Models.Semester;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SemesterNotes extends Fragment {

    SessionManager manager;
    RecyclerView recyclerView2;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference;
    List<Semester> list = new ArrayList<>();
    SemesterNotesAdapter adapter;
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.noteslist,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Semesters");

        manager=new SessionManager();
        progressBar =(ProgressBar)view.findViewById(R.id.progressbar);

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
       // recyclerView2.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));



        getDataFromFirebase();




        return view;
    }

    private void getDataFromFirebase() {

        String course = "";
        course = manager.getPrefs(getActivity(), "course");
        firebaseStorage = FirebaseStorage.getInstance();
        progressBar.setVisibility(View.VISIBLE);

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Teacher").child(course);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("Datasnapchot", "   " + dataSnapshot.getValue());

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    list.clear();
                    for (DataSnapshot child : children) {
                        Semester notesName = new Semester();
                        String folders = child.getKey();
                        notesName.setSemester(folders);
                        Log.e("dataaddhogya", " g " + child.getKey());
                        list.add(notesName);
                    }
                    Log.e("size", " g " + list.size());
                    progressBar.setVisibility(View.GONE);
                    setAdapter();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setAdapter() {

        try {
            adapter = new SemesterNotesAdapter(getActivity(), list, getActivity().getSupportFragmentManager());
            recyclerView2.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
