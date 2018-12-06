package com.vesvihaan.admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {

    SignInButton signInButton;
    GoogleSinginHelper googleSinginHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        googleSinginHelper=new GoogleSinginHelper(this, new GoogleSinginHelper.OnSigninListener() {
            @Override
            public void onSuccess() {
                isAdminUser();
            }
            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();

            }
        });
        isAdminUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signInButton=findViewById(R.id.googleSigninButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(googleSinginHelper.isGooglePlayServiceAvailable()){
                    googleSinginHelper.buildGoogleSigninOption();
                    Intent intent=googleSinginHelper.getGoogleSignInClient().getSignInIntent();
                    startActivityForResult(intent,GoogleSinginHelper.GOOGLE_SIGIN_CODE);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please update your google play sevice",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GoogleSinginHelper.GOOGLE_SIGIN_CODE){
            if(resultCode==RESULT_OK){
                googleSinginHelper.signInUsingData(data);
            }
        }
    }

    private void isAdminUser(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseDatabase.getInstance().getReference("Admins").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean isAdmin=false;
                    for (DataSnapshot snapshot :dataSnapshot.getChildren()) {
                        Log.i(snapshot.getKey(),snapshot.getValue(String.class));
                        if(snapshot.getValue(String.class).equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            isAdmin=true;
                        }
                    }
                    if(isAdmin){
                        Toast.makeText(getApplicationContext(),"You are authorize to you this app",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SigninActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please contact developer for admin access",Toast.LENGTH_SHORT).show();
                        googleSinginHelper.signOut();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
