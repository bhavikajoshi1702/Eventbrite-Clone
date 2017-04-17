package com.example.eventbrite;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.eventbrite.FreeTicketFragment.TicketlistAsync;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class All_Tickets extends SherlockActivity 
{
	public ListView mDrawerList;
	public String[] mPlanetTitles;
	View view;
	private ListView listview;
	
	private TicketlistAsync ticketlistaync;
	private OnItemClickListener listener;
	List<Myevent> myevents;
	List<Myevent> myevents_searchable;
	JSONArray contacts = null;
	// static JSONArray tickets = null;
	boolean asyncrun;
	Button create_tkt;
	// static Button btn;
	static JSONObject json;
	JSONArray tickets = null;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		myevents = new ArrayList<Myevent>();
		myevents_searchable = new ArrayList<Myevent>();
		listview = (ListView)findViewById(R.id.events);
		
		
		
		create_tkt=(Button)findViewById(R.id.create_new_tkt);
	//	btn=(Button)findViewById(R.id.btn_edit_buy);
		
		ticketlistaync = new TicketlistAsync();
		ticketlistaync.execute((Void) null);
		// createList();
		listview.setOnItemClickListener(listener);
		Log.e("tickelist_oncreate","construct");
		Log.e("CHECKN_CONSTANT_ID",Constants.id);
		Log.e("CHECKING_USER_ID",EventDetails.user_id);
	
		ActionBar actionbar=getSupportActionBar();
    	actionbar.setDisplayHomeAsUpEnabled(true);
    	
	
	if(Constants.id.equals(EventDetails.user_id))
	{
		create_tkt.setText("Create New Ticket");
		create_tkt.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				Log.e("Hello","Testing");
				Intent intent=new Intent(All_Tickets.this,AddNewTicket.class);
				startActivity(intent);	
				
			}
		});
	}
	else
	{
		create_tkt.setText("Get Tickets");
		create_tkt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW, 
					     Uri.parse("http://rails2.swapclone.com/home/login"));
					startActivity(intent);
				
			}
		});
	//	create_tkt.setVisibility(View.GONE);
	/*	Button buy_tkt = new Button(this);
		buy_tkt.setText("Buy Tickets");
		 
		// Adding button to listview at footer
		listview.addFooterView(buy_tkt);
		buy_tkt.setBackgroundColor(getResources().getColor(R.color.btn_green));
		buy_tkt.setTextColor(getResources().getColor(R.color.white));
		*/
	}
	}
	/*
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		return asyncrun;
		
	}*/
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	    	switch(item.getItemId())
	    	{
	    	case android.R.id.home:
	    	 backpressed();
	    	 return true;
	    	}
	    	return super.onOptionsItemSelected(item);
	    }
	    
	    // for back button on home icon
	    public void backpressed()
	    {
	    	Intent upintent = new Intent(getApplicationContext(), EventHome.class);
	    	startActivity(upintent);
	    }

	
	private class TicketlistAsync extends AsyncTask<Void, Void, Object> 
	{
		
		
		
		private ProgressDialog pdia;
		protected void onPreExecute() 
		{
				super.onPreExecute();
				pdia = new ProgressDialog(All_Tickets.this);
				pdia.setMessage("Loading...");
				pdia.show();
			
		}

		@Override
		protected Object doInBackground(Void... params) 
		{
			
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL
				+ "mobile_logins/event_tickets_details");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("event_id",EventDetails.event_id));
				nameValuePairs.add(new BasicNameValuePair("user_id",Constants.id));
				Log.e("Namevaluepairs_Test", nameValuePairs.toString());
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				Log.e("Test","Test");
				HttpResponse response = httpclient.execute(httppost);
				Log.e("Test1","Test");
				String response1 = EntityUtils.toString(response.getEntity());
			
				Log.e("test",response1);
				json=new JSONObject(response1);
				tickets = json.getJSONArray("tickets_info");
				String post=json.toString();
				Log.e("ticketlist_TICKETS",post);
				return json;
				
			}
			
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e;
			
			}
		
			
			
				
		}
		protected void onPostExecute(Object jsobj) 
		{
			pdia.dismiss();
				
				Log.e("Ticket_list_onpostexecute","postexecute");
			 
			 Dataadapter_FreeTicket adapter=new Dataadapter_FreeTicket(All_Tickets.this,R.layout.list_row_ticket,tickets);
			 Log.e("TEST_TEST","Testing");
			 listview.setAdapter(adapter);
			
			Log.e("ccccc","ccc_meth_execute");
			 
			
			  Log.e("onpostexecute1","postexecute");
				
				 if (json instanceof JSONObject) 
				 {
						asyncrun = true;
						//eventsadapter.notifyDataSetChanged();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
						{
							
						}
				} 
				
			}
	
		
	}
			
	
}