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
import org.json.JSONException;
import org.json.JSONObject;

import com.example.eventbrite.EventDetails.DeleteEvent;
import com.example.eventbrite.Login1.LoginAsync;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class Delete_Ticket extends Activity
{
	String tt_id;
	DeleteTicket deleteticket=null;
	 @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
		 
		 Bundle a = getIntent().getExtras();
		 tt_id =(String) a.get("ticket_id");
		 Log.e("my Tickets id",tt_id);
		 
		 Log.e("Hiii","Test");
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.dashboard);
	        Log.e("working","layout");
	        
	        deleteticket = new DeleteTicket();
	        
	        Log.e("object sucess","object sucess");
			deleteticket.execute((Void) null);
			Log.e("obj_meth_calling","obj meth call");
	   
			
			
			 
	    }
	
	
	
	
	
	private class DeleteTicket extends AsyncTask<Void, Void, Object> 
	{
		
		
		
		private ProgressDialog pdia;
		protected void onPreExecute() 
		{
				super.onPreExecute();
				pdia = new ProgressDialog(Delete_Ticket.this);
				pdia.setMessage("Loading...");
				pdia.show();
				Log.e("Pre","pre");			
		}

		@Override
		protected Object doInBackground(Void... params) 
		{
			Log.e("TESTing","Test");
			
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL
				+ "mobile_logins/delete_ticket");
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("id",tt_id));
				
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
						 Intent intent=new Intent(Delete_Ticket.this,All_Tickets.class);
							startActivity(intent);	
					}
					
					
					else
					{
						new AlertDialog.Builder(Delete_Ticket.this)
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