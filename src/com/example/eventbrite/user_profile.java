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
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragment;


import com.example.eventbrite.ExecptionHandler;




@SuppressLint("ValidFragment")
class user_profile extends SherlockFragment
{
	
	JSONObject json;
	private String Prefix;
	private String Fname;
	private String Lname;
	private String Hphone;
	private String Cphone;
	private String Email;
	
	private EditText prefix;
	private EditText firstname;
	private EditText lastname;
	private EditText homephone;
	private EditText cellphone;
	private EditText email;

	private ProgressDialog loading;
	JSONArray info = null;
	JSONObject jsobj;
	
	
	boolean asyncrun;
	private ProfileAsync profileasync=null;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{	
		Log.e("profile_oncreateview","profile_construct");
		View rootView = inflater.inflate(R.layout.user_profile, container, false);
		PreUserTask preusertask = new PreUserTask();
		preusertask.execute((Void) null);

		prefix=(EditText)rootView.findViewById(R.id.editText_prefix);
		firstname=(EditText)rootView.findViewById(R.id.editText_firstname);
		lastname=(EditText)rootView.findViewById(R.id.editText_lastname);
		homephone=(EditText)rootView.findViewById(R.id.editText_homephone);
		cellphone=(EditText)rootView.findViewById(R.id.editText_cellphone);
		email=(EditText)rootView.findViewById(R.id.editText_email);
		
	//	PreUserTask preusertask = new PreUserTask();
	//		preusertask.execute((Void) null);
		
		((Button)rootView.findViewById(R.id.button_save)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setprofile();
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
	
	public void setprofile() {
		if (profileasync != null) {
			return;
		}
		prefix.setError(null);
		firstname.setError(null);
		lastname.setError(null);
		homephone.setError(null);
		cellphone.setError(null);
		email.setError(null);
		
		
		// Store values at the time of the login attempt.
		
		
		Prefix=prefix.getText().toString();
		Fname=firstname.getText().toString();
		Lname=lastname.getText().toString();
		Hphone=homephone.getText().toString();
		Cphone=cellphone.getText().toString();
		Email=email.getText().toString();
		
		
		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(Prefix)) {
			prefix.setError(getString(R.string.error_field_required));
			focusView = prefix;
			cancel = true;
		}

		if (TextUtils.isEmpty(Fname)) {
			firstname.setError(getString(R.string.error_field_required));
			focusView = firstname;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(Email)) {
			email.setError(getString(R.string.error_field_required));
			focusView = email;
			cancel = true;
		} else if (!Email.contains("@")) {
			email.setError(getString(R.string.error_invalid_email));
			focusView = email;
			cancel = true;
		}

		if (TextUtils.isEmpty(Lname)) {
			lastname.setError(getString(R.string.error_field_required));
			focusView = lastname;
			cancel = true;
		}

		if (TextUtils.isEmpty(Cphone)) {
			cellphone.setError(getString(R.string.error_field_required));
			focusView = cellphone;
			cancel = true;
		}

		if (TextUtils.isEmpty(Hphone)) {
			homephone.setError(getString(R.string.error_field_required));
			focusView = homephone;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			// mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			// showProgress(true);
		profileasync = new ProfileAsync();
		profileasync.execute((Void) null);
		}
		
		
	}

	
	
	class ProfileAsync extends AsyncTask<Void, Void, Object>
	{
		
		private ProgressDialog pdia;
		protected void onPreExecute() {
				super.onPreExecute();
				pdia = new ProgressDialog(getActivity());
				pdia.setMessage("Loading...");
				pdia.show();
			
		}


		@Override
		protected Object doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/save_user");
			
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("id",	Constants.id));
				nameValuePairs.add(new BasicNameValuePair("imei", Constants.IMEI));
				nameValuePairs.add(new BasicNameValuePair("prefix", prefix.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("firstname", firstname.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("lastname", lastname.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("homephone", homephone.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("cellphone", cellphone.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
				Log.e("test", nameValuePairs.toString());





				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				String _response = EntityUtils.toString(response.getEntity());
				Log.e("profile_test",_response); 
				JSONObject jsobj = new JSONObject(_response);
			//info = jsobj.getJSONArray("user_info");
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
			Log.e("profile_onpostexecute","profile_postexecute");
			String post=jsobj.toString();
			Log.e("profile_rese",post);
			Log.e("profile_onpostexecute1","profile_postexecute");
			
			 if (jsobj instanceof JSONObject) 
			 {
				 try 
				 {
						if(((JSONObject) jsobj).getString("status").equals("1"))
						{
							
							 Log.e("success","success");
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
				 catch (JSONException e) 
				 {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
		}
		}
	}
	
	
	
	class PreUserTask extends AsyncTask<Void, Void, Object>
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
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/myinfo");

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
			
		//	String postexecute;
			Log.e("onpostexecute","postexecute");
			String post=json.toString();
			 Log.e("rese",post);
			 Log.e("onpostexecute1","postexecute");
			 if (json instanceof JSONObject) 
			 {
				 try 
					{	
					 	
						prefix.setText(jsobj.getJSONObject("user_info").get("prefix").toString().replace("null", " "));
						firstname.setText(jsobj.getJSONObject("user_info").get("first_name").toString().replace("null", " "));
						lastname.setText(jsobj.getJSONObject("user_info").get("last_name").toString().replace("null", " "));
						homephone.setText(jsobj.getJSONObject("user_info").get("home_phone").toString().replace("null"," "));
						cellphone.setText(jsobj.getJSONObject("user_info").get("cell_phone").toString().replace("null"," "));
						email.setText(jsobj.getJSONObject("user_info").get("email").toString().replace("null"," "));




					}
					catch (JSONException e) 
					{
						e.printStackTrace();
					}
					
			}
			else 
			{
					ExecptionHandler.register(getActivity(), (Exception) json);
			}	
		}
		
	
}
}
		
		
		
		
	

	
