package com.vesvihaan.UI.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vesvihaan.GlideApp;
import com.vesvihaan.Helper.GoogleSinginHelper;
import com.vesvihaan.R;
import com.vesvihaan.UI.Fragment.RoundedBottomSheetDialogFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    TextView userName,userEmail;
    FirebaseUser user;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
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
        googleSinginHelper=new GoogleSinginHelper(this,null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    GoogleSinginHelper googleSinginHelper;
    public void onLogoutButtonClicked(View view){
        RoundedBottomSheetDialogFragment sheetDialogFragment=new RoundedBottomSheetDialogFragment();
        sheetDialogFragment.setButton1_text("No");
        sheetDialogFragment.setButton2_text("Yes");
        sheetDialogFragment.setTitle("Do you want to logout");
        sheetDialogFragment.setDesc("Are you sure?");
        sheetDialogFragment.setButtonClickListener(new RoundedBottomSheetDialogFragment.OnButtonClickListener() {
            @Override
            public void onRightButtonClick(final RoundedBottomSheetDialogFragment dialogFragment) {
                googleSinginHelper.signOut();
                dialogFragment.dismiss();
                sharedPreferences.edit().putBoolean("Tooltip",true).commit();
                finish();
            }

            @Override
            public void onLeftButtonClick(RoundedBottomSheetDialogFragment dialogFragment) {
                dialogFragment.dismiss();
            }
        });
        sheetDialogFragment.show(getSupportFragmentManager(),"SignoutDialog");
    }
}
