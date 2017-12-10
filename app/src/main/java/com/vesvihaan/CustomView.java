package com.vesvihaan;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pramod on 8/12/17.
 */

public class CustomView extends BaseAdapter{
    String []events;
    String []desc;
    int []imag_id;
    Activity context;
    public CustomView(Activity context, String []events, String []desc, int []imag_id){
        //super(context,R.layout.activity_listview,country);
        this.events=events;
        this.desc=desc;
        this.context=context;
        this.imag_id=imag_id;
    }

    @Override
    public int getCount() {
        return events.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.activity_listview, parent, false);
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.text);
            viewHolder.textViewdesc = (TextView) convertView.findViewById(R.id.des);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);

            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.textViewName.setText(events[position]);
        viewHolder.textViewdesc.setText(desc[position]);
        viewHolder.imageView.setImageResource(imag_id[position]);
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewdesc;
    }
}
