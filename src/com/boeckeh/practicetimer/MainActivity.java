package com.boeckeh.practicetimer;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    SeekBar mSeekBar;
    
    
    public enum cType {c_UP, c_DN, c_RS};
    public cType mType = cType.c_UP;
	public int i_MaxLevel;
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set this to false so the home icon doesn't go backwards to nothing (well, nothing in this app)
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.hide();
        
        // getActionBar().setDisplayHomeAsUpEnabled(false);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.  [EB] we're hiding the action bar, so forget this
                        // getActionBar().setSelectedNavigationItem(position);

                    	RadioButton rbCU = (RadioButton) findViewById(R.id.rb_count_up);
                    	RadioButton rbCD = (RadioButton) findViewById(R.id.rb_count_down);
                    	RadioButton rbCUS = (RadioButton) findViewById(R.id.rb_reset_up);
                    	
                    	SeekBar setBar = (SeekBar) findViewById(R.id.sb_time2run);
                    	TextView toBeSet = (TextView) findViewById(R.id.tv_Timing);
                    	try {
                    		int theSetfromtheBar = setBar.getProgress(); 
                    		int hours = theSetfromtheBar / 60;
                    		int mins = theSetfromtheBar % 60;
                    		
                    		if (rbCD.isChecked())
                			{	
                				toBeSet.setText(String.format("%02d:%02d:00", hours, mins));
                				mType = cType.c_DN;
                			}
                    		if (rbCU.isChecked())
                    		{
                    			toBeSet.setText(String.format("00:00:00"));
                				mType = cType.c_UP;
                    		}
                    		if (rbCUS.isChecked())
                    		{
                    			toBeSet.setText(String.format("00:00:00"));
                				mType = cType.c_RS;
                    		}

                    	} catch (Exception e)
                    		// do nothing if there is an error
                    	{
                    		
                    	} finally
                    	{
                    		// again, nothing
                    	}
                    	
                    }
                });
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
        	Fragment frag = null;
        	switch (i)
        	{
		        case 0: frag = new Frag_GetTimerSettings(); break;
		        case 1: frag = new Frag_GetAudioSettings(); break;
		        case 2: frag = new Frag_GoTimer(); break;
        	}
        	return frag;
        }
        
         @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getString(R.string.title_section1).toUpperCase();
                case 1: return getString(R.string.title_section2).toUpperCase();
                case 2: return getString(R.string.title_section3).toUpperCase();
            }
            return null;
        }
        
    }

 }
