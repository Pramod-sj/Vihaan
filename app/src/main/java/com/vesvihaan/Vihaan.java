package com.vesvihaan;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vesvihaan.Helper.GoogleApiHelper;

public class Vihaan extends Application {
    GoogleApiHelper googleApiHelper;
    public static Vihaan instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        googleApiHelper=new GoogleApiHelper(instance);
        Log.i("onCreate","Vihaan application class");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseMessaging.getInstance().subscribeToTopic(Constant.FIREBASE_FEED_TOPIC).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("Successfully","register app to users");
                }
                else {
                    Log.i("Failed","fail to registered");
                }
            }
        });
    }


    public static Vihaan getInstance(){
        return instance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance(){
        return this.googleApiHelper;
    }

    public static GoogleApiHelper getGoogleApiHelper(){
        return getInstance().getGoogleApiHelperInstance();
    }

}
