package com.vesvihaan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LikerAdapter extends RecyclerView.Adapter<LikerAdapter.ViewHolder> {
    Context context;
    ArrayList<User> users;

    public LikerAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.liker_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(users.get(i).getUserName());
        GlideApp.with(context).load(users.get(i).getUserPhotoUrl()).placeholder(R.drawable.user_profile_drawable).into(viewHolder.profile);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.dialoglikerProfile);
            name=itemView.findViewById(R.id.dialoglikerName);

        }
    }
}
