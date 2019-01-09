package com.vesvihaan.UI.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vesvihaan.Helper.ConnectionUtil;
import com.vesvihaan.Helper.DataSetConvertor;
import com.vesvihaan.Model.Event;
import com.vesvihaan.Helper.EventRegistrationHelper;
import com.vesvihaan.GlideApp;
import com.vesvihaan.Helper.GoogleSinginHelper;
import com.vesvihaan.Helper.OnSigninListener;
import com.vesvihaan.R;
import com.vesvihaan.Model.Registration;
import com.vesvihaan.UI.Fragment.RoundedBottomSheetDialogFragment;

import java.util.ArrayList;

public class EventDetailedActivity extends AppCompatActivity {
    Event event;
    TextView eventDescTextView,eventRuleTextView,eventDayTextView,eventTimeTextView;
    Button register,registerandpay,unregister,pay;
    EventRegistrationHelper eventRegistrationHelper;
    CoordinatorLayout coordinatorLayout;
    ProgressDialog progressDialog;
    private void initDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detailed);
        initDialog();
        coordinatorLayout=findViewById(R.id.eventdetailcoordinatelayout);
        event = (Event) getIntent().getSerializableExtra("Event");
        register=findViewById(R.id.registerButton);
        registerandpay=findViewById(R.id.registerAndPayButton);
        pay=findViewById(R.id.payButton);
        unregister=findViewById(R.id.unregisterButton);
        checkRegistrationAndPaidStatus();
        eventRegistrationHelper=new EventRegistrationHelper(this,event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(event.getEventName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GlideApp.with(this).load(event.getEventImageUrl()).into((ImageView) findViewById(R.id.imageview));
        eventDescTextView = findViewById(R.id.eventDescTextView);
        eventDescTextView.setText(event.getEventDesc());
        eventRuleTextView=findViewById(R.id.rulesTextView);
        if (event.getEventRules() == null) {
            eventRuleTextView.setText("N/A");
        } else {
            eventRuleTextView.setText(Html.fromHtml(String.valueOf(DataSetConvertor.makeHtmlList(event.getEventRules())), null, null));
        }
        eventDayTextView=findViewById(R.id.dayTextView);
        if(event.getEventDay()==null){
            eventDayTextView.setText("Day: N/A");
        }
        else{
            eventDayTextView.setText("Day: "+event.getEventDay());
        }

        eventTimeTextView=findViewById(R.id.eventTimeTextView);
        if(event.getEventTime()==null){
            eventTimeTextView.setText("Time: N/A");
        }
        else{
            eventTimeTextView.setText("Time: "+event.getEventTime());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showBottomSheetForPaymentConfirmation(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait....");
        progressDialog.setMessage("We redirecting you to paytm gateway");
        RoundedBottomSheetDialogFragment sheetDialogFragment=new RoundedBottomSheetDialogFragment();
        sheetDialogFragment.setButton1_text("No");
        sheetDialogFragment.setButton2_text("Yes");
        sheetDialogFragment.setTitle("Read carefully!");
        sheetDialogFragment.setDesc("Note: Payment is done using Paytm");
        sheetDialogFragment.setButtonClickListener(new RoundedBottomSheetDialogFragment.OnButtonClickListener() {
            @Override
            public void onRightButtonClick(final RoundedBottomSheetDialogFragment dialogFragment) {
                progressDialog.show();
                eventRegistrationHelper.pay(new EventRegistrationHelper.OnPaymentListener() {
                    @Override
                    public void onSuccessFullyPaid() {
                        FirebaseDatabase.getInstance().getReference("Events").child(event.getEventId()).child("eventRegisteredUsers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("paid").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.hide();
                                if(task.isSuccessful()){
                                    checkRegistrationAndPaidStatus();
                                    showSnackBar("Successfully payment done!").show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Snackbar.make(coordinatorLayout,errorMessage,Snackbar.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                dialogFragment.dismiss();
            }

            @Override
            public void onLeftButtonClick(RoundedBottomSheetDialogFragment dialogFragment) {
                dialogFragment.dismiss();
            }
        });
        sheetDialogFragment.show(getSupportFragmentManager(),"PaymentConfirmDialog");
    }

    public void onRegisterAndPayClick(View view){
        if (ConnectionUtil.isConnectedToInternet(this)) {
            if (isValidToRegisterOrPay()) {
                progressDialog.show();
                eventRegistrationHelper.registerForEvent(new EventRegistrationHelper.OnRegistrationListener() {
                    @Override
                    public void onSuccessfullyRegistered() {
                        eventRegistrationHelper.registerForEvent(new EventRegistrationHelper.OnRegistrationListener() {
                            @Override
                            public void onSuccessfullyRegistered() {
                                showBottomSheetForPaymentConfirmation();
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                progressDialog.dismiss();
                                Snackbar.make(coordinatorLayout, errorMessage, Snackbar.LENGTH_SHORT).show();

                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
            }
        }
        else {
            showSnackBar("Please connect to internet").show();
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



    GoogleSinginHelper googleSinginHelper;
    public boolean isValidToRegisterOrPay(){
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            showSnackBar("Please login first").setAction("Login", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleSinginHelper=new GoogleSinginHelper(EventDetailedActivity.this, new OnSigninListener() {
                        @Override
                        public void onSuccess() {
                            recreate();
                        }

                        @Override
                        public void onFailure(String errorMessage) {

                        }
                    });
                    if(googleSinginHelper.isGooglePlayServiceAvailable()){
                        googleSinginHelper.buildGoogleSigninOption();
                        Intent intent=googleSinginHelper.getGoogleSignInClient().getSignInIntent();
                        startActivityForResult(intent,GoogleSinginHelper.GOOGLE_SIGIN_CODE);
                    }
                    else {
                        showSnackBar("Please update your google play sevice").show();
                    }
                }
            }).show();
            return false;
        }
        if(event.getEventEntryPrice()==0.0f){

            showSnackBar("Fee is not available please contact developer").show();
            return false;
        }
        return true;
    }

    boolean isRegistered=false;
    boolean isPaid=false;
    public void checkRegistrationAndPaidStatus(){
        FirebaseDatabase.getInstance().getReference("Events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Event eventData = dataSnapshot.child(event.getEventId()).getValue(Event.class);
                    if (eventData.getEventRegisteredUsers() != null) {
                        ArrayList<Registration> regUsers = new ArrayList<Registration>(eventData.getEventRegisteredUsers().values());
                        boolean userFound=false;
                        for (Registration registration : regUsers) {
                            if (FirebaseAuth.getInstance().getCurrentUser()!=null && registration.getUser().getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                Log.i("Datachanged","found user");
                                isRegistered = true;
                                if (registration.isPaid()) {
                                    isPaid = true;

                                }
                                userFound=true;
                            }
                        }
                        if(userFound==false){
                            isPaid=false;
                            isRegistered=false;
                        }
                    }
                    else{
                        isPaid=false;
                        isRegistered=false;

                    }

                    if (isRegistered) {
                        registerandpay.setVisibility(View.GONE);
                        register.setVisibility(View.GONE);
                        unregister.setVisibility(View.VISIBLE);
                        if (isPaid) {
                            pay.setVisibility(View.GONE);
                            unregister.setVisibility(View.GONE);
                            findViewById(R.id.paidMessageButton).setVisibility(View.VISIBLE);
                        } else {
                            pay.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.i("Visible","regandpay");
                        registerandpay.setVisibility(View.VISIBLE);
                        register.setVisibility(View.VISIBLE);
                        unregister.setVisibility(View.GONE);
                        pay.setVisibility(View.GONE);
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onPayClick(View view){
        if(ConnectionUtil.isConnectedToInternet(this)) {
            if (isValidToRegisterOrPay()) {
                showBottomSheetForPaymentConfirmation();
            }
        }
        else {
            showSnackBar("Please connect to internet").show();
        }
    }

    public void onUnRegisterClick(View view){
        progressDialog.show();
        if(ConnectionUtil.isConnectedToInternet(this)) {
            if (isValidToRegisterOrPay()) {
                eventRegistrationHelper.unRegisterEvent(FirebaseAuth.getInstance().getCurrentUser().getUid(), new EventRegistrationHelper.OnUnRegistrationListener() {
                    @Override
                    public void onSuccessfullyUnRegistered() {
                        checkRegistrationAndPaidStatus();
                        showSnackBar("We have un registered you").show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        showSnackBar(errorMessage).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
        else {
            showSnackBar("Please connect to internet").show();
        }
    }

    public void onRegisterClick(View view){
        if(ConnectionUtil.isConnectedToInternet(this)) {
            if (isValidToRegisterOrPay()) {
                progressDialog.show();
                eventRegistrationHelper.registerForEvent(new EventRegistrationHelper.OnRegistrationListener() {
                    @Override
                    public void onSuccessfullyRegistered() {
                        checkRegistrationAndPaidStatus();
                        showSnackBar("Successfully registered! pay now or on spot").show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        showSnackBar(errorMessage).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
        else {
            showSnackBar("Please connect to internet").show();
        }
    }


    public Snackbar showSnackBar(String message){
        Snackbar snackbar=Snackbar.make(findViewById(R.id.eventdetailcoordinatelayout),message,Snackbar.LENGTH_SHORT);
        CoordinatorLayout.LayoutParams params= (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.setAnchorId(R.id.buttonLayout);
        params.anchorGravity= Gravity.TOP;
        params.gravity=Gravity.TOP;
        snackbar.getView().setLayoutParams(params);
        return snackbar;
    }

}
