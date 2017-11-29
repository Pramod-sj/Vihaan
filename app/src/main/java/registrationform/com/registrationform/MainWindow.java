package registrationform.com.registrationform;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainWindow extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    TabLayout tabLayout;
    DownloadManager downloadManager;
    FloatingActionButton fab11,fab22,fab33;
    FloatingActionMenu fab_menu;
    ViewPager viewPager;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    String newVersion = "1.2";
    AlertDialog.Builder builder1,builder2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        fab11 = (FloatingActionButton)findViewById(R.id.fab1);
        fab22 = (FloatingActionButton)findViewById(R.id.fab2);
        fab33 = (FloatingActionButton)findViewById(R.id.fab3);
        fab_menu = (FloatingActionMenu) findViewById(R.id.fab_menu);

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
        //Creating dialog box
        final AlertDialog alert1 = builder1.create();
        final AlertDialog alert2 = builder2.create();
        //Setting the title manually
        alert1.setTitle("New update is available!");
        alert2.setTitle("No update");
        final Float curVer=Float.parseFloat(versionName);
        final Float newVer=Float.parseFloat(newVersion);
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
                Toast.makeText(getApplicationContext(),"Checking for update....",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        if(curVer<newVer) {
                            alert1.show();
                        }
                        else {
                            alert2.show();
                        }
                    }
                },2200);

            }
        });

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Schedule"));
        tabLayout.addTab(tabLayout.newTab().setText("Partners"));
        tabLayout.addTab(tabLayout.newTab().setText("About us"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());


        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(1);
        tabLayout.setScrollPosition(1,0,true);

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

    public void downloadapk() {
            String url = "http://www.appsapk.com/downloading/latest/MP3%20Cutter%20and%20Ringtone%20Maker-2.0.apk";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle("Updating to VIHAAN "+newVersion);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filename.apk");
            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
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
