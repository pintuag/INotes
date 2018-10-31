package com.inotes;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.inotes.Fragments.DetailedFragment;
import com.inotes.Fragments.HomeFragment;
import com.inotes.SharedPref.SessionManager;

public class NavigationActivity extends AppCompatActivity {


    SessionManager manager;
    String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager= new SessionManager();

        if(!manager.getPrefs(NavigationActivity.this,"Loggedin",false)){

            startActivity(new Intent(NavigationActivity.this,StartingActivity.class));
            finish();
        }
        setContentView(R.layout.activity_navigation);

        usertype = manager.getPrefs(NavigationActivity.this,"usertype");

       if(usertype.equals("2")){

           Fragment fragment = new DetailedFragment();
           FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
           fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                   android.R.anim.fade_out);
           fragmentTransaction.replace(R.id.frame, fragment).commit();
       }else if(usertype.equals("1")){

           Fragment fragment = new HomeFragment();
           FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
           fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                   android.R.anim.fade_out);
           fragmentTransaction.replace(R.id.frame, fragment).commit();
       }





      //  int usertype=getIntent().getExtras().getInt("usertype");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            manager.logout(NavigationActivity.this);
            Intent login = new Intent(NavigationActivity.this, StartingActivity.class);
            startActivity(login);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
