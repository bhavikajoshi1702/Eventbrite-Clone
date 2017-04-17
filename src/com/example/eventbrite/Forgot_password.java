package com.example.eventbrite;

import java.util.ArrayList;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Forgot_password extends Activity
{
	private EditText email;
	private Button send;
	private ForgotPassword forgotpassword;
	private ProgressDialog loading;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_pwd);
		email=(EditText)findViewById(R.id.user_email);
		send=(Button)findViewById(R.id.button_send);
		

		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				buttonClick();
			}
		});
	
	}
	
	private void buttonClick() 
	{
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
		if (isvalid)
		{
			forgotpassword = new ForgotPassword();
			forgotpassword.execute((Void) null);
		} else 
		{
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
	
	
	public class ForgotPassword extends AsyncTask<Void, Void, Object>
	{
		
		
		protected void onPreExecute()
		{
			loading=new ProgressDialog(Forgot_password.this);
			loading.setMessage(getResources().getString(R.string.login_loading));
			loading.setIndeterminate(true);
			loading.setCancelable(true);
			loading.show();
		}

		@Override
		protected Object doInBackground(Void... params) 
		{
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/forgot_password");
			
			try 
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				String tep = email.getText().toString();
				
				Log.e("username",tep);
				
				nameValuePairs.add(new BasicNameValuePair("user_email", email.getText().toString()));
			
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse response = httpclient.execute(httppost);
				String response1 = EntityUtils.toString(response.getEntity());
				Log.e("test",response1);
				return new JSONObject(response1);
				
				
				
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
						Intent dashIntent = new Intent(getApplicationContext(),
								EventHome.class);
						dashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(dashIntent);
						finish();
					}
					else
					{
						new AlertDialog.Builder(Forgot_password.this)
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
			else {
				ExecptionHandler.register(Forgot_password.this, (Exception) json);
			}
		}
	
	}

	
}