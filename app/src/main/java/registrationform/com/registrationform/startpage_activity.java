package registrationform.com.registrationform;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pramo on 10/18/2017.
 */

public class startpage_activity extends AppCompatActivity {
    Button regbutton;
    TextView txt1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);
        txt1=(TextView)findViewById(R.id.txt1);
        regbutton=(Button)findViewById(R.id.regbutton);
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(startpage_activity.this,MainActivity.class);
                startActivity(i);
            }
        });
        txt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(startpage_activity.this,MainWindow.class);
                startActivity(i2);
            }
        });


    }



}
