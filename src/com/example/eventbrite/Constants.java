package com.example.eventbrite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Locale;




// needed for 273
					//needed for 273
					//needed for 273// needed for 273
					//needed for 273
					//needed for 273// needed for 273
					//needed for 273
					//needed for 273// needed for 273
					//needed for 273
					//needed for 273// needed for 273
					//needed for 273
					//needed for 273// needed for 273
					//needed for 273
					//needed for 273// needed for 273
					//needed for 273
					//needed for 273// needed for 273
					//needed for 273
					//needed for 273

//imag_uploading_path:  http://rails2.swapclone.com/eventdemo/public/data/thumb/event/logo_0221d98510_banner1.jpg
public final class  Constants
{
	// URL = http://halfevent.com/
	//public static final String BASE_URL = "foodcoin.mybluemix.net/";     added by navneet
	public static final String BASE_URL = "http://rals2.clone.com/";      
	
	
	public static String id="0";
	public static String IMEI;
	
	
	public static String formatteddate(String strdate)
	{
		try {
			Date tempDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.US).parse(strdate);
			DateFormat df = new SimpleDateFormat("MMM,dd yyyy",Locale.US);
			return df.format(tempDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	
	public static String formatteddate1(String strdate)
	{
		try {
			Date tempDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.US).parse(strdate);
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
			return df.format(tempDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	public static String formatteddate2(String strdate)
	{
		try {
			Date tempDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.US).parse(strdate);
			DateFormat df = new SimpleDateFormat("hh:mm",Locale.US);
			return df.format(tempDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
}