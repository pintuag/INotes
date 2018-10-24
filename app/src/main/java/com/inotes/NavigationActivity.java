package com.inotes;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.inotes.Fragments.DetailedFragment;
import com.inotes.SharedPref.SessionManager;

public class NavigationActivity extends AppCompatActivity {


    TextView textView;

    SessionManager manager;
    Button bca,bba;
    String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager= new SessionManager();
        usertype = manager.getPrefs(NavigationActivity.this,"usertype");

        if(!manager.getPrefs(NavigationActivity.this,"Loggedin",false)){

            startActivity(new Intent(NavigationActivity.this,StartingActivity.class));
            finish();
        }
        setContentView(R.layout.activity_navigation);

       if(usertype.equals("2")){

           Fragment fragment = new DetailedFragment();
           FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
           fragmentTransaction.addToBackStack(null);
           fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                   android.R.anim.fade_out);
           fragmentTransaction.replace(R.id.frame, fragment).commit();
       }



        textView=(TextView)findViewById(R.id.text);
        bca=(Button)findViewById(R.id.bca);
        bba=(Button)findViewById(R.id.bba);





      //  int usertype=getIntent().getExtras().getInt("usertype");
        if(manager.getPrefs(NavigationActivity.this,"usertype").equals("1")){
            String hello="hello teachers";
            textView.setText(hello);
        }
        else if(manager.getPrefs(NavigationActivity.this,"usertype").equals("2")){
            String hello="hello students";
            textView.setText(hello);
        }

        bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.setPrefs(NavigationActivity.this,"course","bca");
                Fragment fragment = new DetailedFragment();
                FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment).commit();

            }
        });

        bba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.setPrefs(NavigationActivity.this,"course","bba");
                Fragment fragment = new DetailedFragment();
                FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment).commit();


            }
        });




    }
}
