package com.example.eventbrite;

import java.util.Locale;
import com.actionbarsherlock.app.SherlockFragment;
import com.example.eventbrite.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyEvents extends SherlockFragment {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private FragmentActivity actionBar;
	

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		

		// Set up the ViewPager with the sections adapter.
		
			
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_main1, container, false);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		actionBar = getActivity();
		
		return rootView;
	}

	
	
	public void  OnCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
	{
	    menuInflater.inflate(R.menu.main, menu);
	    super.onCreateOptionsMenu(menu, menuInflater);
		
	}
	
	

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			
			Fragment fragment = new LiveEventFragment();
			switch (position) 
			{
				case 0:
					return fragment = new LiveEventFragment();
				case 1:
					return fragment = new DraftEventFragment();
				case 2:
					return fragment = new CompletedEventFragment();
				case 3:
					return fragment = new CancelEventFragment();
			
			}
			
		//	Bundle args = new Bundle();
		//	args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		//	fragment.setArguments(args);
			return fragment;
			
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				
				return getString(R.string.live_events).toUpperCase(l);
				
			case 1:
				
				return getString(R.string.draft_events).toUpperCase(l);
			case 2:
				
				return getString(R.string.completed_events).toUpperCase(l);
				
			case 3:
				
				return getString(R.string.cancel_events).toUpperCase(l);
		
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
/*public static class DummySectionFragment extends Fragment {
		
		 * The fragment argument representing the section number for this
		 * fragment.
		 
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.create_ticket, container, false);
		//	TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		//	dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}*/
	
	
	

}
