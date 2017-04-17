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

public class FreeTicketFragment extends Fragment
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
	static Button btn;
	JSONObject json;
	JSONArray tickets = null;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{

		View rootView = inflater.inflate(R.layout.dashboard, container, false);
		myevents = new ArrayList<Myevent>();
		myevents_searchable = new ArrayList<Myevent>();
		listview = (ListView)  rootView.findViewById(R.id.events);
		create_tkt=(Button)rootView.findViewById(R.id.create_new_tkt);
		btn=(Button)rootView.findViewById(R.id.btn_edit_buy);
		ticketlistaync = new TicketlistAsync();
		ticketlistaync.execute((Void) null);
		listview.setOnItemClickListener(listener);
		Log.e("tickelist_oncreate","construct");
		Log.e("CHECKN_CONSTANT_ID",Constants.id);
		Log.e("CHECKING_USER_ID",EventDetails.user_id);
		
		if(Constants.id.equals(EventDetails.user_id))
		{
			create_tkt.setOnClickListener(new View.OnClickListener() 
			{
				
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					Log.e("Hello","Testing");
					
					
					//For Starting newActivity form Fragment..
					getActivity().startActivity(new Intent(getActivity().getApplicationContext(),AddNewTicket.class));
					
				/*	Intent  intent   =   new Intent(getActivity().getApplicationContext(), ticket1.class);
				    getActivity().startActivity(intent);  */
					
					
				}
			});
		}
		else
		{
			
			create_tkt.setVisibility(View.GONE);
			
		}
		

		return rootView;
	}
	
	
	class TicketlistAsync extends AsyncTask<Void, Void, Object> 
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
		protected void onPostExecute(Object json) 
		{
			pdia.dismiss();
				
				Log.e("free_post","postexecute");
				
				
				 
			 Dataadapter_FreeTicket adapter=new Dataadapter_FreeTicket(getActivity(),R.layout.list_row_ticket,tickets);
			 Log.e("TEST_TEST","Testing");
			 listview.setAdapter(adapter);
			 Log.e("test","TESTTTT");
			 
			 createList();
				 Log.e("onpostexecute1","postexecute");
				
				 if (json instanceof JSONObject) 
				 {
						asyncrun = true;
						//eventsadapter.notifyDataSetChanged();
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
		Log.e("Createlist","Createlist");
		listview.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				// TODO Auto-generated method stub
				
				Bundle bundle1 = new Bundle();
				try 
				{
					bundle1.putString("Event_idd", json.getJSONArray("tickets_info").getJSONObject(arg2).getString("event_id"));
					bundle1.putString("Ticket_idd", json.getJSONArray("tickets_info").getJSONObject(arg2).getString("id"));
					Log.e("BBBBBBBBBBBBBBBBB","BBBBBBBBB");
					Log.e("IIIIIIIDDDDDDD",json.getJSONArray("tickets_info").getJSONObject(arg2).getString("id"));
				
				}
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
				
				//intent.putExtra("event_id", myevents_searchable.get(arg2).id + "");
				//startActivity(intent);
				
				
			}
			
		});

	}

	
}
	
	

}