package com.vesvihaan.admin;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FeedAdderHelper {
    Uri imageURI;
    Feed feed;
    public void setFeed(Feed feed){
        this.feed=feed;
    }
    public void setFeedImageURI(Uri imageURI){
        this.imageURI=imageURI;
    }
    String uploadedImageUrl=null;
    public void uploadImageAndFeed(){
        final FirebaseStorage storage= FirebaseStorage.getInstance();
        final StorageReference reference=storage.getReference().child("Feeds/"+feed.getFeedId()+".jpg");
        reference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            feed.setFeedImageUrl(String.valueOf(uri));
                            uploadDish();
                        }
                    });
                }
            }
        });
    }

    private void uploadDish(){
        FirebaseDatabase.getInstance().getReference("Feeds").child(feed.getFeedId()).setValue(feed).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    onFeedAddListener.onAdded();
                }
                else if(task.isCanceled()){
                    onFeedAddListener.onFailed(task.getException().getMessage());
                }
            }
        });

    }
    OnFeedAddListener onFeedAddListener;
    public void setOnFeedAddListener(OnFeedAddListener onFeedAddListener){
        this.onFeedAddListener=onFeedAddListener;

    }
    public interface OnFeedAddListener{
        void onAdded();
        void onFailed(String message);
    }

}
