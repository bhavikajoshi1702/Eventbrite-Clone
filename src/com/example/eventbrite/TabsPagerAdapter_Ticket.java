package com.example.eventbrite;

import com.example.eventbrite.PaidFragment;
import com.example.eventbrite.DonaionFragment;
import com.example.eventbrite.FreeFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter_Ticket extends FragmentPagerAdapter {

	public TabsPagerAdapter_Ticket(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new FreeFragment();
		case 1:
			// Games fragment activity
			return new PaidFragment();
		case 2:
			// Movies fragment activity
			return new DonaionFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
