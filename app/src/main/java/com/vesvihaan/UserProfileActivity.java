package com.vesvihaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    TextView userName,userEmail;
    FirebaseUser user;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user=FirebaseAuth.getInstance().getCurrentUser();
        GlideApp.with(this).load(user.getPhotoUrl())
                .into((CircleImageView)findViewById(R.id.userImageProfile));
        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userEmail);
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
