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
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;


public class Organizer_list extends SherlockFragment
{
	
	private ListView listview;
	private ProgressDialog loading;
	private LoginAsync loginaync;
	private OnItemClickListener listener;
	List<Myevent> myevents;
	List<Myevent> myevents_searchable;
	
	JSONArray organizer = null;
	JSONObject jsobj;
	boolean asyncrun;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.dashboard, container, false);
		setHasOptionsMenu(true);
		myevents = new ArrayList<Myevent>();
		myevents_searchable = new ArrayList<Myevent>();
		listview = (ListView) rootView.findViewById(R.id.events);
		Button btn=(Button)rootView.findViewById(R.id.create_new_tkt);
		loginaync = new LoginAsync();
		loginaync.execute((Void) null);
		listview.setOnItemClickListener(listener);
		btn.setText("Create New Organizer");
		btn.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				Log.e("Hello","Testing");
				Intent intent=new Intent(getActivity(),Create_Organizer.class);
				startActivity(intent);	
				
			}
		});
		
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
		
		
		
	}
	private class LoginAsync extends AsyncTask<Void, Void, Object> 
	{

		protected void onPreExecute() {
	
		if(loading==null)
			loading = new ProgressDialog(getActivity());
		
			loading.setMessage("Loadind...");
			loading.setIndeterminate(true);
			loading.setCancelable(true);
			loading.show();
	}

		@Override
		protected Object doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/all_my_event");
			try 
			{
				
				
				List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(1);
				nameValuePairs1.add(new BasicNameValuePair("id",Constants.id));  
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
				HttpResponse response = httpclient.execute(httppost);
				String _response = EntityUtils.toString(response.getEntity());
				Log.e("test",_response); 
				jsobj = new JSONObject(_response);
				organizer = jsobj.getJSONArray("organizers");		
				
				String post=jsobj.toString();
				Log.e("organizers",post);
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
			Dataadapter_organizer adapter=new Dataadapter_organizer(getActivity(),R.layout.list_row,organizer);
			listview.setAdapter(adapter);
			
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
		}
		

	
}