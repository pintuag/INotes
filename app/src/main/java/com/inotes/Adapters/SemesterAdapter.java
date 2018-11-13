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
import com.inotes.Fragments.NotesFragment;
import com.inotes.Fragments.SubjectListFragment;
import com.inotes.Models.NotesName;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.RecyclerViewHolder> {

    Context context;
    NotesName model;
    public int check = 0;
    FragmentManager fragmentManager;
    SessionManager manager;


    public SemesterAdapter(Context context, FragmentManager fragmentManager) {

        this.context = context;
        this.fragmentManager=fragmentManager;
        manager = new SessionManager();
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.semester_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {


        final String semnum = "Semester - "+ (position+1);
        holder.semester.setText(semnum);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sem", semnum);
                bundle.putString("semnum",(position+1)+"");
                Fragment fragment = new SubjectListFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment).commit();
            }
        });
       // holder.foldername.setText("nammememme");

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return 6;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView semester;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            semester=(TextView)itemView.findViewById(R.id.semester);


        }
    }

}
