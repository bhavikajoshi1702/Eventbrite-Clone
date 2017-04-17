package com.example.eventbrite;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.eventbrite.R;
import com.example.eventbrite.Constants;
import com.example.eventbrite.ExecptionHandler;

import com.example.eventbrite.Myevent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


import android.app.SearchManager;


@SuppressLint("NewApi")
public class Home1 extends SherlockFragment implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener 
{
	
	private ListView listview;
	private ProgressDialog loading;
	private LoginAsync loginaync;
	private OnItemClickListener listener;
	List<Myevent> myevents;
	List<Myevent> myevents_searchable;
	JSONArray tickets = null;
	JSONArray events = null;
	Context mContext;
	
	private String mSearchTerm;
	private boolean mIsSearchResultView = false;
	private boolean mSearchQueryChanged;
	private EventsAdapter eventsadapter;
	
	boolean asyncrun;
	JSONObject jsobj;
	
	
	
	

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.dashboard, container, false);
		setHasOptionsMenu(true);
		myevents = new ArrayList<Myevent>();
		myevents_searchable = new ArrayList<Myevent>();
		listview = (ListView) rootView.findViewById(R.id.events);
		Button btn=(Button)rootView.findViewById(R.id.create_new_tkt);
		btn.setVisibility(View.GONE);
		
		loginaync = new LoginAsync();
		loginaync.execute((Void) null);
		listview.setOnItemClickListener(listener);
		
		
		return rootView;
		
	}
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (loading != null)
		{
			if(loading.isShowing())
			{
				loading.dismiss();
			}
		}
	}
	
	@Override
	public boolean onSuggestionClick(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean onSuggestionSelect(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
				return false;
	}
	
	




	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	
	@SuppressLint("NewApi")
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		Log.e("Hii_onoptionmenu","TEST");
		inflater.inflate(R.menu.main, menu);
		
		MenuItem menuitem_refresh = menu.findItem(R.id.action_refresh);
		
		menuitem_refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				loginaync=null;
				loginaync=new LoginAsync();
				loginaync.execute((Void) null);
				return true;
			}
		});
		
		// Retrieves the system search manager service
	
		 // Retrieves the SearchView from the search menu item
		
	
	// Set listeners for SearchView
		
	
	
	}
	
	
	
	
	private class LoginAsync extends AsyncTask<Void, Void, Object> 
	{

		protected void onPreExecute() {
	
		if(loading==null)
			loading = new ProgressDialog(getActivity());
		
			loading.setMessage("All Events");
			loading.setIndeterminate(true);
			loading.setCancelable(true);
			loading.show();
	}

		@Override
		protected Object doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL
				+ "mobile_logins/alllive");
			try 
			{
				HttpResponse response = httpclient.execute(httppost);
				String _response = EntityUtils.toString(response.getEntity());
				Log.e("test",_response); 
				jsobj = new JSONObject(_response);
				events = jsobj.getJSONArray("all_events");		
				tickets=jsobj.getJSONArray("all_tickets");
				String post=jsobj.toString();
				Log.e("ALL_TICKETS",post);
				return jsobj;
			}
		
			catch (Exception e) 
			{
				e.printStackTrace();
				return e;
			}
			}
		
		
		protected void onPostExecute(Object json) 
		{
		
			loading.dismiss();
			Dataadapter adapter=new Dataadapter(getActivity(),R.layout.list_row,events);
			listview.setAdapter(adapter);
			createList();
			if (json instanceof JSONObject) 
			{
					asyncrun = true;
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
					{
					}
			} 
			else 
			{
				ExecptionHandler.register(getActivity(), (Exception) json);
			}
			
		
		}
		
		private void createList() 
		{
			
			
			listview.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) 
				{
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),EventDetails.class);
					
					Bundle bundle = new Bundle();
					try 
					{
					
						bundle.putString("event_title", jsobj.getJSONArray("all_events").getJSONObject(arg2).getString("event_title"));
						bundle.putString("vanue_name", jsobj.getJSONArray("all_events").getJSONObject(arg2).getString("vanue_name"));
						bundle.putString("event_end_date_time", jsobj.getJSONArray("all_events").getJSONObject(arg2).getString("event_end_date_time"));
						bundle.putString("event_detail", jsobj.getJSONArray("all_events").getJSONObject(arg2).getString("event_detail"));
						bundle.putString("event_pass_fees", jsobj.getJSONArray("all_events").getJSONObject(arg2).getString("event_pass_fees"));
						bundle.putString("user_id",jsobj.getJSONArray("all_events").getJSONObject(arg2).getString("user_id"));
						bundle.putString("event_id",jsobj.getJSONArray("all_events").getJSONObject(arg2).getString("id"));
						bundle.putString("has_tick", "0");
						//Log.e("event_title in home 1",jsobj.getJSONObject("event_title").toString() );
					}
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
					intent.putExtras(bundle);
					//intent.putExtra("event_id", myevents_searchable.get(arg2).id + "");
					//startActivity(intent);
					
					if (null != intent) 
					{
						getActivity().startActivity(intent);
					}
				}
				
			});

		}

		
	}



	private class EventsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return myevents_searchable.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null) 
			{
				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						
				vi = inflater.inflate(R.layout.list_row, null);
			}
			
			
			Myevent event = myevents_searchable.get(position);
			Log.e("TESTTTTT","***************************************");
			Log.e("17_HOME_myevent",event.event_title);
			Log.e("17_Home_date_time",event.event_start_date_time);
			Log.e("17_vanue_name_event",event.vanue_name);
			
			
			((TextView) vi.findViewById(R.id.event_name))
					.setText(event.event_title);
			((TextView) vi.findViewById(R.id.event_date_time)).setText(String
					.format("%s %s %s %s", getResources()
							.getString(R.string.on), Constants
							.formatteddate(event.event_start_date_time),
							getResources().getString(R.string.at),
							event.vanue_name));
			return vi;
		}
	}



	





	
	
	 
}



	

