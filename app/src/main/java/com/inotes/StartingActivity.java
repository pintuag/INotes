package com.inotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartingActivity extends AppCompatActivity {


    Button teacher,student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        teacher=(Button)findViewById(R.id.teacher);
        student=(Button)findViewById(R.id.student);


        final Intent i = new Intent(StartingActivity.this,Login.class);

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("usertype", 1);
                startActivity(i);
            }
        });


        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("usertype", 2);
                startActivity(i);
            }
        });




    }
}
