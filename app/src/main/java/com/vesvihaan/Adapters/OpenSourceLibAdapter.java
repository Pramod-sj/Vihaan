package com.vesvihaan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vesvihaan.R;

import java.util.ArrayList;

public class OpenSourceLibAdapter extends RecyclerView.Adapter<OpenSourceLibAdapter.ViewHolder>{
    Context context;
    ArrayList<String> libNames;

    public OpenSourceLibAdapter(Context context, ArrayList<String> libNames, ArrayList<String> libUrls) {
        this.context = context;
        this.libNames = libNames;
        this.libUrls = libUrls;
    }

    ArrayList<String> libUrls;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_lib_textview_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.libName.setText(libNames.get(i));
    }

    @Override
    public int getItemCount() {
        return libNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView libName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            libName=itemView.findViewById(R.id.libName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(libUrls.get(getLayoutPosition())));
            context.startActivity(intent);
        }
    }
}
