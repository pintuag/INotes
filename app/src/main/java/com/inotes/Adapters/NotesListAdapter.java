package com.inotes.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inotes.Fragments.ImageViewFragment;
import com.inotes.Fragments.NotesFragment;
import com.inotes.Fragments.NotesListFragment;
import com.inotes.Fragments.Syllabus;
import com.inotes.Fragments.UploadNotes;
import com.inotes.Globalfunctions;
import com.inotes.ImageCompressor;
import com.inotes.Models.NotesName;
import com.inotes.Models.NotesUrl;
import com.inotes.Models.UploadUri;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.RecyclerViewHolder> {

    Context context;
    public List<NotesName> notesNames;
    NotesName model;
    public int check = 0;
    FragmentManager fragmentManager;
    public DatabaseReference databaseReference;
    SessionManager manager;
    ProgressDialog progressDialog;
    String semester,subject;


    public NotesListAdapter(Context context, List<NotesName> notesNames,String semester,String subject, FragmentManager fragmentManager) {

        this.context = context;
        this.notesNames=notesNames;
        this.semester=semester;
        this.subject=subject;
        this.fragmentManager=fragmentManager;
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
        if(model.getFoldername().contains("pdf")){
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_picture_as_pdf_black_24dp));
        }else{
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_image_black_24dp));
        }
       // holder.foldername.setText("nammememme");

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        Log.e("notessize"," g "+notesNames.size());
        return notesNames.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView foldername;
        ImageView icon;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            foldername=(TextView)itemView.findViewById(R.id.foldername);
            icon = (ImageView)itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("semester",semester);
                    bundle.putString("subject", subject);
                    bundle.putString("folder", notesNames.get(getAdapterPosition()).foldername);
                    Fragment fragment = new NotesFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment).commit();
                }
            });


        }
    }

}
