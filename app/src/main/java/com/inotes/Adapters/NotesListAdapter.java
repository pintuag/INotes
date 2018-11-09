package com.inotes.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inotes.Fragments.ImageViewFragment;
import com.inotes.Fragments.UploadNotes;
import com.inotes.ImageCompressor;
import com.inotes.Models.NotesName;
import com.inotes.Models.UploadUri;
import com.inotes.R;

import java.util.ArrayList;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.RecyclerViewHolder> {

    Context context;
    public List<NotesName> notesNames;
    public int check = 0;
    FragmentManager fragmentManager;


    public NotesListAdapter(Context context, List<NotesName> notesNames, FragmentManager fragmentManager) {

        this.context = context;
        this.notesNames=notesNames;
        this.fragmentManager=fragmentManager;
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

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return notesNames.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView foldername;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            foldername=(TextView)itemView.findViewById(R.id.foldername);

        }
    }

}
