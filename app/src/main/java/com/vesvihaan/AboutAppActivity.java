package com.vesvihaan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pramod_sj on 25/11/17.
 */

public class AboutAppActivity extends AppCompatActivity {
    TextView t11;
    String versionName = BuildConfig.VERSION_NAME;
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_about_app);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("About");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t11=findViewById(R.id.vernumber);
        t11.setText("Version "+versionName);
        GlideApp.with(this).load(getResources().getString(R.string.dev_profile_pic_url)).placeholder(R.drawable.user_profile_drawable).transition(DrawableTransitionOptions.withCrossFade(200)).skipMemoryCache(false).into((CircleImageView)findViewById(R.id.devImage));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onUrlClick(View view){
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
        else if(view.getId()==R.id.websitelink){
            Uri uri = Uri.parse("http://www.vesvihaan.com/");
            Intent i2 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i2);
        }
        else if(view.getId()==R.id.githubLink){
            Uri uri = Uri.parse("http://www.github.com/pramod-sj/vihaan");
            Intent i2 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i2);
        }

    }
}
