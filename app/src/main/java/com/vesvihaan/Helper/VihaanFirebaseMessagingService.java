package com.vesvihaan.Helper;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class VihaanFirebaseMessagingService extends FirebaseMessagingService {
    String TAG="FirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationHelper helper=new NotificationHelper(getApplicationContext());
        Log.i(TAG,"Message data payload"+remoteMessage.getData());
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String imageUrl = remoteMessage.getData().get("imageUrl");
        String feedId = remoteMessage.getData().get("dishId");
        helper.showNotification(title, body, imageUrl, feedId);
    }
}
