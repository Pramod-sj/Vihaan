package com.vesvihaan.UI.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vesvihaan.R;
import com.vesvihaan.Model.Sponsors;

import java.util.ArrayList;

public class SponsorFragment extends Fragment {

    RecyclerView sponsorCatRecyclerView;
    SponsorCategoryAdapter sponsorCategoryAdapter;
    ArrayList<Sponsors> sponsors=new ArrayList<>();
    TextView noSponsor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sponsors,container,false);
        sponsorCatRecyclerView=view.findViewById(R.id.allCatSponsorRecyclerView);
        sponsorCategoryAdapter=new SponsorCategoryAdapter(getActivity().getApplicationContext(),sponsors);
        sponsorCatRecyclerView.setAdapter(sponsorCategoryAdapter);
        noSponsor=view.findViewById(R.id.noSponsorTextView);
        getSponsorData();
        return view;
    }


    public void getSponsorData(){

        FirebaseDatabase.getInstance().getReference("Sponsors")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sponsors.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            Sponsors sponsor=new Sponsors();
                            sponsor.setSponsorCat(snapshot.getKey());
                            ArrayList<Sponsors.Sponsor> sponsorsData=new ArrayList<>();
                            for(DataSnapshot childSnap:snapshot.getChildren()){
                                sponsorsData.add(childSnap.getValue(Sponsors.Sponsor.class));
                            }
                            sponsor.setSponsor(sponsorsData);
                            sponsors.add(sponsor);
                        }
                        if(sponsors.size()==0){
                            sponsorCatRecyclerView.setVisibility(View.GONE);
                            noSponsor.setVisibility(View.VISIBLE);
                        }
                        else{
                            sponsorCatRecyclerView.setVisibility(View.VISIBLE);
                            noSponsor.setVisibility(View.GONE);
                            sponsorCategoryAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


}