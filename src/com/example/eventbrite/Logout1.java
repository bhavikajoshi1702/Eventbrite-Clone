package com.example.eventbrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.actionbarsherlock.app.SherlockFragment;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import com.example.eventbrite.R;
import com.example.eventbrite.Constants;
import com.example.eventbrite.ExecptionHandler;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Logout1 extends SherlockFragment
{
	private OnClickListener listner;
	private ProgressDialog loading;
	private LogoutAsync logoutasync;
	// private static final int RESULT_CLOSE_ALL = 0;

	public Logout1()
	{
		
		listner=new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				(new AlertDialog.Builder(getActivity()))
				.setMessage(R.string.are_you_sure)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() 
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						// TODO Auto-generated method stub
						logoutasync = new LogoutAsync();
						logoutasync.execute((Void) null);
						
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						
			}
				}).show();
			}
		};
		

		
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_logout, container, false);
		((Button)rootView.findViewById(R.id.logout)).setOnClickListener(listner);
		TextView user_email=(TextView)rootView.findViewById(R.id.user_email);
		user_email.setText(Login1.user_id);
		return rootView;
	}
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	//	settitle();
		
	}
	

	/*public void settitle()
	{
		TextView txt=(TextView)rootView.findViewById(R.id.user_email);
		
		

	}*/
	
	
	private class LogoutAsync extends AsyncTask<Void, Void, Object> 
	{
		
			protected void onPreExecute()
			{
				loading = new ProgressDialog(getActivity());
				loading.setMessage(getResources().getString(R.string.logout));
				loading.setIndeterminate(true);
				loading.setCancelable(true);
				loading.show();
			}

			@Override
			protected Object doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(Constants.BASE_URL	+ "mobile_logins/mobile_logout");
				try 
				{
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("id",Constants.id));
					nameValuePairs.add(new BasicNameValuePair("imei",Constants.IMEI));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					String _response = EntityUtils.toString(response.getEntity());
					JSONObject json=new JSONObject(_response);
				//	httpclient.getConnectionManager().shutdown();
					Log.e("test", _response);
					if (json.getString("status").equals("1")) 
					{
						//return true;
						return new JSONObject(_response);
					} 
					else 
					{
						return false;
					}
					
					//return new JSONObject(_response);
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
					if (json instanceof JSONObject) 
					{
						
						EventHome eve = (EventHome) getActivity();
						eve.mPlanetTitles = new String[] { "Home", "Login","Signup" };
						Constants.id="0";
						ArrayList<String> lst = new ArrayList<String>();
						lst.addAll(Arrays.asList(eve.mPlanetTitles));
						eve.adapter.clear();
						eve.adapter.addAll(lst); 
						eve.adapter.notifyDataSetChanged();
						eve.selectItem(0);
				//		setResult(RESULT_CLOSE_ALL);
				//		startActivity(new Intent(logout.this, Login.class));
				//		finish();
					} 
					else 
					{
						ExecptionHandler.register(getActivity(), (Exception) json);
					}	
			
				}
			
	
	
}
}

