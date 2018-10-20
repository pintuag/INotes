package com.inotes.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class DetailedFragment extends Fragment {


    Button syllabus,subjects,uploadnotes;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.detailed_fragment,container,false);


       syllabus=(Button)view.findViewById(R.id.syllabus);
       subjects=(Button)view.findViewById(R.id.subjects);
       uploadnotes=(Button)view.findViewById(R.id.uploadnotes);

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


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  Globalfunctions.setFragment_index(12);
    }



}
