package com.inotes.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.inotes.Adapters.NotesListAdapter;
import com.inotes.Login;
import com.inotes.Models.NotesName;
import com.inotes.Models.User;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class NotesListFragment extends Fragment {

    SessionManager manager;
    RecyclerView recyclerView2;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference;
    List<NotesName> list = new ArrayList<>();
    NotesListAdapter adapter;
    String subject,semester;
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.noteslist,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notes Name");

        manager=new SessionManager();
        progressBar =(ProgressBar)view.findViewById(R.id.progressbar);
        semester=getArguments().getString("semester");
        subject=getArguments().getString("subject");

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
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child("Teacher").child(course).child(semester).child(subject);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("Datasnapchot", "   " + dataSnapshot.getValue());

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    list.clear();
                    for (DataSnapshot child : children) {
                        NotesName notesName = new NotesName();
                        String folders = child.getKey();
                        notesName.setFoldername(folders);
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
            adapter = new NotesListAdapter(getActivity(), list,semester,subject, getActivity().getSupportFragmentManager());
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
