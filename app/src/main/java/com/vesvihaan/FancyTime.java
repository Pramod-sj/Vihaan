package com.vesvihaan;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FancyTime {
    public static String getTimeLine(String date){
        //Log.i("Date",date);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Long time=null;
        try {
            time=simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long now=Calendar.getInstance().getTimeInMillis();
        String ago="";
        float diffInHours=((now-time)/(1000*60*60))%24;
        //Log.i("SPAN",String.valueOf(diffInHours));
        if(diffInHours<1){
            ago= DateUtils.getRelativeTimeSpanString(time,now,DateUtils.MINUTE_IN_MILLIS).toString();
        }
        else if(diffInHours>=1 && diffInHours<24){
            ago= DateUtils.getRelativeTimeSpanString(time,now,DateUtils.HOUR_IN_MILLIS).toString();
        }
        else {
            ago= DateUtils.getRelativeTimeSpanString(time,now,DateUtils.DAY_IN_MILLIS).toString();

        }
        return ago;
    }
}
