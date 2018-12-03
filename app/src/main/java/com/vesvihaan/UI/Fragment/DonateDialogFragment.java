package com.vesvihaan.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.vesvihaan.R;

public class DonateDialogFragment extends DialogFragment implements View.OnClickListener {
    RelativeLayout cookie,coffie,gift;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_donate_layout,container,false);
        cookie=view.findViewById(R.id.cookies);
        coffie=view.findViewById(R.id.coffie);
        gift=view.findViewById(R.id.gift);
        cookie.setOnClickListener(this);
        coffie.setOnClickListener(this);
        gift.setOnClickListener(this);
        return view;
    }

    OnDonateItemClickListener onDonateItemClickListener;
    public void setOnDonateItemClickListener(OnDonateItemClickListener onDonateItemClickListener){
        this.onDonateItemClickListener=onDonateItemClickListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog()!=null){
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cookies:
                dismiss();
                onDonateItemClickListener.onItemClick("COOKIE");
                break;
            case R.id.coffie:
                dismiss();
                onDonateItemClickListener.onItemClick("COFFIE");
                break;
            case R.id.gift:
                dismiss();
                onDonateItemClickListener.onItemClick("GIFT");
                break;
        }
    }

    public static interface OnDonateItemClickListener{
        void onItemClick(String itemName);
    }


}
