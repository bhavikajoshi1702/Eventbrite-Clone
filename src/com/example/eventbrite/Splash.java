package com.example.eventbrite;

import com.example.eventbrite.Constants;
import com.example.eventbrite.Splash;
import com.example.eventbrite.Dashboard;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;

public class Splash extends Activity
{
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		final String android_id = Secure.getString(Splash.this.getContentResolver(), Secure.ANDROID_ID);
		Constants.IMEI = android_id;
		(new Handler()).post(new Runnable() 
		{
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				if (!fetchuser()) 
				{
					finish();
					Intent intent = new Intent(getApplicationContext(), EventHome.class);
					startActivity(intent);
				} 
				else 
				{
					finish();
					Intent intent = new Intent(getApplicationContext(), Dashboard.class);
					startActivity(intent);
				}
				
			}
		});
		
	}
	
	private Boolean fetchuser() {
		/*Cursor cursor = DatabaseHelper.getInstance(Splash.this).executeQuery(
				"select * from `user`");
		if (null != cursor) {
			while (cursor.moveToNext()) {
				//Log.e("app", cursor.getString(cursor.getColumnIndex("email")));
				Constants.USER_ID = cursor.getString(cursor
						.getColumnIndex("user_id"));
				return true;
			}
		}*/
		return false;
	}

	
	
}
