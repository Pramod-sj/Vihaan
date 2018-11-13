package com.vesvihaan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventFragment extends Fragment implements OnEventClickListener {
    RecyclerView eventRecyclerView;
    EventAdapter eventAdapter;
    ArrayList<Event> events=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_event,container,false);
        initEvents(view);
        return view;
    }

    private void initEvents(View view){
        eventRecyclerView=view.findViewById(R.id.eventsReyclerView);
        eventAdapter=new EventAdapter(getActivity(),events,this);
        eventRecyclerView.setAdapter(eventAdapter);

        FirebaseDatabase.getInstance().getReference(Constant.FIREBASE_EVENT_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Event event=snapshot.getValue(Event.class);
                    event.setEventId(snapshot.getKey());
                    events.add(event);
                }
                Log.i("SIZE", String.valueOf(events.size()));
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(Event e) {
        Intent intent=new Intent(getActivity(),EventDetailedActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("Event",e);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
