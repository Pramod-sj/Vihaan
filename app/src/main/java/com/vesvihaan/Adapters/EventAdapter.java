package com.vesvihaan.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.vesvihaan.Model.Event;
import com.vesvihaan.Helper.OnEventClickListener;
import com.vesvihaan.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    ArrayList<Event> events;
    Context context;
    OnEventClickListener onEventClickListener;

    public EventAdapter(Context context,ArrayList<Event> events,OnEventClickListener onEventClickListener) {
        this.events = events;
        this.context = context;
        this.onEventClickListener=onEventClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.event_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.eventTitle.setText(events.get(i).getEventName());
        viewHolder.eventQuote.setText(events.get(i).getEventQuote());
        viewHolder.eventType.setText(events.get(i).getEventType());
        Glide.with(context).load(events.get(i).getEventImageUrl()).transition(DrawableTransitionOptions.withCrossFade(300)).into(viewHolder.eventImage);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView eventImage;
        TextView eventTitle,eventQuote,eventType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage=itemView.findViewById(R.id.imageview);
            eventTitle=itemView.findViewById(R.id.eventTitle);
            eventQuote=itemView.findViewById(R.id.eventDesc);
            eventType=itemView.findViewById(R.id.eventTypeText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEventClickListener.onClick(events.get(getLayoutPosition()));
        }
    }

}
