package com.vesvihaan.Helper;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vesvihaan.UI.Activity.MainActivity;

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
        helper.setPendingIntent(MainActivity.class);
        helper.showNotification(title, body, imageUrl);
    }
}
