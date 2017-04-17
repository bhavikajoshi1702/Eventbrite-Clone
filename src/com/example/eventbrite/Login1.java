package com.example.eventbrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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


import com.example.eventbrite.R;
import com.example.eventbrite.Constants;

import com.example.eventbrite.ExecptionHandler;

import com.example.eventbrite.EventHome;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;



import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.ViewGroup;
// import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;


public class Login1 extends SherlockFragment
{
	
	private EditText email;
	private EditText password;
	private LoginAsync loginaync;
	private ProgressDialog loading;
	Button forgot_password;
	public static String user_id, user_password;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		Log.e("oncreateview","construct");
		View rootView = inflater.inflate(R.layout.activity_login, container, false);
		forgot_password=(Button)rootView.findViewById(R.id.button_forgot);
		email=(EditText)rootView.findViewById(R.id.username);
		password=(EditText)rootView.findViewById(R.id.password);
		((Button)rootView.findViewById(R.id.button_login)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				buttonClick();
			}
		});	
		
		forgot_password.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity().getApplicationContext(),Forgot_password.class));
				
			}
		});
		return rootView;
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_login);
		
	}
	
	private void buttonClick() {
		Boolean isvalid = true;
		EditText temp = null;
		if (TextUtils.isEmpty(email.getText().toString())) {
			email.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = email;
		}
		if (!emailValidator(email.getText().toString())) {
			email.setError(getResources().getText(R.string.invalid_email));
			isvalid = false;
			temp = email;
		}
		if (TextUtils.isEmpty(password.getText().toString())) {
			password.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = email;
		}

		if (isvalid) {
			loginaync = new LoginAsync();
			loginaync.execute((Void) null);
		} else {
			temp.setFocusable(true);
		}

	}
	
	public boolean emailValidator(String email)
	{
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern=Pattern.compile(EMAIL_PATTERN);
		matcher=pattern.matcher(email);
		return matcher.matches();
		}
	
	public class LoginAsync extends AsyncTask<Void, Void, Object>
	{
		
		
		protected void onPreExecute()
		{
			loading=new ProgressDialog(getActivity());
			loading.setMessage(getResources().getString(R.string.login_loading));
			loading.setIndeterminate(true);
			loading.setCancelable(true);
			loading.show();
		}

		@Override
		protected Object doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/post_login");
		//	TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
		//	final String android_id =  mngr.getDeviceId();
		//	Constants.IMEI = android_id;
			//String imeis = android_id;
			
			try 
			{
				user_id=email.getText().toString();
				user_password=password.getText().toString();
				Log.e("UUUUUUUUU_iiiddddd",user_id);
				Log.e("Passsssword",user_password);
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("user_email", email.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("user_password", password.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("imei", Constants.IMEI));
				Log.e("test", nameValuePairs.toString());
				
			//	String tep = email.getText().toString();
			//	String tep2 = password.getText().toString();
			//	String tep3=Constants.IMEI;
			//	Log.e("username",tep);
			//	Log.e("password",tep2);
			//	Log.e("IMEI NO",tep3);
				
			//	nameValuePairs.add(new BasicNameValuePair("id", Constants.id));
				
				//Log.e("imei", Constants.IMEI);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse response = httpclient.execute(httppost);
				
				String response1 = EntityUtils.toString(response.getEntity());
				Log.e("test",response1);
				JSONObject json=new JSONObject(response1);
				//return new JSONObject(response1);
				if (json.getString("status").equals("1")) 
				{
				//	Constants.id = json.get("guid").toString();
					
					
					Constants.id = json.getString("msg");
					
					
				//	Constants. = new JSONObject(responce4);
					
					Log.e("ID",Constants.id);
					//return true;
					return new JSONObject(response1);
					
				} 
				else 
				{
					Log.e("Wrong","Wrong");
				//	Toast.makeText(getActivity(), "Invalid user/password combination", Toast.LENGTH_SHORT).show();
					
					return false;
				}
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
			loading.dismiss();
			if(json instanceof JSONObject)
			{
				try {
					if(((JSONObject) json).getString("status").equals("1"))
					{
						Constants.id = ((JSONObject) json)
								.getString("msg");
						 Log.e("success","success");
						 EventHome eve = (EventHome) getActivity();
							eve.mPlanetTitles = new String[] { "Home", "My Events","Profile","Create an Event","Organizer Profile","Change Password","Logout" };
							ArrayList<String> lst = new ArrayList<String>();
							lst.addAll(Arrays.asList(eve.mPlanetTitles));
							eve.adapter.clear();
							eve.adapter.addAll(lst);
							eve.adapter.notifyDataSetChanged();
							eve.selectItem(0);
						
						 
					//	Intent dashIntent = new Intent(getApplicationContext(),	EventHome.class);
					//	dashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
					//	startActivity(dashIntent);
					//	finish();
					}
					else
					{
						Log.e("Invalid user/password combination","Invalid user/password combination");
						new AlertDialog.Builder(getActivity())
						.setTitle("")
						.setMessage("Invalid user/password combination")
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
			/*else {
				ExecptionHandler.register(getActivity(), (Exception) json);
			}*/
			
		}
	
		
	}
	
	
}