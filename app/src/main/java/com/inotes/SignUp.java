package com.inotes;

import android.app.ProgressDialog;
import android.content.Intent;
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

    private EditText email, fullname;
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

        usertype=getIntent().getExtras().getInt("usertype");
        Log.e("USertype",""+usertype);

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
            final String name = fullname.getText().toString();
            final String mobile = rmobn.getText().toString();
            Log.e("VALUES",id+"     "+pass+"     "+name+"     "+mobile);

            auth.createUserWithEmailAndPassword(id, pass)
                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.e("TASKKKKK!",task.getResult()+"      "+task.getException());

                            if (task.isSuccessful()) {

                                User user = new User(
                                        name,
                                        ""+usertype,
                                        mobile
                                );
                                String typeuser = "";
                                Log.e("USERTYPE", "" + usertype);
                                if (usertype == 1) {
                                    typeuser = "Teacher";
                                } else if (usertype == 2) {
                                    typeuser = "Student";
                                }

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(typeuser).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push()
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Log.e("TAkkkkk!",task.getResult()+"      "+task.getException());
                                        Log.e("TASKKK",""+task.toString());
                                        progressDialog.dismiss();
                                        if (task.isSuccessful()) {

                                            progressDialog.dismiss();
                                            Toast.makeText(SignUp.this, "SignUp Successfully.", Toast.LENGTH_LONG).show();
                                            manager.setPrefs(SignUp.this, "Loggedin", true);
                                            startActivity(new Intent(SignUp.this, NavigationActivity.class).putExtra("usertype",1));
                                            finish();
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(SignUp.this, "Please Try Again Later.", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });

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


        String id = email.getText().toString();
        String pass = password.getText().toString();
        String name=fullname.getText().toString();
        String mobile=rmobn.getText().toString();



        if (id.isEmpty()) {
            email.setError("Enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 6) {
            password.setError("Password too short, enter minimum 6 characters!");
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
