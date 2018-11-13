package com.vesvihaan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;

public class LikerDialogFragment extends DialogFragment {
    RecyclerView recyclerView;
    LikerAdapter adapter;
    ArrayList<User> users;

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_liker_layout,container,false);
        recyclerView=view.findViewById(R.id.likersList);
        adapter=new LikerAdapter(getActivity(),users);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
