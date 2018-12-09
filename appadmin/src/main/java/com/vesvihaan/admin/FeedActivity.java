package com.vesvihaan.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedActivity extends AppCompatActivity{

    ImageButton camera;
    EditText editText;
    Button addFeed;
    ImageView feedImageView;

    ProgressDialog progressDialog;

    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Add feed");
        setSupportActionBar(toolbar);
        camera=findViewById(R.id.camera);
        feedImageView=findViewById(R.id.image);
        feed_image_id=FirebaseDatabase.getInstance().getReference().push().getKey();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        editText=findViewById(R.id.feedEdtText);
        progressDialog=new ProgressDialog(this);

        //required for running camera properly
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        addFeed=findViewById(R.id.addFeeds);
        addFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(compressedImage!=null && !editText.getText().toString().isEmpty()){
                    progressDialog.setTitle("Uploading new feed");
                    progressDialog.setMessage("Please wait for a while...");
                    progressDialog.show();
                    FeedAdderHelper feedAdderHelper=new FeedAdderHelper();
                    feedAdderHelper.setFeedImageURI(compressedImage);
                    Feed feed=new Feed();
                    feed.setFeedImageUrl(null);
                    feed.setFeedId(feed_image_id);
                    feed.setFeedTitle(editText.getText().toString());
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    feed.setFeedDateTime(simpleDateFormat.format(new Date()));
                    feedAdderHelper.setFeed(feed);
                    feedAdderHelper.setOnFeedAddListener(new FeedAdderHelper.OnFeedAddListener() {
                        @Override
                        public void onAdded() {
                            Toast.makeText(getApplicationContext(),"Successfully added new feed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailed(String message) {
                            progressDialog.dismiss();
                        }
                    });
                    feedAdderHelper.uploadImageAndFeed();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter all content",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    Uri capturedfileUri;
    public final static int CAMERA_CAPTURED_CODE=7895;
    public void openCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        capturedfileUri=Uri.fromFile(createImageFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT,capturedfileUri);
        startActivityForResult(intent,CAMERA_CAPTURED_CODE);
    }


    private String feed_image_id;
    private File createImageFile(){
        File imageFile=new File(Environment.getExternalStorageDirectory()+"/Vihaan/",feed_image_id+"_original.jpg");
        return imageFile;
    }

    Uri compressedImage=null;
    public void result(Intent intent) {
        if(intent!=null){
            try {
                compressedImage=new ImageCompressorHelper(getApplicationContext())
                        .setHeight(700)
                        .setWidth(500)
                        .setImageName(String.valueOf(feed_image_id))
                        .setSource(Constant.UPLOAD_IMAGE_USING_CAMERA)
                        .compressImage(capturedfileUri);
                Glide.with(this).load(compressedImage).into(feedImageView);
            }
            catch (Exception e){
                Log.i("Hello",e.getLocalizedMessage());

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Toast.makeText(getApplicationContext(),resultCode+" "+requestCode,Toast.LENGTH_SHORT).show();
        if(requestCode==CAMERA_CAPTURED_CODE){
            if(resultCode==RESULT_OK){
                result(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
