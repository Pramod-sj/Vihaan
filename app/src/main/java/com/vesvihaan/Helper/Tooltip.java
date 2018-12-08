package com.vesvihaan.Helper;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vesvihaan.R;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Tooltip {
    Context context;
    PopupWindow popupWindow;
    LayoutInflater layoutInflater;
    View contentView;
    public Tooltip(Context context) {
        this.context = context;
        popupWindow=new PopupWindow(context);
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView=layoutInflater.inflate(R.layout.tooltip_layout,null);
    }

    public void showTooltip(View anchor,String message){
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setElevation(20);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(contentView);
        int screen_pos[]=new int[2];
        anchor.getLocationOnScreen(screen_pos);
        Rect anchor_rect=new Rect(screen_pos[0],screen_pos[1],screen_pos[0]+anchor.getWidth(),screen_pos[1]+anchor.getHeight());
        contentView.measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        int contentViewWidth=contentView.getWidth();
        int contentViewHeight=contentView.getHeight();
        int x=anchor_rect.centerX()-(contentViewWidth/2);
        int y=anchor_rect.centerY()-(contentViewHeight/2);
        TextView messageView=contentView.findViewById(R.id.tooltipText);
        messageView.setText(message);
        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY,x,y);
    }

    public void dismissTooltip(){
        if(popupWindow!=null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }



}
