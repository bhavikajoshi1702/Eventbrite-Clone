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
import com.example.eventbrite.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CancelEventFragment extends Fragment 
{
	
	public ListView mDrawerList;
	public String[] mPlanetTitles;
	View view;
	private ListView listview;
	private ProgressDialog loading;
	private LoginAsync loginaync;
	private OnItemClickListener listener;
	List<Myevent> myevents;
	List<Myevent> myevents_searchable;
	JSONArray contacts = null;
	JSONArray events = null;
	JSONArray cancel = null;
	boolean asyncrun;
	JSONObject jsobj;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{

		Log.e("oncreateview","construct");
		View rootView = inflater.inflate(R.layout.dashboard, container, false);
		myevents_searchable = new ArrayList<Myevent>();
		listview = (ListView) rootView.findViewById(R.id.events);
		Button btn=(Button)rootView.findViewById(R.id.create_new_tkt);
		btn.setVisibility(View.GONE);
		loginaync = new LoginAsync();
		loginaync.execute((Void) null);
		listview.setOnItemClickListener(listener);
		Log.e("oncreateview...","construct");
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
class LoginAsync extends AsyncTask<Void, Void, Object> 
{
		
		
		private ProgressDialog pdia;
		protected void onPreExecute() 
		{
				super.onPreExecute();
				pdia = new ProgressDialog(getActivity());
				pdia.setMessage("Loading...");
				pdia.show();
			
		}

		@Override
		protected Object doInBackground(Void... params) 
		{
				// TODO Auto-generated method stub
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(Constants.BASE_URL
					+ "mobile_logins/all_my_event");
				try 
				{
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("id",	Constants.id));
					//nameValuePairs.add(new BasicNameValuePair("imei", Constants.IMEI));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					String _response = EntityUtils.toString(response.getEntity());
					Log.e("test",_response); 
					 jsobj = new JSONObject(_response);
					events = jsobj.getJSONArray("cancel_events");
					cancel=jsobj.getJSONArray("cancel_tickets");
					
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
		pdia.dismiss();
			
			Log.e("onpostexecute","postexecute");
			String post=json.toString();
			 Log.e("rese",post);
			 
			
			 
			
			 createList();
			 Dataadapter adapter=new Dataadapter(getActivity(),R.layout.list_row,events);
			 
			 listview.setAdapter(adapter);
			 Log.e("onpostexecute1","postexecute");
			//loading.dismiss();
			 if (json instanceof JSONObject) {
					asyncrun = true;
					//eventsadapter.notifyDataSetChanged();
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					}
				} else {
					ExecptionHandler.register(getActivity(), (Exception) json);
				}	
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
					bundle.putString("event_title", jsobj.getJSONArray("cancel_events").getJSONObject(arg2).getString("event_title"));
					bundle.putString("vanue_name", jsobj.getJSONArray("cancel_events").getJSONObject(arg2).getString("vanue_name"));
					bundle.putString("event_end_date_time", jsobj.getJSONArray("cancel_events").getJSONObject(arg2).getString("event_end_date_time"));
					bundle.putString("event_detail", jsobj.getJSONArray("cancel_events").getJSONObject(arg2).getString("event_detail"));
					bundle.putString("event_pass_fees", jsobj.getJSONArray("cancel_events").getJSONObject(arg2).getString("event_pass_fees"));
					bundle.putString("user_id",jsobj.getJSONArray("cancel_events").getJSONObject(arg2).getString("user_id"));
					bundle.putString("event_id",jsobj.getJSONArray("cancel_events").getJSONObject(arg2).getString("id"));
					bundle.putString("has_tick", "0");
					//Log.e("event_title in home 1",jsobj.getJSONObject("event_title").toString() );
				}
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
				intent.putExtras(bundle);
				//intent.putExtra("event_id", myevents_searchable.get(arg2).id + "");
			//	startActivity(intent);
				
				if (null != intent) 
				{
					getActivity().startActivity(intent);
				}
			}
			
		});

	}
	
}
