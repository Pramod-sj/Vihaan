package com.vesvihaan.UI.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.vesvihaan.Constant;
import com.vesvihaan.UI.Fragment.EventFragment;
import com.vesvihaan.GlideApp;
import com.vesvihaan.Helper.GoogleSinginHelper;
import com.vesvihaan.UI.Fragment.HomeFragment;
import com.vesvihaan.Helper.OnSigninListener;
import com.vesvihaan.R;
import com.vesvihaan.UI.Fragment.RoundedBottomSheetDialogFragment;
import com.vesvihaan.UI.Fragment.SponsorFragment;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements OnSigninListener, SharedPreferences.OnSharedPreferenceChangeListener {


    ViewPager viewPager;
    static CoordinatorLayout coordinatorLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0,true);
                    return true;
                case R.id.navigation_event:
                    viewPager.setCurrentItem(1,true);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2,true);
                    return true;
            }
            return false;
        }
    };
    BottomNavigationView navigation;
    Toolbar toolbar;
    GoogleSinginHelper googleSinginHelper;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        googleSinginHelper=new GoogleSinginHelper(this,this);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        coordinatorLayout=findViewById(R.id.container);
        viewPager=findViewById(R.id.mainViewPager);
        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
        showProfileImage();
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                navigation.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        if(FirebaseAuth.getInstance().getCurrentUser()==null) {
            menu.findItem(R.id.menu_logout).setVisible(false);
        }
        else{
            menu.findItem(R.id.menu_logout).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void showProfileImage(){
        CircleImageView circleImageView=findViewById(R.id.profileImage);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            GlideApp.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).placeholder(R.drawable.user_profile_drawable).into(circleImageView);
        }
        else {
            Log.i("Not logged in","true");
            GlideApp.with(this).load(R.drawable.user_profile_drawable).placeholder(R.drawable.user_profile_drawable).into(circleImageView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onProfileImageClick(View v){
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            if(googleSinginHelper.isGooglePlayServiceAvailable()){
                googleSinginHelper.buildGoogleSigninOption();
                Intent intent=googleSinginHelper.getGoogleSignInClient().getSignInIntent();
                startActivityForResult(intent,GoogleSinginHelper.GOOGLE_SIGIN_CODE);
            }
            else {
                showSnackBar("Please update your google play sevice");
            }
        }
        else{
            Pair pair=new Pair(findViewById(R.id.profileImage),"profile_transition");
            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(this,pair);
            Intent intent=new Intent(this,UserProfileActivity.class);
            startActivity(intent,options.toBundle());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GoogleSinginHelper.GOOGLE_SIGIN_CODE:
                if(resultCode==RESULT_OK){
                    googleSinginHelper.signInUsingData(data);
                }
        }
    }

    @Override
    public void onSuccess() {
        recreate();
    }

    @Override
    public void onFailure(String errorMessage) {
        showSnackBar(errorMessage);
    }

    public static void showSnackBar(String message){
        Snackbar snackbar=Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        CoordinatorLayout.LayoutParams params= (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.setAnchorId(R.id.navigation);
        params.anchorGravity= Gravity.TOP;
        params.gravity=Gravity.TOP;
        snackbar.getView().setLayoutParams(params);
        snackbar.show();
    }

    public static String getPostString(HashMap<String,String> data) {

        StringBuilder stringBuilder=new StringBuilder();
        boolean isFirst=true;
        for(Map.Entry<String,String> entry:data.entrySet()){
            if(isFirst){
                isFirst=false;
            }
            else{
                stringBuilder.append("&");
            }
            try {
                stringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            catch(Exception e){
                Log.i("ERROR",e.getLocalizedMessage());
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_us:
                startActivity(new Intent(this,AboutAppActivity.class));
                break;
            case R.id.menu_logout:
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
                        showSnackBar("Successfully signed out");
                    }

                    @Override
                    public void onLeftButtonClick(RoundedBottomSheetDialogFragment dialogFragment) {
                        dialogFragment.dismiss();
                    }
                });
                sheetDialogFragment.show(getSupportFragmentManager(),"SignoutDialog");
                break;
            case R.id.share:
                shareApp();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void shareApp(){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT,"Download Vihaan'19 app now\n"+getResources().getString(R.string.google_play_store_app_url));
        startActivity(Intent.createChooser(intent,"Choose one of the app to share"));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(Constant.SHAREDPREF_USER_LOGGEDIN))
        {
            recreate();
        }
    }


    public class CustomPagerAdapter extends FragmentPagerAdapter{

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return new HomeFragment();
                case 1:
                    return new EventFragment();
                case 2:
                    return new SponsorFragment();
                    default:
                        return new HomeFragment();

            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onDestroy() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }
}
