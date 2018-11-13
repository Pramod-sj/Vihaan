package com.vesvihaan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RoundedBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setButton1_text(String button1_text) {
        this.button1_text = button1_text;
    }

    public void setButton2_text(String button2_text) {
        this.button2_text = button2_text;
    }


    String title,desc,button1_text,button2_text;
    OnButtonClickListener onButtonClickListener;
    public void setButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.onButtonClickListener=onButtonClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetStyle);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view=inflater.inflate(R.layout.bottom_sheet_layout,container,false);
        Button button1,button2;
        button1=view.findViewById(R.id.bottom_sheet_button1);
        button1.setText(button1_text);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClickListener.onLeftButtonClick(RoundedBottomSheetDialogFragment.this);
            }
        });
        button2=view.findViewById(R.id.bottom_sheet_button2);

        button2.setText(button2_text);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClickListener.onRightButtonClick(RoundedBottomSheetDialogFragment.this);
            }
        });

        TextView title_txtView,desc_txtView;
        title_txtView=view.findViewById(R.id.bottom_sheet_title);
        desc_txtView=view.findViewById(R.id.bottom_sheet_desc);
        title_txtView.setText(title);
        desc_txtView.setText(desc);
        if(desc!=null && desc.isEmpty()){
            desc_txtView.setVisibility(View.GONE);
        }
        if (savedInstanceState != null) {
            title_txtView.setText(savedInstanceState.getString("title"));
            desc_txtView.setText(savedInstanceState.getString("desc"));
            button1.setText(savedInstanceState.getString("button1text"));
            button2.setText(savedInstanceState.getString("button2text"));
        }
        return view;
    }

    public interface OnButtonClickListener{
        void onRightButtonClick(RoundedBottomSheetDialogFragment dialogFragment);
        void onLeftButtonClick(RoundedBottomSheetDialogFragment dialogFragment);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title",title);
        outState.putString("desc",desc);
        outState.putString("button1text",button1_text);
        outState.putString("button2text",button2_text);
    }
}
