package com.vesvihaan.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

public class RegistrationFragment extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    RegistrationAdapter registrationAdapter;
    ArrayList<Registration> registrations=new ArrayList<>();
    Event event;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registration, container, false);
        event= (Event) getArguments().getSerializable("Event");
        initRecyclerViewAndAdapter(view);
        return view;
    }


    public void initRecyclerViewAndAdapter(View view){
        recyclerView=view.findViewById(R.id.registereduser);
        registrationAdapter=new RegistrationAdapter(getActivity().getApplicationContext(),registrations);
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
