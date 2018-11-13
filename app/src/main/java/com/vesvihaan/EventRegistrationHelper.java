package com.vesvihaan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.paytm.pgsdk.PaytmPGService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class EventRegistrationHelper {

    Context context;
    Event event;

    public EventRegistrationHelper(Context context, Event event) {
        this.context = context;
        this.event = event;
    }

    interface OnRegistrationListener{
        void onSuccessfullyRegistered();
        void onFailure(String errorMessage);
    }
    public void registerForEvent(final OnRegistrationListener onRegistrationListener){
        Registration registration=new Registration();
        User user=new User();
        user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setUserName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        user.setUserPhotoUrl(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()));
        user.setUserEmailId(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        registration.setPaid(false);
        registration.setUser(user);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        registration.setDate(simpleDateFormat.format(new Date()));
        FirebaseDatabase.getInstance().getReference("Events").child(event.getEventId()).child("eventRegisteredUsers").child(user.getUid()).setValue(registration).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    onRegistrationListener.onSuccessfullyRegistered();
                }
                else {
                    onRegistrationListener.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    interface OnUnRegistrationListener{
        void onSuccessfullyUnRegistered();
        void onFailure(String errorMessage);
    }
    public void unRegisterEvent(String userId, final OnUnRegistrationListener onUnRegistrationListener){
        FirebaseDatabase.getInstance().getReference("Events").child(event.getEventId()).child("eventRegisteredUsers").child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    onUnRegistrationListener.onSuccessfullyUnRegistered();
                }
                else {
                    onUnRegistrationListener.onFailure(task.getException().getMessage());
                }
            }
        });
    }

    interface OnPaymentListener{
        void onSuccessFullyPaid();
        void onFailed(String errorMessage);
    }
    public void pay(OnPaymentListener onPaymentListener){
        PaytmPGService paytmPGService = PaytmPGService.getStagingService();
        HashMap<String, String> data = new HashMap<>();
        String orderID = FirebaseDatabase.getInstance().getReference().push().getKey();
        data.put("orderId", "order" + orderID);
        data.put("custId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        data.put("amount", String.valueOf(event.getEventEntryPrice()));
        data.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        ChecksumChecker checksumChecker;
        try {
            checksumChecker = new ChecksumChecker(context, "https://regdata.000webhostapp.com/Paytm/generateChecksum.php", MainActivity.getPostString(data), paytmPGService,onPaymentListener);
            checksumChecker.execute();
        } catch (Exception e) {
            Log.i("ERROR", e.getLocalizedMessage());
        }
    }

}
