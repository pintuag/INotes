package com.inotes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.inotes.Models.User;
import com.inotes.SharedPref.SessionManager;
import com.inotes.Utility.Utility;

public class SignUp extends AppCompatActivity {

    private EditText email, fullname,course;
    private EditText password;
    private AutoCompleteTextView rmobn;
    ImageButton pstatus, pstatush;
    Button signUpbutton;
    private FirebaseAuth auth;
    TextView login;
    SessionManager manager;
    int usertype;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        manager= new SessionManager();
        auth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.prompt_name);
        fullname = (EditText) findViewById(R.id.fname);
        password = (EditText) findViewById(R.id.prompt_password);
        rmobn = (AutoCompleteTextView) findViewById(R.id.rmobn);
        pstatus = (ImageButton) findViewById(R.id.pstatus);
        pstatush = (ImageButton) findViewById(R.id.pstatush);
        signUpbutton= (Button) findViewById(R.id.signUp);
        login=(TextView)findViewById(R.id.login);
        course=(EditText)findViewById(R.id.course_value);

        usertype=getIntent().getExtras().getInt("usertype");
        Log.e("USertype",""+usertype);

        if(usertype==1){
            course.setVisibility(View.GONE);
        }else if(usertype==2){
            course.setVisibility(View.VISIBLE);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,Login.class));
            }
        });


        pstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                password.setSelection(password.getText().length());


                pstatush.setVisibility(View.VISIBLE);
                pstatus.setVisibility(View.GONE);

            }

        });
        pstatush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pstatus.setVisibility(View.VISIBLE);
                pstatush.setVisibility(View.GONE);
                password.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                password.setSelection(password.getText().length());
            }
        });

        signUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    private void signup() {

        try {

            if(!validate())
            {
                //onLoginFailed();
                return ;
            }
            // validate user input first
            //  signUpbutton.setEnabled(false); // disable signup button

            // create progress dialog with default spinner style
            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            // Handler used to simulate authentication logic execution time
            // progress dialog will appear for 3 seconds
            Log.e("VALUES","+"+email.getText().toString());

            String id = email.getText().toString();
            String pass = password.getText().toString();
            final String coourse=course.getText().toString();
            final String name = fullname.getText().toString().toLowerCase();
            final String mobile = rmobn.getText().toString();
            Log.e("VALUES",id+"     "+pass+"     "+name+"     "+mobile);

            auth.createUserWithEmailAndPassword(id, pass)
                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.e("TASKKKKK!",task.getResult()+"      "+task.getException());

                            if (task.isSuccessful()) {
                                User user=null;
                                if(usertype==1) {
                                     user= new User(
                                            name,
                                            "" + usertype,
                                            mobile
                                    );
                                }else if(usertype==2){
                                    user= new User(
                                            name,
                                            "" + usertype,
                                            mobile,
                                            coourse
                                    );
                                }
                                String typeuser = "";
                                Log.e("USERTYPE", "" + usertype);
                                if (usertype == 1) {
                                    typeuser = "Teacher";
                                    manager.setPrefs(SignUp.this,"usertype","1");
                                } else if (usertype == 2) {
                                    typeuser = "Student";
                                    manager.setPrefs(SignUp.this,"usertype","2");
                                }

                                try {
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(typeuser).push()
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Log.e("TAkkkkk!", task.getResult() + "      " + task.getException());
                                            Log.e("TASKKK", "" + task.toString());
                                            progressDialog.dismiss();
                                            if (task.isSuccessful()) {

                                                progressDialog.dismiss();
                                                Toast.makeText(SignUp.this, "SignUp Successfully.", Toast.LENGTH_LONG).show();
                                                manager.setPrefs(SignUp.this, "Loggedin", true);
                                                manager.setPrefs(SignUp.this,"course",coourse);
                                                startActivity(new Intent(SignUp.this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                    finishAffinity();
                                                }else{
                                                    finish();
                                                }
                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(SignUp.this, "Please Try Again Later.", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "Something Went Wrong.", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        signUpbutton.setEnabled(true);

    }

    private void onSignupSuccess()
    {
        Toast.makeText(getBaseContext(), "Signup Succeeded", Toast.LENGTH_SHORT).show();
        signUpbutton.setEnabled(true);
    }


    public boolean validate()
    {
        boolean valid = true;


        String cr=course.getText().toString();
        String id = email.getText().toString();
        String pass = password.getText().toString();
        String name=fullname.getText().toString();
        String mobile=rmobn.getText().toString();

        if(usertype==2) {
            if (cr.isEmpty()) {
                course.setError("Enter a valid course details");
                valid = false;
            } else {
                course.setError(null);
            }
        }

        if (id.isEmpty()) {
            email.setError("Enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if(usertype==1){
            Log.e("ISSME Aa gyame", id.toLowerCase().contains("vk@jagannatht.com")+"     ll  "+usertype);
            if(!id.toLowerCase().endsWith("@jagannatht.com")){
                Log.e("ISSME Aa gyame",""+usertype);
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                valid=false;
            }
        }else if(usertype==2){
            Log.e("ISSME Aa gyame", id.toLowerCase().contains("vk@jagannaths.com")+"     ll  "+usertype);
            if(!id.toLowerCase().endsWith("@jagannaths.com")){
                Log.e("ISSME Aa gyame",""+usertype);
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                valid=false;
            }
        }


        if (pass.isEmpty() || pass.length() < 6 || pass.length() > 10) {
            password.setError("Password should be atleast 6 characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (name.isEmpty()) {
            fullname.setError("Enter Name");
            valid = false;
        } else {
            fullname.setError(null);
        }

        if (mobile.isEmpty()) {
            rmobn.setError("Enter Phone");
            valid = false;
        } else {
            rmobn.setError(null);
        }

        return valid;
    }
}
