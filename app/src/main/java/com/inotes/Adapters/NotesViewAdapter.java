package com.inotes.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.inotes.Fragments.ImageViewFragment;
import com.inotes.Fragments.NotesFragment;
import com.inotes.Fragments.Syllabus;
import com.inotes.Globalfunctions;
import com.inotes.Models.NotesName;
import com.inotes.Models.NotesUrl;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class NotesViewAdapter extends RecyclerView.Adapter<NotesViewAdapter.RecyclerViewHolder> {

    Context context;
    public static List<NotesName> notesNames;
    public static List<NotesUrl> newList;
    NotesName model;
    public int check = 0;
    FragmentManager fragmentManager;
    public DatabaseReference databaseReference;
    SessionManager manager;
    String folder;
    List<NotesUrl> list = new ArrayList<>();
    ProgressDialog progressDialog;


    public NotesViewAdapter(Context context, List<NotesName> notesNames,List<NotesUrl> newlist, String folder, FragmentManager fragmentManager) {

        this.context = context;
        this.notesNames = notesNames;
        this.newList=newlist;
        this.fragmentManager = fragmentManager;
        this.folder = folder;
        manager = new SessionManager();
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notes_list_recy, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {

        model = notesNames.get(position);
        Log.e("modelvalue","H H"+model.getFoldername());
        holder.foldername.setText(model.getFoldername());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folder.contains("pdf")) {
                    Globalfunctions.setPresViewIndex(3);
                    Bundle bundle = new Bundle();
                    bundle.putString("folder", newList.get(position).getUrl());
                    Fragment fragment = new Syllabus();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment).commit();
                }else{
                    Globalfunctions.setPresViewIndex(1);
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos",position);
                    Fragment fragment = new ImageViewFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment).commit();
                }
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        Log.e("notessize", " g " + notesNames.size());
        return notesNames.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView foldername;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            foldername = (TextView) itemView.findViewById(R.id.foldername);


        }
    }

}
