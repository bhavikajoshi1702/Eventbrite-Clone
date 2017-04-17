package com.example.eventbrite;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.example.eventbrite.Login1.LoginAsync;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Change_Password extends SherlockFragment
{
	private EditText old_password, new_password, confirm_password;
	private ChangePassword changepassword;
	private ProgressDialog loading;
	Button save;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		Log.e("oncreateview","construct");
		View rootView = inflater.inflate(R.layout.change_password, container, false);
		old_password=(EditText)rootView.findViewById(R.id.old_pwd);
		new_password=(EditText)rootView.findViewById(R.id.new_pwd);
		confirm_password=(EditText)rootView.findViewById(R.id.confirm_pwd);
		save=(Button)rootView.findViewById(R.id.btn_save);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				buttonClick();
			}
		});	
		
		
		return rootView;
	}
	private void buttonClick() {
		Boolean isvalid = true;
		EditText temp = null;
		if (TextUtils.isEmpty(old_password.getText().toString())) {
			old_password.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = old_password;
		}
		
		if (TextUtils.isEmpty(new_password.getText().toString())) {
			new_password.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = new_password;
		}
		if (TextUtils.isEmpty(confirm_password.getText().toString())) {
			confirm_password.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = confirm_password;
		}

		if (isvalid) 
		{
			check();
		} 
		else {
			temp.setFocusable(true);
		}

	}
	
	private void check()
	{
		String pass=Login1.user_password;
		String old_pass=old_password.getText().toString();
		String new_pass=new_password.getText().toString();
		String confirm_pass=confirm_password.getText().toString();
		
		if(pass.equals(old_pass))
		{
			if(new_pass.equals(confirm_pass))
			{
				changepassword = new ChangePassword();
				changepassword.execute((Void) null);
			}
			else 
			{
				Toast.makeText(getActivity(), "Not Matched...", Toast.LENGTH_SHORT).show();
				Log.e("Both pass r not equal","both pass are not equal");
			}
		}
		else
		{
			Toast.makeText(getActivity(), "Please, Enter correct Password...", Toast.LENGTH_SHORT).show();
			Log.e("Entered old pass is Incorrect..","Old pass is incorrect");
		}
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_login);
		
	}
	
	
	public class ChangePassword extends AsyncTask<Void, Void, Object>
	{
		
		
		protected void onPreExecute()
		{
			loading=new ProgressDialog(getActivity());
			loading.setMessage(getResources().getString(R.string.loading));
			loading.setIndeterminate(true);
			loading.setCancelable(true);
			loading.show();
		}

		@Override
		protected Object doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/set_new_password");
	
			
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("id", Constants.id));
			//	nameValuePairs.add(new BasicNameValuePair("old_password", old_password.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("user_password", new_password.getText().toString()));
				Log.e("test", nameValuePairs.toString());
				
		
		
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse response = httpclient.execute(httppost);
				
				String response1 = EntityUtils.toString(response.getEntity());
				Log.e("test",response1);
				JSONObject json=new JSONObject(response1);
				//return new JSONObject(response1);
				if (json.getString("status").equals("1")) 
				{
				
					return new JSONObject(response1);
					
				} 
				else 
				{
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
					
						 Log.e("success","success");
					 EventHome eve = (EventHome) getActivity();
							eve.mPlanetTitles = new String[] { "Home", "My Events","Profile","Create an Event","Logout","Change password" };
							ArrayList<String> lst = new ArrayList<String>();
							lst.addAll(Arrays.asList(eve.mPlanetTitles));
							eve.adapter.clear();
							eve.adapter.addAll(lst);
							eve.adapter.notifyDataSetChanged();
							eve.selectItem(0);
							Toast.makeText(getActivity(), "Password has been changed successfully...", Toast.LENGTH_SHORT).show(); 
							
						
						 
					//	Intent dashIntent = new Intent(getApplicationContext(),	EventHome.class);
					//	dashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
					//	startActivity(dashIntent);
					//	finish();
					}
					else
					{
						Log.e("NOt success","Not success");
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