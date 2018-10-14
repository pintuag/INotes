package com.inotes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

//import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleSignin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "BaseActivity";
    protected FirebaseUser mFirebaseUser;
    protected GoogleApiClient mGoogleApiClient;
    protected FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private GoogleSignInOptions mGso;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG,"onCreate: started");
        mAuth= FirebaseAuth.getInstance();
        if (mAuth != null){
            //Log.d(TAG,"onCreate: checking");
            mFirebaseUser=mAuth.getCurrentUser();

        }
        mGso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,mGso)
                .build();
    }
    protected void showProgressDialog(){

        if (mProgressDialog==null){
            mProgressDialog=new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
        }
        mProgressDialog.show();
    }

    protected void dismissProgressDialog(){
        if (mProgressDialog!=null && mProgressDialog.isShowing()){

            mProgressDialog.dismiss();
        }


    }
    protected void signOut(){
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dismissProgressDialog();
    }
}

   /* private void signIn() {

        showProgressDialog();
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Log.e(TAG, "onActivityResult: is being created");

        if (resultCode == Activity.RESULT_OK){

            if (requestCode==RC_SIGN_IN){

                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                if (result.isSuccess()){

                    GoogleSignInAccount account = result.getSignInAccount();

                    firebaseAuthWithGoogle(account);
                }


            }

        }
        else{
            dismissProgressDialog();
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        showProgressDialog();


        // Log.e(TAG, "firebaseAuthWithGoogle: started");

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            // Log.e(TAG, "onComplete: google account task is successful");

                            //to save email in shared preference
                            String userEmail = mAuth.getCurrentUser().getEmail();
                            String userName = mAuth.getCurrentUser().getDisplayName();

                            Log.e("USERDETAILS",""+userEmail+"   "+userName);
                            dismissProgressDialog();


                        }

                    }
                });

    }*/

   /* signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });
*/