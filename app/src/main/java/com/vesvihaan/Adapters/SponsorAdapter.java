package com.vesvihaan.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vesvihaan.GlideApp;
import com.vesvihaan.Model.Sponsors;
import com.vesvihaan.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.ViewHolder> {
        Context context;
        ArrayList<Sponsors.Sponsor> sponsor;
        RecyclerView.RecycledViewPool viewPool;
        public SponsorAdapter(Context context,ArrayList<Sponsors.Sponsor> sponsor) {
            this.context = context;
            this.sponsor = sponsor;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sponsor_item_layout,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.sponsorNameTextView.setText(sponsor.get(i).getSponsorName());
            GlideApp.with(context).load(sponsor.get(i).getSponsorLogoUrl()).into(viewHolder.logoImageView);
        }
        @Override
        public int getItemCount() {
                return sponsor.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView sponsorNameTextView;
            CircleImageView logoImageView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                sponsorNameTextView=itemView.findViewById(R.id.sponsorName);
                logoImageView=itemView.findViewById(R.id.sponsorImage);
        }
}

}
