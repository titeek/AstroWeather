package com.example.astro;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;


public class AstroActivity extends FragmentActivity {



    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem sunTab, moonTab;
    private PageAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_activity);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        sunTab = (TabItem) findViewById(R.id.sunTab);
        moonTab = (TabItem) findViewById(R.id.moonTab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //getResources().getConfiguration().orientation;
        //Configuration.ORIENTATION_LANDSCAPE;

        //if(getResources().getConfiguration().screenLayout != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
        //if(viewPager != null)
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) { //na tablecie jak nie ma tego to nie działa, a na tel jak sie obróci kilka razy to sie buguje i wywala
            pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(pagerAdapter);

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());

                    if(tab.getPosition() == 0) {
                        pagerAdapter.notifyDataSetChanged();
                    } else if(tab.getPosition() == 1) {
                        pagerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }

    }


}
