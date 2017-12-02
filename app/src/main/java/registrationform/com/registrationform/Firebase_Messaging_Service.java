package registrationform.com.registrationform;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by pramod_sj on 2/12/17.
 */

public class Firebase_Messaging_Service extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        Intent i=new Intent(this,MainWindow.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder nb=new Notification.Builder(this);
        nb.setContentTitle("VIHAAN");
        nb.setAutoCancel(true);
        nb.setContentIntent(pi);
        nb.setContentText(remoteMessage.getNotification().getBody());
        nb.setSmallIcon(android.R.drawable.ic_popup_reminder);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,nb.build());

    }
}
