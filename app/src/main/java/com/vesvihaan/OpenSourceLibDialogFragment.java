package com.vesvihaan;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class OpenSourceLibDialogFragment extends DialogFragment {
    RecyclerView recyclerView;
    ArrayList<String> libNames;
    ArrayList<String> libUrls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_liker_layout,container,false);
        libNames=new ArrayList(Arrays.asList(getContext().getResources().getStringArray(R.array.library_names)));
        libUrls=new ArrayList(Arrays.asList(getContext().getResources().getStringArray(R.array.library_url)));
        recyclerView=view.findViewById(R.id.likersList);
        recyclerView.setAdapter(new OpenSourceLibAdapter(getContext(),libNames,libUrls));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog()!=null){
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
