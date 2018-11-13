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
import com.inotes.Adapters.SemesterAdapter;
import com.inotes.Models.NotesName;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SemesterListFragment extends Fragment {

    SessionManager manager;
    RecyclerView recyclerView2;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference;
    List<NotesName> list = new ArrayList<>();
    SemesterAdapter adapter;
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.semester_fragment,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Select Semester");

        manager=new SessionManager();
        progressBar =(ProgressBar)view.findViewById(R.id.progressbar);

        recyclerView2 = (RecyclerView) view.findViewById(R.id.semesterRecy);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        adapter = new SemesterAdapter(getActivity(),getActivity().getSupportFragmentManager());
        recyclerView2.setAdapter(adapter);
       // recyclerView2.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));






        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
