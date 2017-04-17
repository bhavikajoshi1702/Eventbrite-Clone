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

import com.example.eventbrite.EventDetails.DeleteEvent;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.TextView;




public class Dataadapter_FreeTicket extends BaseAdapter
{
	
	public static String ticket_id;
	private final Context context;
	public JSONArray values;
	public TextView textview;
	public TextView textview1;
	public TextView textview2;
	public TextView textview3;
	public TextView textview4;
	public Button btn;
	public Button btn_del;
	JSONObject temp = null;
	
	
	
	public Dataadapter_FreeTicket(Context context, int _resource, JSONArray values) 
	{
		
		// TODO Auto-generated constructor stub
		
		
		this.context = context;
		this.values = values;
		
	}

	@Override
	public int getCount() 
	{
		
			
			
			return values.length();
			
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		try 
		{
			return values.get(position);
		} 
		catch (JSONException e) 
		{
			return e;
		}
	}
	

	@Override
	public long getItemId(int arg0) 
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder = new ViewHolder();
		
		
		
		
		if (convertView == null) 
		{
			convertView = inflater.inflate(R.layout.list_row_ticket, null);
			holder.textview = (TextView) convertView
					.findViewById(R.id.ticket_name);
			holder.textview1 = (TextView) convertView
					.findViewById(R.id.ticket_start_date);
			holder.textview2 = (TextView) convertView
					.findViewById(R.id.ticket_end_date);
			holder.textview3=(TextView)convertView
					.findViewById(R.id.ticket_price);
			holder.textview4=(TextView)convertView
					.findViewById(R.id.ticket_qty);
			holder.btn=(ImageButton)convertView
					.findViewById(R.id.btn_edit_buy);
			holder.btn_del=(ImageButton)convertView.findViewById(R.id.btn_delete);
			
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		try {
			
			temp = (JSONObject) values.get(position);
			
			
			//.replace("null", " ");
			
			
			if(temp.getInt("free")==1)
			{
				
			
				Log.e("Freeee","Freeee");
					String title=temp.get("Ticket Name:"+"    "+"free_ticket_name").toString();
					//String Ctitle = title.substring(0, 10);
					holder.textview.setText(title);
					String tkt_s_date=temp.get("free_start_sale").toString();
					String s_date=Constants.formatteddate(tkt_s_date);
					holder.textview1.setText("Selling Start Date:"+"    "+s_date);
					String tkt_e_date=temp.get("free_end_sale").toString();
					String e_date=Constants.formatteddate(tkt_e_date);
					holder.textview2.setText("Selling End Date:"+"    "+e_date);
					String q=temp.get("free_qty").toString();
					holder.textview3.setText("Available Tickets:"+"    "+q);
				
					holder.textview4.setText("FREE TICKETS");
			}
			else if(temp.getInt("paid")==1)
			{
				Log.e("Paiddddd","Paidddd");
				String title=temp.get("paid_ticket_name").toString();
				//String Ctitle = title.substring(0, 10);
				holder.textview.setText("Ticket Name:"+"    "+title);
				String tkt_s_date=temp.get("paid_start_sale").toString();
				String s_date=Constants.formatteddate(tkt_s_date);
				holder.textview1.setText("Selling Start Date:"+"    "+s_date);
				String tkt_e_date=temp.get("paid_end_sale").toString();
				String e_date=Constants.formatteddate(tkt_e_date);
				holder.textview2.setText("Selling End Date:"+"    "+e_date);
				String q=temp.get("paid_qty").toString();
				holder.textview3.setText("Available Tickets:"+"    "+q);
			
				String p=temp.get("paid_price").toString();
				holder.textview4.setText("Ticket Price:"+"    "+p);
				
			}
			else if(temp.getInt("donation")==1)
			{
				Log.e("Donation","Donation");
				String title=temp.get("donation_ticket_name").toString();
				//String Ctitle = title.substring(0, 10);
				holder.textview.setText("Ticket Name:"+"    "+title);
				String tkt_s_date=temp.get("donation_start_sale").toString();
				String s_date=Constants.formatteddate(tkt_s_date);
				holder.textview1.setText("Selling Start Date:"+"    "+s_date);
				String tkt_e_date=temp.get("donation_end_sale").toString();
				String e_date=Constants.formatteddate(tkt_e_date);
				holder.textview2.setText("Selling End Date:"+"    "+e_date);
			String q=temp.get("donation_qty").toString();
				holder.textview3.setText("Available Tickets:"+"    "+q);
				holder.textview4.setText("DONATION TICKETS");
			//	String p=temp.get("donation_price").toString();
			//	holder.textview4.setText("Ticket Price:"+"    "+p);
			}
			else
			{
				Log.e("Else Condition..","Else Conditionnnn");
				
			}
					Log.e("con_id",Constants.id);
					Log.e("e_d_id",EventDetails.user_id);
					
					if(Constants.id.equals(EventDetails.user_id))
					{
						//holder.btn.setText("EDIT");
						holder.btn.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) 
							{
							//	Toast.makeText(getApplicationContext()," MSG", Toast.LENGTH_LONG).show();
								
									String json_res;
									json_res=All_Tickets.json.toString();
								
									
									
							
								Log.e("position",String.valueOf(position));
								
								Log.e("Edit_meth...","Edit...");
								
								try 
								{
							
									ticket_id=values.getJSONObject(position).getString("id");
									
									Log.e("Ticket ID",ticket_id);
									Intent i =new Intent(context,Edit_Ticket.class);
									i.putExtra("ticket_id", ticket_id);
									context.startActivity(i);
									
									//context.startActivity(new Intent(context,Edit_Ticket.class));
								}
								catch (JSONException e) 
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
							}
						});  
						
						
						holder.btn_del.setOnClickListener(new OnClickListener() 
						{
							
							@Override
							public void onClick(View v)
							{
								// TODO Auto-generated method stub
							
								Log.e("Delete_meth...","delete...");
								try 
								{
									ticket_id=values.getJSONObject(position).getString("id");
									Intent ii = new Intent(context,Delete_Ticket.class);
									ii.putExtra("ticket_id",ticket_id);
									context.startActivity(ii);
								}
								catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							//	context.startActivity(new Intent(context,Delete_Ticket.class));
							}
						});
					}
					else
					{
					//	holder.btn.setText("BUY");
						holder.btn.setVisibility(View.GONE);
						holder.btn_del.setVisibility(View.GONE);
						
					
						
					}
					
					Log.e("Title",temp.get("free_ticket_name").toString());
					Log.e("Venue",temp.get("free_description").toString());
					Log.e("Date",temp.get("free_start_sale").toString());
					ticket_id=temp.get("id").toString();
					Log.e("Ticket ID",ticket_id);
					convertView.setId(position);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return convertView;
	}
	
	private class ViewHolder 
	{
		public TextView textview;
		public TextView textview1;
		public TextView textview2;
		public TextView textview3;
		public TextView textview4;
		public ImageButton btn;
		public ImageButton btn_del;
		public Spinner spinner_tick;
	
	}

	

	
	
	
}


/*
 * 
 * String aString ="123456789"; String cutString = aString.substring(0, 4);
 */



