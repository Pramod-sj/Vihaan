package com.vesvihaan.Helper;

import android.support.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vesvihaan.Model.User;

public class LikeHelper {
    User user;
    DatabaseReference feedRef;
    OnLikeUnLikeListener onLikeUnLikeListener;
    public LikeHelper(OnLikeUnLikeListener onLikeUnLikeListener) {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            user = new User();
            user.setUserName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            user.setUserPhotoUrl(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
            user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
        feedRef= FirebaseDatabase.getInstance().getReference().child("Feeds");
        this.onLikeUnLikeListener=onLikeUnLikeListener;
    }

    public interface OnLikeUnLikeListener{
        void onLike();
        void onUnLike();
    }

    public void like(final String feedId){
        //checks whether feed exists or not
        feedRef.child(feedId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    feedRef.child(feedId).child("feedLikes").push().setValue(user);
                    onLikeUnLikeListener.onLike();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void unlike(final String dishId){
        feedRef.child(dishId).child("feedLikes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String key=snapshot.getKey();
                    String username=dataSnapshot.child(key).child("uid").getValue(String.class);
                    if(username.equals(user.getUid())){
                        feedRef.child(dishId).child("feedLikes").child(key).removeValue();
                        onLikeUnLikeListener.onUnLike();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}