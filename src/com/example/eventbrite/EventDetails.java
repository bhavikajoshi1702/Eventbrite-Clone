package com.example.eventbrite;

import java.io.IOException;
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
import org.json.JSONException;
import org.json.JSONObject;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventDetails extends SherlockFragmentActivity 
{
	private GoogleMap mMap;
    double[] gps = new double[2];
    double ll;
    double llt;
    double d,d1;
    TextView event_title;
	TextView event_date_time;
	TextView event_description;
	TextView event_pass_fees;
	String eventtitle = null;
	String venue = null;
	String eedt = null;
	String ed = null;
	String ef = null;
	String has_ticket;
	int has_tick;
	private Context context;

	public static String event_id;
	public static String user_id;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);
   
        	ActionBar actionbar=getSupportActionBar();
        	actionbar.setDisplayHomeAsUpEnabled(true);
        	
        	
        
        // getting data from pagehome1.java
        Bundle bundle = getIntent().getExtras();;
        if (bundle.getString("event_title") != null)
        eventtitle = bundle.getString("event_title");
        venue = bundle.getString("vanue_name");
        
        eedt = bundle.getString("event_end_date_time");
        String eve_end_date=Constants.formatteddate(eedt);
        ed = bundle.getString("event_detail");
        ef = bundle.getString("event_pass_fees");
        event_id=bundle.getString("event_id");
        user_id=bundle.getString("user_id");
        has_ticket=bundle.getString("has_tick");
        
        has_tick=Integer.parseInt(has_ticket); 
        
        
        
        
      
        
        //by default values
        event_title = (TextView) findViewById(R.id.event_title);
		event_date_time = (TextView) findViewById(R.id.event_date_time);
		event_description = (TextView) findViewById(R.id.event_details);
		
		//user choice values
		event_title.setText(eventtitle);
		event_date_time.setText(venue + "\n" + eve_end_date);
		event_description.setText(ed);
		
		Button btn_event_details=(Button)findViewById(R.id.button1);
		Button btn_publish=(Button)findViewById(R.id.btn_publish);
		Button btn_delete=(Button)findViewById(R.id.btn_del);
		
		
		if(Constants.id.equals(user_id))
		{
		
					btn_delete.setOnClickListener(new View.OnClickListener() 
					{
				
						@Override
						public void onClick(View v) 
						{
							// TODO Auto-generated method stub
							DeleteEvent del = new DeleteEvent();
							del.execute((Void) null);
					
					
							
						}
					});

			if(has_tick==1)
			{
				btn_publish.setOnClickListener(new View.OnClickListener() 
				{
					
					@Override
					public void onClick(View v) {
						
						Log.e("Method...","meth executing...");
						
						
					
					//	Intent intent=new Intent(EventDetails.this,Edit_Event.class);
					//	startActivity(intent);
						
						PublishEvent pub = new PublishEvent();
						pub.execute((Void) null);
						
					}
				});
				
			}
			else
			{
				btn_publish.setVisibility(View.GONE);
			}
			
			
		}
		else
		{
			
			btn_delete.setVisibility(View.GONE);
			btn_publish.setVisibility(View.GONE);
			
		}
		
		
		
		btn_event_details.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				
				
				
				Intent intent=new Intent(EventDetails.this,All_Tickets.class);
				startActivity(intent);
				
				
				
			}
		});
		
    }
   
    
   
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

    

  protected void onResume()
  {
	  super.onResume();
	 // getGPS();
	  setUpMapIfNeeded(d,d1);
  }
  
 

private void setUpMapIfNeeded(double lat, double lon) 
{
try {
    String location = venue;
    Geocoder gc = new Geocoder(this);
    List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects
    
    List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
    for(Address a : addresses){
        if(a.hasLatitude() && a.hasLongitude()){
        	d = a.getLatitude();
        	d1 = a.getLongitude();
        	String s = Double.toString(d);
        	String s1 = Double.toString(d1);
        	
        	Log.e("Latitude", s);
        	Log.e("Longitude", s1);
        	ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
        }  
     
    }  
} catch (IOException e) {
     // handle the exception
}




    if (mMap != null) {
        return;
    }
    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
    if (mMap == null) {
        return;
    }

    double latitudes = d;
    double longitudes =d1;
    
  
    // create marker
    MarkerOptions marker = new MarkerOptions().position(new LatLng(latitudes, longitudes)).title("Hello Maps ");

    // adding marker
    mMap.addMarker(marker);
}



class DeleteEvent extends AsyncTask<Void, Void, Object> 
{
	
	
	
	private ProgressDialog pdia;
	protected void onPreExecute() 
	{
			super.onPreExecute();
			pdia = new ProgressDialog(EventDetails.this);
			pdia.setMessage("Loading...");
			pdia.show();
		
	}

	@Override
	protected Object doInBackground(Void... params) 
	{
		
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constants.BASE_URL
			+ "mobile_logins/delete");
		try 
		{
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("id",event_id));
			
			Log.e("Namevaluepairs_Test", nameValuePairs.toString());
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			Log.e("Test","Test");
			HttpResponse response = httpclient.execute(httppost);
			Log.e("Test1","Test");
			String response1 = EntityUtils.toString(response.getEntity());
			Log.e("test",response1);
			JSONObject json=new JSONObject(response1);
		
			return json;
			
		}
		
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e;
		
		}
	
		
		
			
	}
	protected void onPostExecute(Object json) 
	{
		pdia.dismiss();
			
			Log.e("Ticket_list_onpostexecute","postexecute");
			try 
			{
				if(((JSONObject) json).getString("status").equals("1"))
				{
					
					 Log.e("success","success");	 
					 Intent intent=new Intent(EventDetails.this,EventHome.class);
					 startActivity(intent);	
						
						
					
				
				}
				else
				{
					new AlertDialog.Builder(EventDetails.this)
					.setTitle("")
					.setMessage(((JSONObject) json).getString("msg"))
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
				}
				
			} 
			
			
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		 
	}
}


class PublishEvent extends AsyncTask<Void, Void, Object> 
{
	
	
	
	private ProgressDialog pdia;
	protected void onPreExecute() 
	{
			super.onPreExecute();
			pdia = new ProgressDialog(EventDetails.this);
			pdia.setMessage("Loading...");
			pdia.show();
		
	}

	@Override
	protected Object doInBackground(Void... params) 
	{
		
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constants.BASE_URL
			+ "mobile_logins/publish");
		try 
		{
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("id",event_id));
			
			Log.e("Namevaluepairs_Test", nameValuePairs.toString());
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			Log.e("Test","Test");
			HttpResponse response = httpclient.execute(httppost);
			Log.e("Test1","Test");
			String response1 = EntityUtils.toString(response.getEntity());
			Log.e("test",response1);
			JSONObject json=new JSONObject(response1);
		
			return json;
			
		}
		
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e;
		
		}
	
		
		
			
	}
	protected void onPostExecute(Object json) 
	{
		pdia.dismiss();
			
			Log.e("Ticket_list_onpostexecute","postexecute");
			try 
			{
				if(((JSONObject) json).getString("status").equals("1"))
				{
					
					 Log.e("success","success");
					 
					 Intent intent=new Intent(EventDetails.this,EventHome.class);
						startActivity(intent);	
						
						
					
				
				}
				else
				{
					new AlertDialog.Builder(EventDetails.this)
					.setTitle("")
					.setMessage(((JSONObject) json).getString("msg"))
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
				}
				
			} 
			
			
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		 
	}
}

    
    
}