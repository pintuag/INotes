package com.inotes.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inotes.NavigationActivity;
import com.inotes.R;
import com.inotes.SharedPref.SessionManager;

public class HomeFragment extends Fragment {

    TextView textView;

    SessionManager manager;
    Button bca,bba;
    String usertype;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.home_fragment,container,false);

        manager= new SessionManager();
        usertype = manager.getPrefs(getActivity(),"usertype");

        textView=(TextView)view.findViewById(R.id.text);
        bca=(Button)view.findViewById(R.id.bca);
        bba=(Button)view.findViewById(R.id.bba);


        if(manager.getPrefs(getActivity(),"usertype").equals("1")){
            String hello="hello teachers";
            textView.setText(hello);
        }
        else if(manager.getPrefs(getActivity(),"usertype").equals("2")){
            String hello="hello students";
            textView.setText(hello);
        }

        bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.setPrefs(getActivity(),"course","bca");
                Fragment fragment = new DetailedFragment();
                FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment).commit();

            }
        });

        bba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.setPrefs(getActivity(),"course","bba");
                Fragment fragment = new DetailedFragment();
                FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
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