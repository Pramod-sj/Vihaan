package com.vesvihaan.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.vesvihaan.R;

public class CustomInfoDialog extends DialogFragment{

    WebView webView;
    String url=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.privary_policy_dialog_layout,container,false);
        webView=(WebView) view.findViewById(R.id.privacyTermConditionWebView);
        webView.loadUrl(url);
        return view;
    }

    public void showPrivacyPolicy(){
        url="file:///android_asset/privacy_policy.html";
    }

    public void showTermCondition(){
        url="file:///android_asset/terms_and_conditions.html";
    }

    public void showAboutComputerScience(){
        url="file:///android_asset/about_computer_science.html";
    }
    @Override
    public void onStart() {
        super.onStart();
        if(getDialog()!=null){
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
