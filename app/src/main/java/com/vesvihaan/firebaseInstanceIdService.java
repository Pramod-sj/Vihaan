package com.vesvihaan;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.content.ContentValues.TAG;

/**
 * Created by pramod_sj on 2/12/17.
 */

public class firebaseInstanceIdService extends FirebaseInstanceIdService {
    String REG_TOKEN="REG_TOKEN";
    @Override
    public void onTokenRefresh(){
        super.onTokenRefresh();
        String recent_token=FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recent_token);
        FirebaseMessaging.getInstance().subscribeToTopic("alldevices");
    }


}
