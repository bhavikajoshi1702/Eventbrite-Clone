package com.example.eventbrite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.urlimageviewhelper.*;

public class Dataadapter extends BaseAdapter
{
	public static String event_id;
	public static String user_id;
	private final Context context;
	public JSONArray values;
	public static String uu_id;
	public static String ee_id;
	

	public Dataadapter(Context context, int _resource, JSONArray values) 
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		this.values = values;
		
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
	//	Log.e("inside count","ddfd");
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder = new ViewHolder();
		
		if (convertView == null) 
		{
			convertView = inflater.inflate(R.layout.list_row, null);
			holder.textview = (TextView) convertView
					.findViewById(R.id.event_name);
			holder.textview1 = (TextView) convertView
					.findViewById(R.id.event_description);
			holder.textview2 = (TextView) convertView
					.findViewById(R.id.event_date_time);
			holder.imageview = (ImageView) convertView
					.findViewById(R.id.list_image);
			holder.btn_edit=(Button)convertView.findViewById(R.id.btn_event_edit);
			
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		JSONObject temp = null;
		
	
		try {
			temp = (JSONObject) values.get(position);
			String title=temp.get("event_title").toString();
			//String Ctitle = title.substring(0, 10);
			holder.textview.setText(title);
			String esdt=temp.get("event_start_date_time").toString();
			String eve_start_date=Constants.formatteddate(esdt);
			holder.textview1.setText(eve_start_date);
		/*	String eedt=temp.get("event_end_date_time").toString();
			String eve_end_date=Constants.formatteddate(eedt);
			holder.textview2.setText(eve_end_date); */
			holder.textview2.setText(temp.get("vanue_name").toString());
			String image_url="http://rails2.swapclone.com/eventdemo/public/data/thumb/event/";
			String image_name=temp.get("event_logo").toString();
			String image=image_url+image_name;
			
			Log.e("Image",image);
			Log.e("Title",temp.get("event_title").toString());
			Log.e("Start_date_time",temp.get("event_start_date_time").toString());
			Log.e("End_date_time",temp.get("event_end_date_time").toString());
			event_id=temp.get("id").toString();
			Log.e("Event ID",event_id);
			
			Log.e("login IDDDD",Constants.id);
			user_id=temp.get("user_id").toString();
			Log.e("USER IDDDDD",user_id);
		//	Log.e("Variable",temp.get("active").toString());
		//	UrlImageViewHelper.setUrlDrawable(holder.imageview,temp.get("tasks_user_image").toString());
			UrlImageViewHelper.setUrlDrawable(holder.imageview,image);

			convertView.setId(position);
			
			uu_id=values.getJSONObject(position).getString("user_id");
			if(Constants.id.equals(uu_id))
			{
				holder.btn_edit.setOnClickListener(new OnClickListener() 
				{

					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						try 
						{
							
							ee_id=values.getJSONObject(position).getString("id");
							Log.e("EVENT ID",ee_id);
							Intent i =new Intent(context,Edit_Event.class);
							i.putExtra("event_id", ee_id);
							context.startActivity(i);
						}
						catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
					
				
				});
		
			
		}
			
			else
			{
				holder.btn_edit.setVisibility(View.GONE);
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return convertView;
	}
		
	
	private class ViewHolder {
		public TextView textview;
		public TextView textview1;
		public TextView textview2;
		public ImageView imageview;
		public Button btn_edit;
		
	}
	
	

}


/*
 * 
 * String aString ="123456789"; String cutString = aString.substring(0, 4);
 */



