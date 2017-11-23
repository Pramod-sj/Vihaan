package registrationform.com.registrationform;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class MainWindow extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    //This is our tablayout
    private TabLayout tabLayout;
    //This is our viewPager

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_window, menu);
        return super.onCreateOptionsMenu(menu);
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
                    return new home_activity();
                case 2:
                    return new about_activity();
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Events";
                case 1:
                    return "Home";
                case 2:
                    return "About us";
            }
            return null;
        }
    }
