package com.vesvihaan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;


/**
 * Created by pramod_sj on 25/11/17.
 */

public class aboutapp_Activity extends AppCompatActivity {
    TextView t11;
    ImageView img1,img2;
    String versionName = BuildConfig.VERSION_NAME;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.aboutapp);
        ShimmerFrameLayout container = (ShimmerFrameLayout) findViewById(R.id.shimmerlayout);
        container.setDuration(2000);
        container.setIntensity(0.15f);
        container.startShimmerAnimation();
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
