package com.vesvihaan.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

public class RegistrationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RegistrationAdapter registrationAdapter;
    ArrayList<Registration> registrations=new ArrayList<>();
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);
        event= (Event) getIntent().getExtras().getSerializable("Event");
        initRecyclerViewAndAdapter();
    }

    Event event;



    public void initRecyclerViewAndAdapter(){
        recyclerView=findViewById(R.id.registereduser);
        registrationAdapter=new RegistrationAdapter(getApplicationContext(),registrations);
        recyclerView.setAdapter(registrationAdapter);
        registrationAdapter.updateDataSet(getRes());
    }


    public ArrayList<Registration> getRes(){
        ArrayList<Registration> registrations=new ArrayList<>();
        if(event.getEventRegisteredUsers()!=null){
            for (Registration registration:event.getEventRegisteredUsers().values()){
                registrations.add(registration);
            }
        }
        return  registrations;
    }


}
