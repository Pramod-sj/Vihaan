package com.vesvihaan;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
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
