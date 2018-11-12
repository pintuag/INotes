package com.inotes.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inotes.Globalfunctions;
import com.inotes.Models.NotesUrl;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Syllabus extends Fragment {

    PDFView pdfView;
    ProgressBar progressBar;
    SessionManager manager;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.syllabus_fragment,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notes/Syllabus");



        pdfView=(PDFView)view.findViewById(R.id.pdfsyllabus);
        progressBar=(ProgressBar)view.findViewById(R.id.progressbar);
        manager=new SessionManager();

        String url  = "";
        if(Globalfunctions.presViewIndex==3){

            Globalfunctions.setPresViewIndex(10);
            url = getArguments().getString("folder");
            Log.e("printurl"," g "+url);

        }else {
            if (manager.getPrefs(getActivity(), "course").equals("bca")) {

                url = "https://firebasestorage.googleapis.com/v0/b/inotes-5cd3d.appspot.com/o/Courses%2FBca%2Fsyllbca191011.pdf?alt=media&token=ca94381f-c2a2-4d21-85e8-bcf4e5349a8b";
            } else if (manager.getPrefs(getActivity(), "course").equals("bba")) {

                Log.e("Bba meaagyake", "mee");
                url = "https://firebasestorage.googleapis.com/v0/b/inotes-c2295.appspot.com/o/Courses%2FBba%2Fbbagen.pdf?alt=media&token=12778ce9-defa-441d-abb6-67a6bb02c072";

            }
        }


        try {
            new RetrievePDFStream().execute(url);
        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //  Globalfunctions.setFragment_index(12);
    }

    public class RetrievePDFStream extends AsyncTask<String,Void,InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            progressBar.setVisibility(View.VISIBLE);
            InputStream inputStream=null;

            try{
                Log.e("fucntion call hua","hai"+strings.length);

                URL urlx=new URL(strings[0]);
                Log.e("fucntion call hua",strings[0]+"  hai  "+strings.length);
                HttpURLConnection urlConnection=(HttpURLConnection) urlx.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());

                }
            }catch (Exception e){
                return null;
            }
            return inputStream;

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {

            try {
                if (inputStream != null) {
                    progressBar.setVisibility(View.GONE);
                    pdfView.fromStream(inputStream).load();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
