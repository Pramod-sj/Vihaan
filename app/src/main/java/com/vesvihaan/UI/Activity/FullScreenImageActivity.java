package com.vesvihaan.UI.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.vesvihaan.GlideApp;
import com.vesvihaan.Helper.NotificationHelper;
import com.vesvihaan.Model.Feed;
import com.vesvihaan.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class FullScreenImageActivity extends AppCompatActivity {

    ImageView fullScreenImageView;
    Feed feed;
    android.support.v7.widget.Toolbar toolbar;
    int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE=1000;
    ContentLoadingProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        feed=(Feed)getIntent().getExtras().getSerializable("Feed");
        setContentView(R.layout.activity_full_screen_image);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(feed.getFeedTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fullScreenImageView=findViewById(R.id.fullScreenImageView);
        GlideApp.with(this).load(feed.getFeedImageUrl()).placeholder(R.color.colorPrimaryDark).transition(DrawableTransitionOptions.withCrossFade(200)).into(fullScreenImageView);
        progressBar=findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
        }
        else if(item.getItemId()==R.id.downloadImage){
            downloadImage();
        }
        return super.onOptionsItemSelected(item);

    }

    private void downloadImage(){
        if(checkStoragePermission()) {
            progressBar.setVisibility(View.VISIBLE);
            Log.i("Data","Feeds/"+feed.getFeedId()+".jpg");
            FirebaseStorage.getInstance().getReference().child("Feeds/"+feed.getFeedId()+".jpg").getBytes(1024*1024)
                    .addOnCompleteListener(new OnCompleteListener<byte[]>() {
                        @Override
                        public void onComplete(@NonNull Task<byte[]> task) {

                            NotificationHelper helper=new NotificationHelper(getApplicationContext());

                            try {
                                byte[] data = task.getResult();
                                File dir=new File(Environment.getExternalStorageDirectory()+"/Vihaan'19");
                                dir.mkdirs();
                                File file=new File(dir,feed.getFeedTitle() + ".jpg");
                                file.createNewFile();
                                if(file.exists()) {
                                    FileOutputStream fio = new FileOutputStream(file);
                                    fio.write(data);
                                    fio.flush();
                                }
                                helper.setPendingIntent(Uri.fromFile(file));
                                helper.showNotification("Sucessfully downloaded ","file is stored under "+file.getAbsolutePath());
                            }
                            catch (Exception e){
                                helper.setPendingIntent(MainActivity.class);
                                helper.showNotification("Download failed","please try after sometime");
                                Log.i("Error",e.getLocalizedMessage());
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }
    public boolean checkStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            }
            return false;
        }
        return true;
    }

}
