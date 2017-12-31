package com.vesvihaan;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pramod_sj on 2/12/17.
 */

public class Firebase_Messaging_Service extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
            String title,message;
            title= remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");
            String imageUri = remoteMessage.getData().get("image");
            Bitmap bitmap = getBitmapfromUrl(imageUri);
            Intent i = new Intent(this, MainWindow.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
            nb.setContentTitle(title);
            nb.setContentText(message);
            nb.setAutoCancel(true);
            nb.setContentIntent(pi);
            nb.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
            nb.setVibrate(new long[]{150, 300, 150, 400});
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            nb.setSound(sounduri);
            nb.setSmallIcon(R.drawable.ic_notification);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, nb.build());
    }
        public Bitmap getBitmapfromUrl(String imageUrl) {
                try {
                        URL url = new URL(imageUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        return bitmap;

                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;

                }
        }
}
