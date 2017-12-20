package com.vesvihaan;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.DachshundIndicator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class MainWindow extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    DachshundTabLayout tabLayout;
    DownloadManager downloadManager;
    FloatingActionButton fab11,fab22,fab33;
    FloatingActionMenu fab_menu;
    ViewPager viewPager;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    String newVersion = null;
    AlertDialog.Builder builder1,builder2,noconn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        fab11 = (FloatingActionButton)findViewById(R.id.fab1);
        fab22 = (FloatingActionButton)findViewById(R.id.fab2);
        fab33 = (FloatingActionButton)findViewById(R.id.fab3);
        fab_menu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        if(Build.VERSION.SDK_INT>=21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        //Setting permission so that there is no need to create another async class
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //end
        //setting permission SO THAT IT ALLOW TO INSTALL NEW UPDATE
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //END
        builder1 = new AlertDialog.Builder(this);
        builder2 = new AlertDialog.Builder(this);
        final Float curVer=Float.parseFloat(versionName);

        //final startpage_activity sa=new startpage_activity();
        noconn=new AlertDialog.Builder(this);
        noconn.setMessage("Need internet for checking update");
        noconn.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        noconn.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        fab11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(MainWindow.this,MainActivity.class);
                startActivity(i);
                fab_menu.close(true);
            }
        });
        fab22.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(MainWindow.this,aboutapp_Activity.class);
                startActivity(i);
                fab_menu.close(true);
            }
        });
        fab33.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                fab_menu.close(true);
                if(isConnected_custom()==true){
                    Toast.makeText(getApplicationContext(), "Checking for update....", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (curVer < checkVersion()){
                                //Uncomment the below code to Set the message and title from the strings.xml file
                                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                                //Setting message manually and performing action on button click
                                builder1.setMessage("Update "+newVersion+" is available to download")
                                        .setCancelable(false)
                                        .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        downloadapk();
                                                    }
                                                },500);

                                            }
                                        })
                                        .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                            public void onClick(final DialogInterface dialog, int id) {
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.cancel();
                                                    }
                                                },500);

                                            }
                                        });

                                //Creating dialog box
                                final AlertDialog alert1 = builder1.create();
                                //Setting the title manually
                                alert1.setTitle("New update is available!");

                                alert1.show();
                            } else {
                                builder2.setMessage("You are currently running latest version")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(final DialogInterface dialog, int id) {
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.cancel();
                                                    }
                                                },500);

                                            }
                                        });
                                final AlertDialog alert2 = builder2.create();
                                alert2.setTitle("No update");
                                alert2.show();
                            }
                        }
                    }, 2200);
                }
                else{

                    final AlertDialog alert = noconn.create();
                    alert.setTitle("No Internet");
                    alert.show();
                }

            }
        });
        //Initializing the tablayout
        tabLayout= (DachshundTabLayout) findViewById(R.id.tabLayout);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Schedule"));
        tabLayout.addTab(tabLayout.newTab().setText("Partners"));
        tabLayout.addTab(tabLayout.newTab().setText("About us"));

        DisplayMetrics dm=getResources().getDisplayMetrics();
        int densityDpi = (int)(dm.density * 160f);

        if(densityDpi>=320){
            tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        }
        else{
            tabLayout.setTabMode(tabLayout.MODE_FIXED);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        //Creating our pager adapter
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());


        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
        tabLayout.setScrollPosition(0,0,true);
        DachshundIndicator indicator=new DachshundIndicator(tabLayout);
        tabLayout.setAnimatedIndicator(indicator);
        //Adding onTabSelectedListener to swipe views


        tabLayout.setOnTabSelectedListener(MainWindow.this);

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        tabLayout.getTabAt(position).select();
                    }
                });

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    //Checking and Downloading updated apk
    public float checkVersion() {
        String temp;
        try {
            URL url = new URL("https://raw.githubusercontent.com/Pramod-sj/Vihaan/master/version.txt");
            URLConnection uc = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((temp = br.readLine()) != null) {
                newVersion=temp;
            }
        }
        catch (Exception e) {}
        //Toast.makeText(getApplicationContext(),newVersion,Toast.LENGTH_SHORT).show();
        Float newVer=Float.parseFloat(newVersion);
        return Float.parseFloat(newVersion);
    }

    public void downloadapk() {
        try {
            String url = "https://github.com/Pramod-sj/Vihaan/raw/master/Vihaan.apk";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle("Updating to VIHAAN " + newVersion);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Vihaan.apk");
            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"Please grant storage permission",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
        }
    }
    public boolean isConnected_custom(){
        boolean isInternetAvailable = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(startpage_activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && (networkInfo.isConnected())){
                isInternetAvailable  = true;
            }
        }
        catch(Exception exception) {}
        return isInternetAvailable;
    }

}

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     *
     * */
    class SectionsPagerAdapter extends FragmentPagerAdapter {
        int tabCount;

        public SectionsPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new event_activity();
                case 1:
                    return new schedules_activity();
                case 2:
                    return new sponsers_activity();
                case 3:
                    return new about_activity();

            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Events";
                case 1:
                    return "Schedule";
                case 2:
                    return "Partners";
                case 3:
                    return "About us";

            }
            return null;
        }
    }
