package com.vesvihaan;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectionUtil {
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
        boolean isConnected=activeNetwork!=null && activeNetwork.isConnected();
        return isConnected;
    }
}
