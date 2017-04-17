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

public class Dataadapter_organizer extends BaseAdapter
{
	public static String event_id;
	public static String user_id;
	private final Context context;
	public JSONArray values;
	
	public static String or_id;
	

	public Dataadapter_organizer(Context context, int _resource, JSONArray values) 
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
			convertView = inflater.inflate(R.layout.list_row_organizer, null);
			holder.textview = (TextView) convertView
					.findViewById(R.id.event_name);
			holder.textview1 = (TextView) convertView
					.findViewById(R.id.event_description);
			holder.textview2 = (TextView) convertView
					.findViewById(R.id.event_date_time);
			holder.imageview = (ImageView) convertView
					.findViewById(R.id.list_image);
			holder.btn_edit=(ImageButton)convertView.findViewById(R.id.btn_event_edit);
			holder.textview2.setVisibility(View.GONE);
			
			
			
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		JSONObject temp = null;
		
	
		try {
			temp = (JSONObject) values.get(position);
			String title=temp.get("name").toString();
			//String Ctitle = title.substring(0, 10);
			holder.textview.setText(title);
			String esdt=temp.get("description").toString();
			
			holder.textview1.setText(esdt);
			
			String image_url="http://rails2.swapclone.com/eventdemo/public/data/thumb/org/";
			String image_name=temp.get("org_logo").toString();
			String image=image_url+image_name;
			
			Log.e("Image",image);
			Log.e("Title",temp.get("name").toString());
			Log.e("Start_date_time",temp.get("description").toString());
		
			event_id=temp.get("id").toString();
			Log.e("Event ID",event_id);
			
			Log.e("login IDDDD",Constants.id);
			user_id=temp.get("user_id").toString();
			Log.e("USER IDDDDD",user_id);
		//	Log.e("Variable",temp.get("active").toString());
		//	UrlImageViewHelper.setUrlDrawable(holder.imageview,temp.get("tasks_user_image").toString());
			UrlImageViewHelper.setUrlDrawable(holder.imageview,image);

			convertView.setId(position);
			
		
				holder.btn_edit.setOnClickListener(new OnClickListener() 
				{

					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						try 
						{
							
							or_id=values.getJSONObject(position).getString("id");
							Log.e("EVENT ID",or_id);
							Intent i =new Intent(context,Edit_Organizer.class);
							i.putExtra("org_id", or_id);
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
		public ImageButton btn_edit;
		
	}
	
	

}


/*
 * 
 * String aString ="123456789"; String cutString = aString.substring(0, 4);
 */



