package com.vesvihaan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class SponsorCategoryAdapter extends RecyclerView.Adapter<SponsorCategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<Sponsors> sponsors;
    RecyclerView.RecycledViewPool viewPool;
    public SponsorCategoryAdapter(Context context,ArrayList<Sponsors> sponsors) {
        this.context = context;
        this.sponsors = sponsors;
        viewPool=new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.sponsor_cat_item_layout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.recyclerView.setRecycledViewPool(viewPool);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.catTextView.setText(sponsors.get(i).getSponsorCat());
        viewHolder.recyclerView.setAdapter(new SponsorAdapter(context,sponsors.get(i).getSponsor()));
        viewHolder.recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return sponsors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catTextView;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catTextView=itemView.findViewById(R.id.sponCategoryTextView);
            recyclerView=itemView.findViewById(R.id.sponsorRecyclerView);
        }
    }

}
