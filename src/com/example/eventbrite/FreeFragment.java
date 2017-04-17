package com.example.eventbrite;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import com.actionbarsherlock.app.SherlockFragment;
import com.example.eventbrite.R;
import com.example.eventbrite.Create_Event.CreateEvent;
import com.example.eventbrite.user_profile.PreUserTask;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class FreeFragment extends Fragment implements OnClickListener 
{
	EditText TicketName, TicketQuantity, TicketPrice, TicketDescription;
	TextView txt_price;
	Button btn_save;
	Button b_start_calender, b_end_calender,b_start_timepicker, b_end_timepicker;
	EditText start_date, end_date,start_time, end_time;
	private int start_year, start_month, start_day, start_hour, start_minute;
	private int end_year, end_month, end_day, end_hour, end_minute;
	private String start_date_time, end_date_time;
	Calendar startDate;
	String start_d, end_d, start_t, end_t;
	private CreateTicket createticket=null;
	boolean asyncrun;
	JSONObject jsobj1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{

		View rootView = inflater.inflate(R.layout.create_ticket, container, false);
		
	//	PreUserTask preusertask = new PreUserTask();
	//	preusertask.execute((Void) null);
		
		TicketName=(EditText)rootView.findViewById(R.id.ticket_name);
		TicketQuantity=(EditText)rootView.findViewById(R.id.ticket_quantity);
		TicketPrice=(EditText)rootView.findViewById(R.id.ticket_price);
		TicketDescription=(EditText)rootView.findViewById(R.id.ticket_description);
		txt_price=(TextView)rootView.findViewById(R.id.tkt_price_tv);
		b_start_calender=(Button)rootView.findViewById(R.id.btn_tkt_start_calender);
		b_end_calender=(Button)rootView.findViewById(R.id.btn_tkt_end_calender);
		start_date=(EditText)rootView.findViewById(R.id.tkt_start_date);
		b_start_timepicker=(Button)rootView.findViewById(R.id.btn_tkt_start_timepicker);
	
		b_end_timepicker=(Button)rootView.findViewById(R.id.btn_tkt_end_timepicker);
		
		start_time=(EditText)rootView.findViewById(R.id.tkt_start_time);
	
		end_time=(EditText)rootView.findViewById(R.id.tkt_end_time);
	
		
		end_date=(EditText)rootView.findViewById(R.id.tkt_end_date);
		
		
		
		b_start_calender.setOnClickListener(this);
		b_end_calender.setOnClickListener(this);
		b_start_timepicker.setOnClickListener(this);
		
		b_end_timepicker.setOnClickListener(this);
		
		
		
		if(AddNewTicket.ticket_type==1)
		{
			TicketPrice.setVisibility(View.GONE);
			txt_price.setVisibility(View.GONE);
			
		}
		
		if(AddNewTicket.ticket_type==3)
		{
			TicketPrice.setVisibility(View.GONE);
			txt_price.setVisibility(View.GONE);
			
		}
		
		
		btn_save=(Button)rootView.findViewById(R.id.save);
		btn_save.setOnClickListener(new OnClickListener() 
		{
			
			
			@Override
			public void onClick(View v) {
				Log.e("Hello_onclick","onclick");
				// TODO Auto-generated method stub
				createticket = new CreateTicket();
				createticket.execute((Void) null);
			}
		});
		
		
		
	
		return rootView;
	}
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		if(v==b_start_calender)
		{
			final Calendar c=Calendar.getInstance();
			start_year=c.get(Calendar.YEAR);
			start_month=c.get(Calendar.MONTH);
			start_day=c.get(Calendar.DAY_OF_MONTH);
		
		DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() 
		{

		    public void onDateSet(DatePicker view, int year,
		            int monthOfYear, int dayOfMonth) 
		    {
		    	
		    	
		    	startDate = Calendar.getInstance();
		    	startDate.set(Calendar.YEAR, year);
		    	startDate.set(Calendar.MONTH, monthOfYear);
		    	startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		       start_date.setText(dayOfMonth + "-"
						+ (monthOfYear + 1) + "-" + year);
		       
		        start_d=year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
		    
		       Log.e("Start_date",startDate.toString());
		       
		       
		    }
		    
		     
		};
		DatePickerDialog d = new DatePickerDialog(getActivity(),
		         mDateSetListener, start_year, start_month, start_day);
		d.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
		d.show();
		
		
	}  
		
		if(v==b_start_timepicker)
		{
			final Calendar c = Calendar.getInstance();
			start_hour=c.get(Calendar.HOUR_OF_DAY);
			start_minute=c.get(Calendar.MINUTE);
			
			TimePickerDialog.OnTimeSetListener mTimeSetListner = new TimePickerDialog.OnTimeSetListener() 
			{
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					start_time.setText(hourOfDay + ":" + minute);
					 start_t=hourOfDay + ":" + minute + ":"+"00";
				}
			};
			TimePickerDialog t=new TimePickerDialog(getActivity(), mTimeSetListner, start_hour, start_minute,false);
			t.show();
			
		}
		
	
	
	
	
	if(v==b_end_calender)
	{
		final Calendar c=Calendar.getInstance();
		end_year=c.get(Calendar.YEAR);
		end_month=c.get(Calendar.MONTH);
		end_day=c.get(Calendar.DAY_OF_MONTH);
		
		DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() 
		{

		    public void onDateSet(DatePicker view, int year,
		            int monthOfYear, int dayOfMonth) 
		    {
		       
		    	Calendar newEndDate=Calendar.getInstance();
		    	newEndDate.set(Calendar.YEAR, year);
		    	newEndDate.set(Calendar.MONTH, monthOfYear);
		    	newEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		    	Log.e("End_date",newEndDate.toString());
		    	 end_d=year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
		  	
		    	
		    	if(newEndDate.before(startDate))
		    	{
		    		end_date.setText("Plz.. enter date after starting date..");
		    		
		    	}
		    	else
		    	{	
		    		end_date.setText(dayOfMonth + "-"
							+ (monthOfYear + 1) + "-" + year);	
		    	}
		    }
		};
			DatePickerDialog d = new DatePickerDialog(getActivity(),mDateSetListener, end_year, end_month, end_day);
			
			d.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
			
			d.show();	
	}  
	
	if(v==b_end_timepicker)
	{
		final Calendar c = Calendar.getInstance();
		end_hour=c.get(Calendar.HOUR_OF_DAY);
		end_minute=c.get(Calendar.MINUTE);
		
		TimePickerDialog.OnTimeSetListener mTimeSetListner = new TimePickerDialog.OnTimeSetListener() 
		{
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				end_time.setText(hourOfDay + ":" + minute);
				end_t=hourOfDay + ":" + minute + ":"+"00";
			}
		};
		TimePickerDialog t=new TimePickerDialog(getActivity(), mTimeSetListner, end_hour, end_minute,false);
		t.show();
		//     Date futureDate = new Date(new Date().getTime() + 86400000);
	}

		
	}
	
	public void create_ticket()
	{
		
		Boolean isvalid = true;
		EditText temp = null;
		if (TextUtils.isEmpty(TicketName.getText().toString())) {
			TicketName.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = TicketName;
		}
		if (TextUtils.isEmpty(TicketQuantity.getText().toString())) {
			TicketQuantity.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = TicketQuantity;
		}
		
		if (TextUtils.isEmpty(TicketPrice.getText().toString())) {
			TicketPrice.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = TicketPrice;
		}
		
		if (TextUtils.isEmpty(TicketDescription.getText().toString())) {
			TicketDescription.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = TicketDescription;
		}
		
		if (isvalid)
		{
			
			createticket = new CreateTicket();
			createticket.execute((Void) null);
		}
		else
		{
			temp.setFocusable(true);
		}
		
	}
	
	
	class CreateTicket extends AsyncTask<Void, Void, Object>
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
		protected Object doInBackground(Void... params) {
			// TODO Auto-generated method stub
		
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/create_ticket");
			

			try 
			{
				start_date_time=start_d+" "+start_t;
				end_date_time=end_d+" "+end_t;
				String type=String.valueOf(AddNewTicket.ticket_type);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);	
				nameValuePairs.add(new BasicNameValuePair("event_id",EventDetails.event_id));
				nameValuePairs.add(new BasicNameValuePair("ticket_type",type));
				nameValuePairs.add(new BasicNameValuePair("ticket_name", TicketName.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("qty", TicketQuantity.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("price", TicketPrice.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("description", TicketDescription.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("start_sale",start_date_time));
				nameValuePairs.add(new BasicNameValuePair("end_sale", end_date_time));
			
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
							
							Intent intent=new Intent(getActivity(),All_Tickets.class);
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
				 			ExecptionHandler.register(getActivity(), (Exception) jsobj);
				 		}	
				 }
				
		
		}
	}
	
	
	
	
}
