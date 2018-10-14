package com.inotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.inotes.SharedPref.SessionManager;

public class NavigationActivity extends AppCompatActivity {


    TextView textView;

    SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        textView=(TextView)findViewById(R.id.textview);

        manager= new SessionManager();

        if(!manager.getPrefs(NavigationActivity.this,"Loggedin",false)){

            startActivity(new Intent(NavigationActivity.this,StartingActivity.class));
            finish();
        }

//        int usertype=getIntent().getExtras().getInt("usertype");
        /*if(usertype==1){
            textView.setText("Hello Teachers");
        }
        else if(usertype==2){
            textView.setText("Hello Students");
        }*/
    }
}
