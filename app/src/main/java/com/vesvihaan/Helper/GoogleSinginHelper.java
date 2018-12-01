package com.vesvihaan.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vesvihaan.Constant;
import com.vesvihaan.R;
import com.vesvihaan.Vihaan;

public class GoogleSinginHelper {
    public final static int GOOGLE_SIGIN_CODE=1000;
    Context context;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    OnSigninListener onSigninListener;
    SharedPreferences.Editor editor;
    public GoogleSinginHelper(Context context,OnSigninListener onSigninListener) {
        this.context = context;
        this.onSigninListener=onSigninListener;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        editor=sharedPreferences.edit();
    }

    public void buildGoogleSigninOption(){
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        auth=FirebaseAuth.getInstance();
        googleSignInClient= GoogleSignIn.getClient(context,googleSignInOptions);
    }

    public boolean isGooglePlayServiceAvailable(){
        int resultCode= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if(resultCode== ConnectionResult.SUCCESS){
            return true;
        }
        return false;
    }

    public GoogleSignInClient getGoogleSignInClient(){
        return googleSignInClient;
    }


    public void signInUsingData(Intent data){
        GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        }
        else {
            onSigninListener.onFailure(result.getStatus().getStatusMessage());
        }
    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount account){
        final AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    editor.putBoolean(Constant.SHAREDPREF_USER_LOGGEDIN,true).commit();
                    onSigninListener.onSuccess();
                }
                else{
                    onSigninListener.onFailure(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        GoogleApiClient googleApiClient= Vihaan.getGoogleApiHelper().getGoogleApiClient();
        Auth.GoogleSignInApi.signOut(googleApiClient);
        editor.putBoolean(Constant.SHAREDPREF_USER_LOGGEDIN,false).commit();
    }

    private SharedPreferences sharedPreferences;


}
