package com.vesvihaan;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleApiHelper implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    GoogleApiClient client;
    Context context;
    public GoogleApiHelper(Context context){
        this.context=context;
        buildGoogleApiClient();
        connect();
    }
    public void connect(){
        if(client!=null){
            client.connect();
        }
    }
    public void disconnect(){
        if(client!=null && client.isConnected()){
            client.disconnect();
        }
    }
    public GoogleApiClient getGoogleApiClient(){
        return client;
    }
    protected synchronized void buildGoogleApiClient(){
        client=new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
