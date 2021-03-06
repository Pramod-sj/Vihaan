package com.vesvihaan;
/**
 * Created by pramo on 10/4/2017.
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class event_activity extends Fragment {
    View view;
    ViewGroup root;
    ListView listView;
    String []events=new String[]{"Code Trove","Quiriosity","Techtonic","Posteria","Megablog","Freeze The Moment","App ki Peshkash","IOTICS","Introview"};
    String []desc=new String[]
            {"'Everyone should know how to code because it teaches you how to think' \b-Steve Jobs"
                    ,"Don't fight your problem,know that there is a solution"
                    ,"The purpose of quiz is that to realize everyone is on same page"
                    ,"Do you think you have sound imagination?"
                    ,"A blog is not only a platform to express your thoughts,but also a platform to touch,motivate and inspire"
                    , "Photography takes an instant out of time, altering life by holding still"
                    ,"Imagination is the beginning of the creation"
                    ,"Embracing the future with Internet Of Things"
                    ,"Interaction with Experienced Personalities"
            };
    int []imag_id=new int[]{R.drawable.codetrove__red,R.drawable.quizeria__red,R.drawable.techtonic__red,R.drawable.posteria,R.drawable.megablog__red,R.drawable.freezethemoment__red,R.drawable.appkipeshkash__red,R.drawable.iotics2__red,R.drawable.intro_red};
    AlertDialog.Builder detail;
    AlertDialog alert1;
    String []data;
    PopupWindow popup;
    String rules[],teams[];
    View customView;
    TextView event_txt1,data_txt2,rule_txt,team_txt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.event, container, false);
        root = (ViewGroup) getActivity().getWindow().getDecorView().getRootView();
        listView=(ListView) view.findViewById(R.id.listview);
        data=getResources().getStringArray(R.array.data);
        teams=getResources().getStringArray(R.array.team);
        rules=getResources().getStringArray(R.array.rules);
        detail=new AlertDialog.Builder(getContext());
        CustomView c=new CustomView(getActivity(),events,desc,imag_id);
        listView.setAdapter(c);
        listView.setSmoothScrollbarEnabled(true);
        final int i=0;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater layoutInflater=(LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                customView=layoutInflater.inflate(R.layout.custompopup,null);
                event_txt1=(TextView) customView.findViewById(R.id.eventname);
                data_txt2=(TextView) customView.findViewById(R.id.eventdesc);
                rule_txt=(TextView) customView.findViewById(R.id.rule);
                team_txt=(TextView) customView.findViewById(R.id.team);
                event_txt1.setText(events[i]);
                data_txt2.setText(data[i]);
                rule_txt.setText(rules[i]);
                team_txt.setText(teams[i]);
                WindowManager wm=(WindowManager)getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                Display display=wm.getDefaultDisplay();
                DisplayMetrics dm=new DisplayMetrics();
                display.getMetrics(dm);
                int width=dm.widthPixels;
                if(width<=720) {
                    popup = new PopupWindow(customView, 650, 850, true){
                        @Override
                        public void dismiss() {
                            super.dismiss();
                            clearDim(root);
                        }
                    }; // Creation of popup
                }
                else{
                    popup = new PopupWindow(customView, 950, 1150, true){
                        @Override
                        public void dismiss() {
                            super.dismiss();
                            clearDim(root);
                        }
                    };
                }
                popup.setAnimationStyle(android.R.style.Animation_Dialog);
                popup.showAtLocation(customView, Gravity.CENTER, 0, 0);
                applyDim(root, (float) 0.6);

            }

        });
        return view;
    }

    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }
}
