package com.vesvihaan.admin;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.ViewHolder> {
    Context context;
    ArrayList<Registration> registrations;
    public RegistrationAdapter(Context context, ArrayList<Registration> registrations) {
        this.context = context;
        this.registrations = registrations;
    }


    @NonNull
    @Override
    public RegistrationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.registrated_user_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistrationAdapter.ViewHolder viewHolder, int i) {
        viewHolder.regUserName.setText(registrations.get(i).getUser().getUserName());
        viewHolder.regUserEmail.setText(registrations.get(i).getUser().getUserEmailId());
        viewHolder.regEventName.setText("TEST");
        if(registrations.get(i).isPaid()) {
            viewHolder.regStatus.setText("Paid");
            viewHolder.regStatus.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
        }
        else{
            viewHolder.regStatus.setText("Not paid");
            viewHolder.regStatus.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
        }
    }

    public void updateDataSet(ArrayList<Registration> registrations){
        this.registrations=registrations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return registrations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView regUserName,regUserEmail,regEventName,regStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            regEventName=itemView.findViewById(R.id.eventName);
            regStatus=itemView.findViewById(R.id.regStatusText);
            regUserName=itemView.findViewById(R.id.regName);
            regUserEmail=itemView.findViewById(R.id.regEmail);


        }
    }
}
