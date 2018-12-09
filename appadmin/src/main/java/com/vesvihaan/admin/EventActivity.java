package com.vesvihaan.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EventActivity extends AppCompatActivity implements EventAdapter.OnEventItemClickListener {

    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    ArrayList<Event> events=new ArrayList<>();
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.events);
        eventAdapter=new EventAdapter(getApplicationContext(),events,this);
        recyclerView.setAdapter(eventAdapter);
        initDataSet();
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
        Intent intent=new Intent(this,RegistrationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_logout){
            GoogleSinginHelper googleSinginHelper=new GoogleSinginHelper(this);
            googleSinginHelper.signOut();
        }
        else {
            Intent intent=new Intent(this,FeedActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
