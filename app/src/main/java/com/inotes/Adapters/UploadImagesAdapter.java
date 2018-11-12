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
import android.text.Editable;
import android.text.TextWatcher;
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


import com.bumptech.glide.Glide;
import com.inotes.Fragments.ImageViewFragment;
import com.inotes.Fragments.UploadNotes;
import com.inotes.Globalfunctions;
import com.inotes.ImageCompressor;
import com.inotes.Models.UploadUri;
import com.inotes.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadImagesAdapter extends RecyclerView.Adapter<UploadImagesAdapter.RecyclerViewHolder> {

    Context context;
    public List<UploadUri> uris;
    public int check = 0;
    public Boolean ignoreOnclick = false;
    public Boolean selectedMode = false;
    FragmentManager fragmentManager;
    public static List<UploadUri> urls = new ArrayList<>();


    public UploadImagesAdapter(Context context, List<UploadUri> uris,FragmentManager fragmentManager) {

        this.context = context;
        this.uris = uris;
        this.urls=uris;
        this.fragmentManager=fragmentManager;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.images, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {

        try {
            holder.pos = position;
            if(uris.get(position).getUri()!=null) {
                final String uri = uris.get(position).getUri();

                final Uri parseuri = Uri.parse(uris.get(position).getUri());
                Glide.with(context).load(parseuri).into(holder.imageView);
                holder.uri = parseuri;
              //  Log.e("IMAGEURI", "" + holder.uri);
               /* if (!parseuri.toString().isEmpty()) {
                    Bitmap bitmap = ImageCompressor.decodeSampledBitmapFromResource(context, parseuri, 300, 300);
                    //bitmap = getCircularBitmap(bitmap);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, 250, 250);

                    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                    holder.imageView.setImageBitmap(bitmap);
                }*/
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    Log.e("oncclick", "megya me");
                    ViewOfImage(position);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,check;
        int pos;
        Uri uri = null;
        ProgressBar progressBar;
        RelativeLayout parent_relative;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
           // Log.e("urisssincount", "" + uris.size());
            progressBar=(ProgressBar)itemView.findViewById(R.id.progress);
            imageView=(ImageView)itemView.findViewById(R.id.old_pres) ;
            check=(ImageView)itemView.findViewById(R.id.check);

            parent_relative=(RelativeLayout)itemView.findViewById(R.id.parent);

           // delete = (TextView) itemView.findViewById(R.id.btn_del);


          /*  delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deletee(pos);
                }
            });*/

        }
    }

    public void ViewOfImage(int pos) {

        Globalfunctions.setPresViewIndex(2);
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        final Fragment fragment = new ImageViewFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.add(R.id.frame, fragment).commit();


    }

}
