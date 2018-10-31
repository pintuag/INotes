package com.inotes.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

public class NotesListFragment extends Fragment {

    SessionManager manager;
    RecyclerView recyclerView2;
    FirebaseStorage firebaseStorage;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.noteslist,container,false);

        manager=new SessionManager();

        /*recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView2.setItemViewCacheSize(20);
        recyclerView2.setDrawingCacheEnabled(true);
        recyclerView2.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);*/

        getDataFromFirebase();




        return view;
    }

    private void getDataFromFirebase() {

        String folder = "";
        folder = manager.getPrefs(getActivity(), "course");
        firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference("bba");

        Log.e("bukettt",storageReference.getStorage()+"  df  "+storageReference.getBucket());
       // for(int i = 0;i<storageReference.)



    }

    @Override
    public void onResume() {
        super.onResume();
        //  Globalfunctions.setFragment_index(12);
    }


}
