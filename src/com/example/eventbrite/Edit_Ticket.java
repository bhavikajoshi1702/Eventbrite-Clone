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

import com.example.eventbrite.Edit_Event.CreateEvent1;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Edit_Ticket extends Activity
{
	
	JSONObject jsobj1;
	EditText TicketName, TicketQuantity, TicketPrice, TicketDescription;
	Button btn_save;
	EditText start_date, start_time, end_date, end_time;
	String s_date, e_date, ss_date, ss_time, ee_date, ee_time;
	private EditTicket editticket=null;
	
	String tt_id;
	boolean asyncrun;	
	 @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
		 
		 Log.e("Hiii","Test");
		 Bundle a = getIntent().getExtras();
		 tt_id =(String) a.get("ticket_id");
		 Log.e("my Tickets id",tt_id);
		
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.create_ticket);
	        Log.e("working","layout");
	        
	    	TicketName=(EditText)findViewById(R.id.ticket_name);
			TicketQuantity=(EditText)findViewById(R.id.ticket_quantity);
			TicketPrice=(EditText)findViewById(R.id.ticket_price);
			TicketDescription=(EditText)findViewById(R.id.ticket_description);
			start_date=(EditText)findViewById(R.id.tkt_start_date);
			start_time=(EditText)findViewById(R.id.tkt_start_time);
			end_date=(EditText)findViewById(R.id.tkt_end_date);
			end_time=(EditText)findViewById(R.id.tkt_end_time);
			btn_save=(Button)findViewById(R.id.save);
			
			btn_save.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					editticket = new EditTicket();
					editticket.execute((Void) null);
					
				}
			});
			
	        
	        Log.e("object sucess","object sucess");
	        PreUserTask preusertask = new PreUserTask();
			preusertask.execute((Void) null);

			Log.e("obj_meth_calling","obj meth call");
	   
	    }
	
	
	
	
	
	 class PreUserTask extends AsyncTask<Void, Void, Object>

		{

			
			private ProgressDialog pdia;
			protected void onPreExecute()
			{
					super.onPreExecute();
					pdia = new ProgressDialog(Edit_Ticket.this);
					pdia.setMessage("Loading...");
					pdia.show();
				
			}
			
			@Override
			protected Object doInBackground(Void... params) 
			{
				// TODO Auto-generated method stub
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/ticket_data");

				try 
				{
					
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("id",tt_id));
					//nameValuePairs.add(new BasicNameValuePair("imei", Constants.IMEI));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					String _response = EntityUtils.toString(response.getEntity());
					Log.e("test",_response); 
					jsobj1 = new JSONObject(_response);
					
					return jsobj1;
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
				
			//	String postexecute;
				Log.e("onpostexecute","postexecute");
				String post=json.toString();
				 Log.e("rese",post);
				 Log.e("onpostexecute1","postexecute");
				 if (json instanceof JSONObject) 
				 {
				 try 
						{	
					 
					 if(jsobj1.getJSONObject("tick_info").getInt("free")==1)
					 {
						 Log.e("Freeee","Freeee");
						 TicketName.setText(jsobj1.getJSONObject("tick_info").get("free_ticket_name").toString().replace("null", " "));
						 TicketQuantity.setText(jsobj1.getJSONObject("tick_info").get("free_qty").toString().replace("null", " "));
						 TicketPrice.setVisibility(View.GONE);
						 TicketDescription.setText(jsobj1.getJSONObject("tick_info").get("free_description").toString().replace("null"," "));
					  s_date=jsobj1.getJSONObject("tick_info").get("free_start_sale").toString().replace("null"," ");
					  e_date=jsobj1.getJSONObject("tick_info").get("free_end_sale").toString().replace("null"," ");
					  ss_date=Constants.formatteddate1(s_date);
					  ss_time=Constants.formatteddate2(s_date);
					 
					  ee_date=Constants.formatteddate1(e_date);
					  ee_time=Constants.formatteddate2(e_date);
					 
					 start_date.setText(ss_date);
					 start_time.setText(ss_time);
					 end_date.setText(ee_date);
					 end_time.setText(ee_time);
					 
					 
							
					 }
					 else if(jsobj1.getJSONObject("tick_info").getInt("paid")==1)
					 {
						 
					
						 Log.e("Paiddddd","pAIDDDD");	
						 TicketName.setText(jsobj1.getJSONObject("tick_info").get("paid_ticket_name").toString().replace("null", " "));
						 TicketQuantity.setText(jsobj1.getJSONObject("tick_info").get("paid_qty").toString().replace("null", " "));
					 TicketPrice.setText(jsobj1.getJSONObject("tick_info").get("paid_price").toString().replace("null", " "));
						 TicketDescription.setText(jsobj1.getJSONObject("tick_info").get("paid_description").toString().replace("null"," "));
					  s_date=jsobj1.getJSONObject("tick_info").get("paid_start_sale").toString().replace("null"," ");
					  e_date=jsobj1.getJSONObject("tick_info").get("paid_end_sale").toString().replace("null"," ");
					  
					  ss_date=Constants.formatteddate1(s_date);
					  ss_time=Constants.formatteddate2(s_date);
					 
					  ee_date=Constants.formatteddate1(e_date);
					  ee_time=Constants.formatteddate2(e_date);
					 
					 start_date.setText(ss_date);
					 start_time.setText(ss_time);
					 end_date.setText(ee_date);
					 end_time.setText(ee_time);
					 }	
					 
					 else if(jsobj1.getJSONObject("tick_info").getInt("donation")==1)
					 {
						 
					
						 Log.e("Donation","Donation");	
						 TicketName.setText(jsobj1.getJSONObject("tick_info").get("donation_ticket_name").toString().replace("null", " "));
						 TicketQuantity.setText(jsobj1.getJSONObject("tick_info").get("donation_qty").toString().replace("null", " "));
					 TicketPrice.setText(jsobj1.getJSONObject("tick_info").get("donation_price").toString().replace("null", " "));
						 TicketDescription.setText(jsobj1.getJSONObject("tick_info").get("donation_description").toString().replace("null"," "));
					  s_date=jsobj1.getJSONObject("tick_info").get("donation_start_sale").toString().replace("null"," ");
					  e_date=jsobj1.getJSONObject("tick_info").get("donation_end_sale").toString().replace("null"," ");
					  
					  ss_date=Constants.formatteddate1(s_date);
					  ss_time=Constants.formatteddate2(s_date);
					 
					  ee_date=Constants.formatteddate1(e_date);
					  ee_time=Constants.formatteddate2(e_date);
					 
					 start_date.setText(ss_date);
					 start_time.setText(ss_time);
					 end_date.setText(ee_date);
					 end_time.setText(ee_time);
					 }	



						}
						catch (JSONException e) 
						{
							e.printStackTrace();
						}   
						
				}
				else 
				{
						ExecptionHandler.register(Edit_Ticket.this, (Exception) json);
				}	
			}
			
		
	}  
	 
	 class EditTicket extends AsyncTask<Void, Void, Object>
		{
			
			private ProgressDialog pdia;
			protected void onPreExecute() 
			{
					super.onPreExecute();
					pdia = new ProgressDialog(Edit_Ticket.this);
					pdia.setMessage("Loading...");
					pdia.show();
				
			}
			@Override
			protected Object doInBackground(Void... params) {
				// TODO Auto-generated method stub
			
				
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/edit_ticket");
				

				try 
				{
				//	start_date_time=start_d+" "+start_t;
				//	end_date_time=end_d+" "+end_t;
				//	String type=String.valueOf(AddNewTicket.ticket_type);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);	
					nameValuePairs.add(new BasicNameValuePair("event_id",EventDetails.event_id));
					nameValuePairs.add(new BasicNameValuePair("ticket_type","3"));
					nameValuePairs.add(new BasicNameValuePair("id",tt_id));
					nameValuePairs.add(new BasicNameValuePair("ticket_name", TicketName.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("qty", TicketQuantity.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("price", TicketPrice.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("description", TicketDescription.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("start_sale","2014-04-27 00:00:00"));
					nameValuePairs.add(new BasicNameValuePair("end_sale", "2014-08-27 00:00:00"));
				
					Log.e("test", nameValuePairs.toString());
					
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					String _response = EntityUtils.toString(response.getEntity());
					Log.e("Event_test",_response); 
					JSONObject jsobj = new JSONObject(_response);
				
					return jsobj;    
				}
			
				catch (Exception e) 
				{
					e.printStackTrace();
					return e;
				}
				
				
			}
			
			protected void onPostExecute( Object jsobj) 
			{
				
				pdia.dismiss();
				Log.e("event_onpostexecute","event_postexecute");
				String post=jsobj.toString();
				Log.e("event_rese",post);
				Log.e("event_onpostexecute1","event_postexecute");
				
				 if (jsobj instanceof JSONObject) 
				 {
					 
					 try {
							if(((JSONObject) jsobj).getString("status").equals("1"))
							{
								Log.e("success","success");
							//	 ticket2 eve = (ticket2) getActivity();
								
								Intent intent=new Intent(Edit_Ticket.this,All_Tickets.class);
								startActivity(intent);
									
							}
					 }
					 catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
							asyncrun = true;
							//eventsadapter.notifyDataSetChanged();
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
							{
							}
					 
					 
					 		else 
					 		{
					 			ExecptionHandler.register(Edit_Ticket.this, (Exception) jsobj);
					 		}	
					 }
					
			
			}
		}
		
		


	
	
	
	
}