package com.vesvihaan.admin;

import android.app.Activity;
import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class VihaanAdmin extends Application {

    public static VihaanAdmin instance;
    GoogleApiHelper googleApiHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        instance=this;
        googleApiHelper=new GoogleApiHelper(instance);
    }

    public static VihaanAdmin getInstance(){
        return instance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance(){
        return this.googleApiHelper;
    }

    public static GoogleApiHelper getGoogleApiHelper(){
        return getInstance().getGoogleApiHelperInstance();
    }
}
