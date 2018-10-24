package com.inotes.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

public class DetailedFragment extends Fragment {


    Button syllabus,subjects,uploadnotes;
    SessionManager manager;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.detailed_fragment,container,false);

        manager=new SessionManager();
       syllabus=(Button)view.findViewById(R.id.syllabus);
       subjects=(Button)view.findViewById(R.id.subjects);
       uploadnotes=(Button)view.findViewById(R.id.uploadnotes);

        if(manager.getPrefs(getActivity(),"usertype").equals("1")){
            uploadnotes.setVisibility(View.VISIBLE);
        }
        else if(manager.getPrefs(getActivity(),"usertype").equals("2")){
            String hello="hello students";
            uploadnotes.setVisibility(View.GONE);
        }

       syllabus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Fragment fragment = new Syllabus();
               FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                       android.R.anim.fade_out);
               fragmentTransaction.replace(R.id.frame, fragment).commit();


           }
       });
       subjects.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Snackbar.make(view,"Not Implemented Yet",Snackbar.LENGTH_SHORT).show();
           }
       });
        uploadnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view,"Not Implemented Yet",Snackbar.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  Globalfunctions.setFragment_index(12);
    }



}
