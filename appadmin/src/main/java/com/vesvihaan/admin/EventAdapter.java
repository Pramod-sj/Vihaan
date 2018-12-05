package com.vesvihaan.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    Context context;
    ArrayList<Event> events;

    OnEventItemClickListener onEventItemClickListener;
    public EventAdapter(Context context, ArrayList<Event> events,OnEventItemClickListener onEventItemClickListener) {
        this.context = context;
        this.events = events;
        this.onEventItemClickListener=onEventItemClickListener;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.event_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder viewHolder, int i) {
        viewHolder.eventName.setText(events.get(i).getEventName());
        if(events.get(i).getEventRegisteredUsers()==null){
            viewHolder.eventParticipantCount.setText("No Participants");
        }
        else{
            viewHolder.eventParticipantCount.setText("Total Participants: "+events.get(i).getEventRegisteredUsers().size());
        }
    }

    public void updateDataset(ArrayList<Event> events){
        this.events=events;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView eventName,eventParticipantCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName=itemView.findViewById(R.id.eventName);
            eventParticipantCount=itemView.findViewById(R.id.participantCount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEventItemClickListener.onClick(events.get(getLayoutPosition()));
        }
    }

    public interface OnEventItemClickListener{
        void onClick(Event event);
    }
}
