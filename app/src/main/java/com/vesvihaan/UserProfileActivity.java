package com.vesvihaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    TextView userName,userEmail;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        user=FirebaseAuth.getInstance().getCurrentUser();
        GlideApp.with(this).load(user.getPhotoUrl())
                .into((CircleImageView)findViewById(R.id.userImageProfile));
        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userEmail);
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());
    }

}
