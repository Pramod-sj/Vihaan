package com.vesvihaan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pramod_sj on 25/11/17.
 */

public class AboutAppActivity extends AppCompatActivity {
    TextView t11;
    ImageView img1,img2;
    String versionName = BuildConfig.VERSION_NAME;
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_about_app);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("About");
        setSupportActionBar(toolbar);
        t11=(TextView)findViewById(R.id.vernumber);
        t11.setText("Version "+versionName);
        img1= (ImageView) findViewById(R.id.fb);
        img2=(ImageView)findViewById(R.id.insta);
        img1.setOnClickListener(new ToWebLink());
        img2.setOnClickListener(new ToWebLink());
    }
    class ToWebLink implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.fb) {
                Uri uri = Uri.parse("http://www.facebook.com/vihaan2k18/");
                Intent i1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i1);
            }
            else if(view.getId()==R.id.insta) {
                Uri uri = Uri.parse("http://www.instagram.com/vihaan2k18/");
                Intent i2 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i2);
            }
        }
    }
}
