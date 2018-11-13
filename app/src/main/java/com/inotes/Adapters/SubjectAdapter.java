package com.inotes.Adapters;

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

import com.inotes.Fragments.NotesFragment;
import com.inotes.Fragments.UploadNotes;
import com.inotes.Models.NotesName;
import com.inotes.Models.Subjects;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.RecyclerViewHolder> {

    Context context;
    NotesName model;
    List<Subjects> list;
    Subjects subjects;
    public int check = 0;
    FragmentManager fragmentManager;
    String semnum;
    SessionManager manager;


    public SubjectAdapter(Context context, List<Subjects> list,String semnum, FragmentManager fragmentManager) {

        this.context = context;
        this.list=list;
        this.semnum=semnum;
        this.fragmentManager=fragmentManager;
        manager = new SessionManager();
        Log.e("subjectSize",""+list.size());
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.subjects_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {


        subjects = list.get(position);
        Log.e("subjectttt",""+subjects.getSubjects());
        holder.subjects.setText(subjects.getSubjects());
        holder.code.setText(subjects.getCoursecode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sem",semnum);
                bundle.putString("subject", subjects.getSubjects());
                Fragment fragment = new UploadNotes();
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

        TextView subjects,code;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            subjects=(TextView)itemView.findViewById(R.id.subjects);
            code=(TextView)itemView.findViewById(R.id.code);


        }
    }

}
