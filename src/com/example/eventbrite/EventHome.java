package com.example.eventbrite;

import java.util.ArrayList;
import java.util.Arrays;


import org.json.JSONArray;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import com.actionbarsherlock.view.MenuItem;




import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventHome extends SherlockFragmentActivity{
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	public String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	public ListView mDrawerList;
	public ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	int selected;
	
	JSONObject jsobj;
	JSONArray events = null;
	
	boolean asyncrun;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evenhome);
		
		mTitle = mDrawerTitle = getTitle();
		ArrayList<String> lst = new ArrayList<String>();
		
		if (Integer.parseInt(Constants.id) > 0) {
			//mPlanetTitles = new String[] { "Home", "My Events", "Login","Logout", "My Posted Task" };
			mPlanetTitles = new String[] { "Home", "My Events","My Profile","Create an Event","Organizer Profile","Change Password","Logout" };

			
			lst.addAll(Arrays.asList(mPlanetTitles));
		} else {
			mPlanetTitles = new String[] { "Home", "Login", "SignUp" };
			lst.addAll(Arrays.asList(mPlanetTitles));
		}
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		adapter = new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, lst);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		// ActionBarDrawerToggle ties together the the proper interactions
				// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.abs__action_bar_home_description, R.string.abs__action_bar_up_description )
		{					
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu(); 
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu(); 
			}
					
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}
	
	
	 
	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if(mDrawerToggle.onOptionsItemSelected(getMenuItem(item))) {
				Log.e("drawer", "first");
				return true;
			}
			if (item.getItemId() == android.R.id.home || item.getItemId() == 0) {
				Log.e("drawer", "second");
				return false;
			}
			if(selected == 4)
			{
				Log.e("drawer", "MyProjectfilter");
				Intent intent = new Intent(getBaseContext(), Dashboard.class);
				startActivity(intent);
						
			}
			else if(selected == 1){
				Log.e("drawer", "ChangeCity");
				Intent intent = new Intent(getBaseContext(), EventDetails.class);
				startActivity(intent);
			}else{
				Log.e("drawer", "Homefilter");
				Intent intent = new Intent(getBaseContext(), Login1.class);
				startActivity(intent);
			}
			return true;
		}
	
	 
	
	 
	/*	 @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if(selected == 2 /*|| selected == 4 )
		{
			menu.clear();
		}else if( selected == 1 ){
			SubMenu sub = menu.addSubMenu("");
			//Log.e("menu",""+sub.getItem().getItemId());
			sub.add(0, R.style.Theme_Sherlock, 0, "Post Task");
			sub.setIcon(R.drawable.abs__ic_menu_moreoverflow_holo_light);
			sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}else{
			SubMenu sub = menu.addSubMenu("");
			Log.e("menu",""+sub.getItem().getItemId());
			sub.add(0, R.style.Theme_Sherlock, 0, "Filter");
			sub.setIcon(R.drawable.abs__ic_menu_moreoverflow_holo_light);
			sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		return super.onPrepareOptionsMenu(menu);
	} */
	
	private android.view.MenuItem getMenuItem(final MenuItem item) {
		return new android.view.MenuItem() {
			@Override
			public int getItemId() {
				return item.getItemId();
			}

			public boolean isEnabled() {
				return true;
			}

			@Override
			public boolean collapseActionView() {
				return false;
			}

			@Override
			public boolean expandActionView() {
				return false;
			}

			@Override
			public ActionProvider getActionProvider() {
				return null;
			}

			@Override
			public View getActionView() {
				return null;
			}

			@Override
			public char getAlphabeticShortcut() {
				return 0;
			}

			@Override
			public int getGroupId() {
				return 0;
			}

			@Override
			public Drawable getIcon() {
				return null;
			}

			@Override
			public Intent getIntent() {
				return null;
			}

			@Override
			public ContextMenuInfo getMenuInfo() {
				return null;
			}

			@Override
			public char getNumericShortcut() {
				return 0;
			}

			@Override
			public int getOrder() {
				return 0;
			}

			/*@Override
			public SubMenu getSubMenu() {
				// 
				return null;
			}*/

			@Override
			public CharSequence getTitle() {
				return null;
			}

			@Override
			public CharSequence getTitleCondensed() {
				return null;
			}

			@Override
			public boolean hasSubMenu() {
				return false;
			}

			@Override
			public boolean isActionViewExpanded() {
				return false;
			}

			@Override
			public boolean isCheckable() {
				return false;
			}

			@Override
			public boolean isChecked() {
				return false;
			}

			@Override
			public boolean isVisible() {
				return false;
			}

			@Override
			public android.view.MenuItem setActionProvider(
					ActionProvider actionProvider) {
				return null;
			}

			@Override
			public android.view.MenuItem setActionView(View view) {
				return null;
			}

			@Override
			public android.view.MenuItem setActionView(int resId) {
				return null;
			}

			@Override
			public android.view.MenuItem setAlphabeticShortcut(char alphaChar) {
				return null;
			}

			@Override
			public android.view.MenuItem setCheckable(boolean checkable) {
				return null;
			}
			
/*"Cowards die many times before their death" Seems Shakespeare has memorized Arvind Kejriwal only to write this phrase.*/
			
			@Override
			public android.view.MenuItem setChecked(boolean checked) {
				return null;
			}

			@Override
			public android.view.MenuItem setEnabled(boolean enabled) {
				return null;
			}

			@Override
			public android.view.MenuItem setIcon(Drawable icon) {
				return null;
			}

			@Override
			public android.view.MenuItem setIcon(int iconRes) {
				return null;
			}

			@Override
			public android.view.MenuItem setIntent(Intent intent) {
				return null;
			}

			@Override
			public android.view.MenuItem setNumericShortcut(char numericChar) {
				return null;
			}

			@Override
			public android.view.MenuItem setOnActionExpandListener(
					OnActionExpandListener listener) {
				return null;
			}

			@Override
			public android.view.MenuItem setOnMenuItemClickListener(
					OnMenuItemClickListener menuItemClickListener) {
				return null;
			}

			@Override
			public android.view.MenuItem setShortcut(char numericChar,
					char alphaChar) {
				return null;
			}

			@Override
			public void setShowAsAction(int actionEnum) {

			}

			@Override
			public android.view.MenuItem setShowAsActionFlags(int actionEnum) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitle(CharSequence title) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitle(int title) {
				return null;
			}

			@Override
			public android.view.MenuItem setTitleCondensed(CharSequence title) {
				return null;
			}

			@Override
			public android.view.MenuItem setVisible(boolean visible) {
				return null;
			}

			@Override
			public android.view.SubMenu getSubMenu() {
				return null;
			}
		};
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}
	public void selectItem(int position) {
		
		Fragment fragment = new PlanetFragment();
		Bundle args = new Bundle();
		args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getSupportFragmentManager();
		selected = position;
		
		
		if (Integer.parseInt(Constants.id) > 0) 
		{
			if(position == 1)
			{
				Log.e("positionsss", "postion 1");
				Fragment login =new MyEvents();
				fragmentManager.beginTransaction()
				.replace(R.id.content_frame, login).commit();
			} 
			else if(position == 2) 
			{
				Log.e("positionsss", "postion 2");
				Fragment login = new  user_profile();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			}
			else if(position == 3) 
			{
				Log.e("positionsss", "postion 3");
				Fragment login = new  Create_Event();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			}
			else if(position == 4) 
			{
				Log.e("positionsss", "postion 4");
				Fragment login = new  Organizer_list();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			}
			else if(position == 5) 
			{
				Log.e("positionsss", "postion 5");
				Fragment login = new  Change_Password();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			}
			else if(position == 6) 
			{
				Log.e("positionsss", "postion 6");
				Fragment login = new  Logout1();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			}
			
		
			else 
			{
				Log.e("positionsss", "postion else id>0");
				Fragment login = new  Home1();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			}
		} 
		else 
		{
			if (position == 1) 
			{
				Fragment login = new Login1();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
				//fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			}
			
		else if (position == 2) 
			{
				Fragment login = new Signup();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			} 
			else 
			{
				Log.e("positionsss", "postion else");
				Fragment login = new  Home1();
				fragmentManager.beginTransaction().replace(R.id.content_frame, login).commit();
			}
			Log.e("pos", "postion else id<0");
		}
		
		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		
	}
	
	public static class PlanetFragment extends SherlockFragment 
	{
		public static final String ARG_PLANET_NUMBER = "planet_number";

		public PlanetFragment() 
		{
			// Empty constructor required for fragment subclasses
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_planet,
					container, false);
			int i = getArguments().getInt(ARG_PLANET_NUMBER);
//			Log.e("test", "" + i);
			if (i == 1) 
			{

			}
			
			return rootView;
		}
	}
	
	

}
