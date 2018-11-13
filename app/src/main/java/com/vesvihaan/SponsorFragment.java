package com.vesvihaan;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SponsorFragment extends Fragment {

    RecyclerView sponsorCatRecyclerView;
    SponsorCategoryAdapter sponsorCategoryAdapter;
    ArrayList<Sponsors> sponsors=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sponsors,container,false);
        sponsorCatRecyclerView=view.findViewById(R.id.allCatSponsorRecyclerView);
        sponsorCategoryAdapter=new SponsorCategoryAdapter(getActivity().getApplicationContext(),sponsors);
        sponsorCatRecyclerView.setAdapter(sponsorCategoryAdapter);
        getSponsorData();
        return view;
    }


    public void getSponsorData(){

        Log.i("hello", "starthello");
        FirebaseDatabase.getInstance().getReference("Sponsors")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i("hello", "hello");
                        sponsors.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            Log.i("Data",dataSnapshot.getValue().toString());
                            Sponsors sponsor=new Sponsors();
                            sponsor.setSponsorCat(snapshot.getKey());
                            ArrayList<Sponsors.Sponsor> sponsorsData=new ArrayList<>();
                            for(DataSnapshot childSnap:snapshot.getChildren()){
                                sponsorsData.add(childSnap.getValue(Sponsors.Sponsor.class));
                            }
                            sponsor.setSponsor(sponsorsData);
                            sponsors.add(sponsor);
                        }
                        Log.i("Size", String.valueOf(sponsors.size()));
                        sponsorCategoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        Log.i("hello", "endhello");
    }


}