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
import com.inotes.Adapters.NotesViewAdapter;
import com.inotes.Globalfunctions;
import com.inotes.Models.NotesName;
import com.inotes.Models.NotesUrl;
import com.inotes.Models.User;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    SessionManager manager;
    RecyclerView recyclerView2;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference,databaseReference2;
    List<NotesName> list = new ArrayList<>();
    List<NotesUrl> urls = new ArrayList<>();
    NotesViewAdapter adapter;
    String subject,semester;
    ProgressBar progressBar;
    String folder;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.noteslist,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notes List");

        manager=new SessionManager();
        progressBar =(ProgressBar)view.findViewById(R.id.progressbar);
        semester=getArguments().getString("semester");
        subject=getArguments().getString("subject");

        folder = getArguments().getString("folder");
        Log.e("FOldervalue",""+folder);

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());



        getDataFromFirebase();

        return view;
    }

    private void getDataFromFirebase() {

        final String course = manager.getPrefs(getActivity(), "course");
        firebaseStorage = FirebaseStorage.getInstance();
        progressBar.setVisibility(View.VISIBLE);

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child("Teacher").child(course).child(semester).child(subject).child(folder);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("Datasnapchot", "   " + dataSnapshot.getValue());

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    list.clear();
                    urls.clear();
                    for (DataSnapshot child : children) {
                        NotesName notesName = new NotesName();
                        String folders = child.getKey();
                        notesName.setFoldername(folders);
                        Log.e("dataaddhogya", " g " + child.getKey());
                        list.add(notesName);
                        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users")
                                .child("Teacher").child(course).child(semester).child(subject).child(folder).child(folders);
                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.e("jsondata", "   " + dataSnapshot.getValue());

                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for(DataSnapshot newchild : children){
                                    String url = newchild.getValue(String.class);
                                    NotesUrl notesUrl = new NotesUrl();
                                    notesUrl.setUrl(url);
                                    Log.e("dataaa", url+" g " + newchild.getKey());
                                    urls.add(notesUrl);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

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
        adapter = new NotesViewAdapter(getActivity(),list,urls,folder,getActivity().getSupportFragmentManager());
        recyclerView2.setAdapter(adapter);
    }


}
