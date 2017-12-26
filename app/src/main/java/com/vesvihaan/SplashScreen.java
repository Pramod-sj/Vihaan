package com.vesvihaan;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;


public class SplashScreen extends AppCompatActivity{
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int totalcount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final ProgressBar spinner;
        spinner = (ProgressBar) findViewById(R.id.probar);
        spinner.setMax(100);
        spinner.setVisibility(View.VISIBLE);
        prefs=getPreferences(Context.MODE_PRIVATE);
        editor=prefs.edit();
        totalcount=prefs.getInt("counter",0);
        totalcount++;
        editor.putInt("counter",totalcount);
        editor.commit();
        if(totalcount>=2){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(SplashScreen.this,MainWindow.class);
                    startActivity(intent);
                    spinner.setVisibility(View.GONE);
                    finish();
                }
            }, 1000);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, startpage_activity.class);
                    startActivity(intent);
                    spinner.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Notification();
                        }
                    }, 7000);
                    finish();

                }
            }, 1500);
        }
    }


    public void Notification(){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainWindow.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(r.getString(R.string.notication_title))
                .setContentText(r.getString(R.string.notication))
                .setAutoCancel(true)
                .build();
        notification.vibrate = new long[]{150, 300, 150, 400};
        Uri no = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ring = RingtoneManager.getRingtone(getApplicationContext(), no);
        ring.play();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
