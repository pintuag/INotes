package com.inotes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inotes.Models.User;
import com.inotes.SharedPref.SessionManager;
import com.inotes.Utility.Utility;

public class Login extends GoogleSignin {

    private static final int RC_SIGN_IN = 21;
    private SignInButton signInButton;
    private Button loginButton;
    private EditText email;
    private EditText password;
    TextView signUp;
    SessionManager manager;
    int usertype;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        manager= new SessionManager();
        loginButton=(Button)findViewById(R.id.login);
        email = (EditText) findViewById(R.id.prompt_name);

        password = (EditText) findViewById(R.id.prompt_password);
        signUp=(TextView)findViewById(R.id.signUp);
        usertype= getIntent().getExtras().getInt("usertype");


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,SignUp.class);
                i.putExtra("usertype",usertype);
                startActivity(i);
            }
        });





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login()
    {
        // validate user input first
        if(!validate())
        {
            onLoginFailed();
            return ;
        }

        loginButton.setEnabled(false);// disable Login button
        // create progress dialog with default spinner style
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String id = email.getText().toString();
        String pass = password.getText().toString();
        //authenticate user


        try {
            mAuth.signInWithEmailAndPassword(id, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                // there was an error

                                // check network status
                                if (!Utility.isNetworkAvailable(Login.this)) {
                                    Toast.makeText(Login.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(Login.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                loginButton.setEnabled(true);
                            } else {
                                progressDialog.dismiss();
                                try {
                                  //  final FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                    Log.e("userdetials", "   " + mFirebaseUser.getEmail());
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Student");
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Log.e("Datasnapchot","   "+dataSnapshot.getValue());
                                           /* User user = dataSnapshot.child(mFirebaseUser.getUid()).getValue(User.class);

                                            Log.e("usercourse", "" + user.getCourse());*/
                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                        for(DataSnapshot child : children){
                                            User user = child.getValue(User.class);
                                            Log.e("usercourse", "" + user.getCourse());
                                            manager.setPrefs(Login.this,"course",user.getCourse());
                                        }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    if (usertype == 1) {
                                        manager.setPrefs(Login.this, "usertype", "1");
                                    } else if (usertype == 2) {
                                        manager.setPrefs(Login.this, "usertype", "2");
                                    }
                                manager.setPrefs(Login.this, "Loggedin", true);
                                startActivity(new Intent(Login.this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void onLoginFailed()
    {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }
    public boolean validate()
    {
        boolean valid = true;

        String id = email.getText().toString();
        String pass = password.getText().toString();

        if (id.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else  {
            email.setError(null);
        }

        Log.e("Lowercase",""+id.toLowerCase());

        if(usertype==1){
            Log.e("ISSME Aa gyame", id.toLowerCase().contains("vk@jagannatht.com")+"     ll  "+usertype);
            if(!id.toLowerCase().endsWith("@jagannatht.com")){
                Log.e("ISSME Aa gyame",""+usertype);
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                valid=false;
            }
        } else if(usertype==2){
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

        return valid;

    }
}
