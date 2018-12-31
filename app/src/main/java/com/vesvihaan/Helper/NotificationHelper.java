package com.vesvihaan.Helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.vesvihaan.R;
import com.vesvihaan.UI.Activity.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class NotificationHelper {
    private final static int NOTIFICATION_ID=99999;
    private final static String PRIMARY_CHANNEL="Vihaan channel";
    private final static String DEFAULT_CHANNEL="default channel";
    private Context context;
    private SharedPreferences preferences;
    public NotificationHelper(Context context){
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
        this.context=context;
        createNotificationChannel();
    }
    public void setPendingIntent(Uri uri){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"image/*");
        pendingIntent=PendingIntent.getActivity(context,11,intent,PendingIntent.FLAG_CANCEL_CURRENT);
    }
    public void setPendingIntent(Class intentTo){
        pendingIntent = PendingIntent.getActivity(context, 10, new Intent(context, intentTo), PendingIntent.FLAG_CANCEL_CURRENT);

    }
    PendingIntent pendingIntent;
    private NotificationCompat.Builder buildNotification(String title,String body,String imageUrl){
        Uri ringtoneManager=RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,PRIMARY_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setSound(ringtoneManager)
                .setContentIntent(pendingIntent);
        builder.setVibrate(new long[]{1000,1000});

        if (imageUrl!=null && !imageUrl.isEmpty()) {
            Bitmap bitmap = getBitmapFromUrl(imageUrl);
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
        }
        return builder;
    }
    public void showNotification(String title,String body,String imageUrl){
        NotificationManager notificationManager=getNotificationManager();
        notificationManager.notify(NOTIFICATION_ID,buildNotification(title,body,imageUrl).build());
    }

    public void showNotification(String title,String body){
        NotificationManager notificationManager=getNotificationManager();
        notificationManager.notify(NOTIFICATION_ID,buildNotification(title,body,null).build());
    }



    private NotificationManager getNotificationManager(){
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        return notificationManager;
    }

    private void createNotificationChannel(){
        NotificationChannel channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel=new NotificationChannel(PRIMARY_CHANNEL,DEFAULT_CHANNEL,NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getNotificationManager().createNotificationChannel(channel);
        }
    }

    private Bitmap getBitmapFromUrl(String url){
        Bitmap bitmap=null;
        try{
            URL url1=new URL(url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream= httpURLConnection.getInputStream();
            bitmap=BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
