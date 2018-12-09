package com.vesvihaan.UI.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.vesvihaan.BuildConfig;
import com.vesvihaan.Constant;
import com.vesvihaan.GlideApp;
import com.vesvihaan.UI.Fragment.CustomInfoDialog;
import com.vesvihaan.UI.Fragment.DonateDialogFragment;
import com.vesvihaan.UI.Fragment.OpenSourceLibDialogFragment;
import com.vesvihaan.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pramod_sj on 25/11/17.
 */

public class AboutAppActivity extends AppCompatActivity {
    TextView t11;
    String versionName = BuildConfig.VERSION_NAME;
    Toolbar toolbar;
    BillingProcessor billingProcessor;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_about_app);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("About");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpBilling();
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

    public void onOthersClick(View view){
        switch (view.getId()){
            case R.id.openSourceLib:
                OpenSourceLibDialogFragment openSourceLibDialogFragment=new OpenSourceLibDialogFragment();
                openSourceLibDialogFragment.show(getSupportFragmentManager(),"OpenSourceLibDialog");
                break;
            case R.id.privacyPolicy:
                CustomInfoDialog dialog=new CustomInfoDialog();
                dialog.showPrivacyPolicy();
                dialog.show(getSupportFragmentManager(),"PrivacyPolicyDialog");
                break;
            case R.id.termAndCondition:
                CustomInfoDialog termConditionDialog=new CustomInfoDialog();
                termConditionDialog.showTermCondition();
                termConditionDialog.show(getSupportFragmentManager(),"TermAndConditionDialog");
                break;
        }
    }

    public void onDonateClick(View view){
        DonateDialogFragment donateDialogFragment=new DonateDialogFragment();
        donateDialogFragment.setOnDonateItemClickListener(new DonateDialogFragment.OnDonateItemClickListener() {
            @Override
            public void onItemClick(String itemName) {
                if(itemName.equals("COOKIE")){
                    if(!billingProcessor.isPurchased(Constant.GOOGLE_IN_APP_PRODUCT_COOKIE)) {
                        billingProcessor.purchase(AboutAppActivity.this, Constant.GOOGLE_IN_APP_PRODUCT_COOKIE);
                    }
                    else{
                        showSnackBar("You have already donated that, Thank you so much:)");
                    }
                }
                else if(itemName.equals("COFFEE")){
                    if(!billingProcessor.isPurchased(Constant.GOOGLE_IN_APP_PRODUCT_COFFEE)) {
                        billingProcessor.purchase(AboutAppActivity.this, Constant.GOOGLE_IN_APP_PRODUCT_COFFEE);
                    }
                    else{
                        showSnackBar("You have already donated that, Thank You so much:)");
                    }
                }
                else if(itemName.equals("GIFT")){
                    if(!billingProcessor.isPurchased(Constant.GOOGLE_IN_APP_PRODUCT_GIFT)) {
                        billingProcessor.purchase(AboutAppActivity.this, Constant.GOOGLE_IN_APP_PRODUCT_GIFT);
                    }
                    else{
                        showSnackBar("You have already donated that, Thank you so much:)");
                    }
                }
            }
        });
        donateDialogFragment.show(getSupportFragmentManager(),"DonateDialogFragment");
    }

    private void setUpBilling(){
        billingProcessor=new BillingProcessor(this, BuildConfig.GoogleSpecAPIKEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                showSnackBar("Thank you so much :)");
            }

            @Override
            public void onPurchaseHistoryRestored() {

            }

            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) { }

            @Override
            public void onBillingInitialized() {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!billingProcessor.handleActivityResult(requestCode,resultCode,data)){
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        if(billingProcessor!=null){
            billingProcessor.release();
        }

        super.onDestroy();
    }
    public void showSnackBar(String message){
        Snackbar.make(findViewById(R.id.activity_about_app_parent_layout),message,Snackbar.LENGTH_SHORT).show();
    }
}
