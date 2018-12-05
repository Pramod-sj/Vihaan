package com.vesvihaan.admin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EventFragment extends Fragment implements EventAdapter.OnEventItemClickListener {

    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    ArrayList<Event> events=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_event, container, false);
        recyclerView=view.findViewById(R.id.events);
        eventAdapter=new EventAdapter(getActivity().getApplicationContext(),events,this);
        recyclerView.setAdapter(eventAdapter);
        initDataSet();
        return view;
    }
DatabaseReference databaseReference;
    public void initDataSet(){
        databaseReference= FirebaseDatabase.getInstance().getReference("Events");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Event event=snapshot.getValue(Event.class);
                    event.setEventId(snapshot.getKey());
                    events.add(event);
                }
                eventAdapter.updateDataset(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(Event event) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("Event",event);
        RegistrationFragment registrationFragment=new RegistrationFragment();
        registrationFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout,registrationFragment).addToBackStack("RegistrationFragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }
}
