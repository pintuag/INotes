package com.inotes.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inotes.Fragments.NotesListFragment;
import com.inotes.Fragments.SubjectsNotes;
import com.inotes.Models.NotesName;
import com.inotes.Models.Semester;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.List;

public class SubjectsNotesAdapter extends RecyclerView.Adapter<SubjectsNotesAdapter.RecyclerViewHolder> {

    Context context;
    NotesName model;
    public int check = 0;
    List<Semester> list;
    FragmentManager fragmentManager;
    SessionManager manager;
    String semester;


    public SubjectsNotesAdapter(Context context, List<Semester> list,String semester, FragmentManager fragmentManager) {

        this.context = context;
        this.list=list;
        this.fragmentManager=fragmentManager;
        this.semester=semester;
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

        holder.semester.setText(list.get(position).getSemester());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("semester",semester);
                bundle.putString("subject", list.get(position).getSemester());
                Fragment fragment = new NotesListFragment();
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
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView semester;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            semester=(TextView)itemView.findViewById(R.id.semester);


        }
    }

}
