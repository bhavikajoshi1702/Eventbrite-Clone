package com.example.eventbrite;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import com.example.eventbrite.R;
//import com.example.eventbrite.DownloadTask;
//import com.example.eventbrite.ParserTask;




import java.util.Calendar;


import android.app.Activity;
import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;



import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;


import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.eventbrite.Constants;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;


import android.app.ProgressDialog;

import android.content.Intent;
import android.database.Cursor;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.Spinner;

import android.widget.EditText;
import android.widget.ImageView;

public class Create_Event extends SherlockFragment implements OnClickListener
{
	//for image_uploading
	
	protected static final int SELECT_PICTURE = 1;
	int serverResponseCode = 0;
	ProgressDialog dialog = null;
	private String selectedImagePath;
	
	
	PlacesTask placesTask;
	ParserTask parserTask;
	
	InputStream is=null;
	String result=null;
	String line=null;	
	JSONObject jsobj1;
	JSONObject json;
	String img_name;
	
	String start_d, start_t,end_d,end_t;
	Calendar startDate;
	
	AutoCompleteTextView atvPlaces;
	private String organization;
	private long organizer_id;
	private String host="not needed";
	private String id=Constants.id;
	private ProgressDialog loading;
	private CreateEvent createevent=null;
	private String logo ="logo_462f201b24_b23.png";
	private String start_date_time, end_date_time;
	Button b_start_calender, b_end_calender, b_start_timepicker, b_end_timepicker;
	EditText start_date, start_time, end_date, end_time;
	JSONArray orr = null;
	JSONArray orr11 = null;
	private String oo;
	private String oon;
	
	EditText Event_title;
	EditText Event_venue;
	AutoCompleteTextView Street_address;

	EditText Event_description;
	private int start_year, start_month, start_day, start_hour, start_minute;
	private int end_year, end_month, end_day, end_hour, end_minute;
	ImageView event_logo;
	Button upload;
	boolean asyncrun;
	Spinner sp;
	String s_date, e_date, ss_date, ss_time, ee_date, ee_time;
	 double d,d1;
	 private GoogleMap mMap;
	//For google map
	
//	AutoCompleteTextView atvPlaces;
	String selectedPath1 = "NONE";
	private static final int SELECT_FILE1 = 1;
	
	JSONArray img_data = null;
	
	JSONObject jsobje;
	GoogleMap googleMap;
	
	final int PLACES=0;
	final int PLACES_DETAILS=1;	
	private Organizer organizer=null;
	
	HashMap<String,String> map_values = new HashMap<String,String>();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{		
		if (loading != null)
		{
			if(loading.isShowing())
			{
				loading.dismiss();
			}
		}
		
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.create_event, container, false);
	
		Event_title=(EditText)rootView.findViewById(R.id.event_title);
		Event_venue=(EditText)rootView.findViewById(R.id.event_venue);
		atvPlaces=(AutoCompleteTextView)rootView.findViewById(R.id.atv_places);
		atvPlaces.setThreshold(1);	
		
		
		
		
			atvPlaces.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
				placesTask = new PlacesTask();				
				placesTask.execute(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub				
			}
		});	
		
		
		b_start_calender=(Button)rootView.findViewById(R.id.btn_start_calender);
		b_start_timepicker=(Button)rootView.findViewById(R.id.btn_start_timepicker);
		b_end_calender=(Button)rootView.findViewById(R.id.btn_end_calender);
		b_end_timepicker=(Button)rootView.findViewById(R.id.btn_end_timepicker);
		
		start_date=(EditText)rootView.findViewById(R.id.start_date);
		start_time=(EditText)rootView.findViewById(R.id.start_time);
		end_date=(EditText)rootView.findViewById(R.id.end_date);
		end_time=(EditText)rootView.findViewById(R.id.end_time);
	
		b_start_calender.setOnClickListener(this);
		b_start_timepicker.setOnClickListener(this);
		b_end_calender.setOnClickListener(this);
		b_end_timepicker.setOnClickListener(this);
		
		Event_description=(EditText)rootView.findViewById(R.id.event_description);
		event_logo=(ImageView)rootView.findViewById(R.id.imageView1);
		upload=(Button)rootView.findViewById(R.id.upload_button);
		sp=(Spinner)rootView.findViewById(R.id.spinner1);
		
		
		
		organizer = new Organizer();
		organizer.execute((Void) null);
		
		event_logo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("222","222_b_btnclick");
            	
				 Intent intent = new Intent();
                 intent.setType("image/*");
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
			}
		});
		
		
		upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("333","333_uploading");
				// TODO Auto-generated method stub
				 dialog = ProgressDialog.show(getActivity(), "", "Uploading file...", true);
	                
	                new Thread(new Runnable() {
	                        public void run() {
	                             getActivity().runOnUiThread(new Runnable() {
	                                    public void run() {
	                                    	
	                                    }
	                                });                  
	                             try 
	                             {
									uploadFile(selectedImagePath);
								} catch (Exception e) 
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                             
	                                                     
	                        }
	                      }).start();
				
			}
		});

		
		Button save_btn=(Button)rootView.findViewById(R.id.button_save);
		save_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				create_event();
			}
		});
		
						return rootView;		
	}
	
	
	  public void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
	    	
	    	Log.e("0000_OnActivity","888888888");
	    	
	        if (resultCode == Activity.RESULT_OK) {
	            if (requestCode == SELECT_PICTURE) {
	                Uri selectedImageUri = data.getData();
	                selectedImagePath = getPath(selectedImageUri);
	                System.out.println("Image Path : " + selectedImagePath);
	                // img.setImageURI(selectedImageUri);
	                // uploadFile(selectedImagePath);
	            }
	        }
	        
	        
	    }
	  
	  public String getPath(Uri uri)
	    {
		  
	    	Log.e("0101010","Getpath");
	    	
	        String[] projection = { MediaStore.Images.Media.DATA };
	        Cursor cursor=getActivity().getContentResolver().query(uri, projection, null, null, null);
	       
	     
	     //  Cursor cursor = managedQuery(uri, projection, null, null, null);
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
	    }
	    
	  public JSONObject parseJSON(String response){

		    try {
		        return new JSONObject(response);
		    } catch (JSONException e) {
		        e.printStackTrace();
		    }
		    return null;
		}
	  
	    
	  public void uploadFile(String sourceFileUri) throws Exception
	    {
	    	
	    	
	    	Log.e("4444","44_meth_execute");
	    	
	    	String fileName = sourceFileUri;
	    	Log.e("image_name_path",fileName);
	    	
	    	
	    	File file1;
	    	if (fileName.equals("NONE"))
				file1 = null;
			else
				file1 = new File(fileName);
	    	
	    	
	    	HttpClient httpClient = new DefaultHttpClient();
	    	
	    	 
	            	
	    	
	  //     HttpPost postRequest = new HttpPost("http://www.hireiphone.com/gofundme/upload.php");
	       
	  	HttpPost postRequest = new HttpPost("http://rails2.swapclone.com/eventdemo/upload.php");
	    	
	        FileBody bin1 = null;
			if (file1 != null) {
				bin1 = new FileBody(file1);
			}
	        
	        Log.e("5555","coonection_success");
	    	
	    MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	      //  MultipartEntity reqEntity = new MultipartEntity();
	       try 
	       {
	    	   
	    	   reqEntity.addPart("uploaded_file", bin1);
	    	   postRequest.setEntity(reqEntity);
	    	   HttpResponse response = httpClient.execute(postRequest);
	    	   
	    	   Log.e("666_upload","imageupload");
	       	
	    	   String response1 = EntityUtils.toString(response.getEntity());
	    	   Log.e("IMAGE_RESPONSE",response1);
	    	  
	    	   
	    	   
	    	 
	    		   jsobje = parseJSON(response1); 
	    		  img_name =jsobje.getString("name_old");
	    		   Log.e("Image name",img_name);
	    		 
	    	   
	    	 
	    	 
	    	
	    	   Log.e("777_seccesss","777");
	    	  
	  
	    	 
	    //	 img_data = jsobj.getJSONArray("live_events");
	    	
	    	 
	 
				 
	    	/*   String image_url="http://rails2.swapclone.com/eventdemo/public/data/thumb/event/";
				String image_name=img_name;
				String image=image_url+image_name;
				UrlImageViewHelper.setUrlDrawable(event_logo,image);  */

				 dialog.dismiss();
	       	
		
	       } 
	       
	       catch (UnsupportedEncodingException e) 
	       {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    }
	  /*
	  public void onResume()
	  {
		  super.onResume();
		 // getGPS();
		  setUpMapIfNeeded(d,d1);
	  }
	  
	  
	  private void setUpMapIfNeeded(double lat, double lon) 
	  {
	  try {
	      String location = atvPlaces.getText().toString();
	      Geocoder gc = new Geocoder(getActivity());
	      List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects
	      
	      List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
	      for(Address a : addresses){
	          if(a.hasLatitude() && a.hasLongitude()){
	          	d = a.getLatitude();
	          	d1 = a.getLongitude();
	          	String s = Double.toString(d);
	          	String s1 = Double.toString(d1);
	          	
	          	Log.e("Latitude", s);
	          	Log.e("Longitude", s1);
	          	ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
	          }  
	       
	      }  
	  } catch (IOException e) {
	       // handle the exception
	  }




	      if (mMap != null) {
	          return;
	      }
	      mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
	      if (mMap == null) {
	          return;
	      }

	      double latitudes = d;
	      double longitudes =d1;
	      
	    
	      // create marker
	      MarkerOptions marker = new MarkerOptions().position(new LatLng(latitudes, longitudes)).title("Hello Maps ");

	      // adding marker
	      mMap.addMarker(marker);
	  }

	*/
	
	/** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);                

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url 
                urlConnection.connect();

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }
                
                data = sb.toString();

                br.close();

        }catch(Exception e){
                Log.d("Exception while downloading url", e.toString());
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }
        return data;
     }	
    
    
    private class PlacesTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... place) {
			// For storing data from web service
			String data = "";
			
			// Obtain browser key from https://code.google.com/apis/console
		//	String key = "key=AIzaSyCaC4sBzEZNQsVqrdyWTCY3HO8x2U6Br04";
			String key = "key=AIzaSyCaC4sBzEZNQsVqrdyWTCY3HO8x2U6Br04";
			
			String input="";
			
			try {
				input = "input=" + URLEncoder.encode(place[0], "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}		
			
			
			// place type to be searched
			String types = "types=geocode";
			
			// Sensor enabled
			String sensor = "sensor=false";			
			
			// Building the parameters to the web service
			String parameters = input+"&"+types+"&"+sensor+"&"+key;
			
			// Output format
			String output = "json";
			
			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;
	
			try{
				// Fetching the data from web service in background
				data = downloadUrl(url);
			}catch(Exception e){
                Log.d("Background Task",e.toString());
			}
			return data;		
		}
		
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);
			
			// Creating ParserTask
			parserTask = new ParserTask();
			
			// Starting Parsing the JSON string returned by Web Service
			parserTask.execute(result);
		}		
	}
	
	
	/** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

    	JSONObject jObject;
    	
		@Override
		protected List<HashMap<String, String>> doInBackground(String... jsonData) {			
			
			List<HashMap<String, String>> places = null;
			
            PlaceJSONParcer placeJsonParser = new PlaceJSONParcer();
            
            try{
            	jObject = new JSONObject(jsonData[0]);
            	
            	// Getting the parsed data as a List construct
            	places = placeJsonParser.parse(jObject);

            }catch(Exception e){
            	Log.d("Exception",e.toString());
            }
            return places;
		}
		
		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {			
			
				String[] from = new String[] { "description"};
				int[] to = new int[] { android.R.id.text1 };
				
				// Creating a SimpleAdapter for the AutoCompleteTextView			
				SimpleAdapter adapter = new SimpleAdapter(getActivity(), result, android.R.layout.simple_list_item_1, from, to);				
				
				// Setting the adapter
				atvPlaces.setAdapter(adapter);
		}			
    }    
	
	
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
	
		
		if(v==b_start_calender)
		{
			final Calendar c=Calendar.getInstance();
			start_year=c.get(Calendar.YEAR);
			start_month=c.get(Calendar.MONTH);
			start_day=c.get(Calendar.DAY_OF_MONTH);
		
		DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() 
		{

		    public void onDateSet(DatePicker view, int year,
		            int monthOfYear, int dayOfMonth) 
		    {
		    	
		    	
		    	startDate = Calendar.getInstance();
		    	startDate.set(Calendar.YEAR, year);
		    	startDate.set(Calendar.MONTH, monthOfYear);
		    	startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		       start_date.setText(dayOfMonth + "-"
						+ (monthOfYear + 1) + "-" + year);
		       
		        start_d=year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
		    
		       Log.e("Start_date",startDate.toString());
		       
		       
		    }
		    
		     
		};
		DatePickerDialog d = new DatePickerDialog(getActivity(),
		         mDateSetListener, start_year, start_month, start_day);
		d.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
		d.show();
		
		
	}  
	
	if(v==b_start_timepicker)
	{
		final Calendar c = Calendar.getInstance();
		start_hour=c.get(Calendar.HOUR_OF_DAY);
		start_minute=c.get(Calendar.MINUTE);
		
		TimePickerDialog.OnTimeSetListener mTimeSetListner = new TimePickerDialog.OnTimeSetListener() 
		{
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				start_time.setText(hourOfDay + ":" + minute);
				 start_t=hourOfDay + ":" + minute + ":"+"00";
			}
		};
		TimePickerDialog t=new TimePickerDialog(getActivity(), mTimeSetListner, start_hour, start_minute,false);
		t.show();
		
	}
	
	if(v==b_end_calender)
	{
		final Calendar c=Calendar.getInstance();
		end_year=c.get(Calendar.YEAR);
		end_month=c.get(Calendar.MONTH);
		end_day=c.get(Calendar.DAY_OF_MONTH);
		
		DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() 
		{

		    public void onDateSet(DatePicker view, int year,
		            int monthOfYear, int dayOfMonth) 
		    {
		       
		    	Calendar newEndDate=Calendar.getInstance();
		    	newEndDate.set(Calendar.YEAR, year);
		    	newEndDate.set(Calendar.MONTH, monthOfYear);
		    	newEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		    	Log.e("End_date",newEndDate.toString());
		    	 end_d=year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth;
		  	
		    	
		    	if(newEndDate.before(startDate))
		    	{
		    		end_date.setText("Plz.. enter date after starting date..");
		    		
		    	}
		    	else
		    	{	
		    		end_date.setText(dayOfMonth + "-"
							+ (monthOfYear + 1) + "-" + year);	
		    	}
		    }
		};
			DatePickerDialog d = new DatePickerDialog(getActivity(),mDateSetListener, end_year, end_month, end_day);
			
			d.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
			
			d.show();	
	}  
		
	if(v==b_end_timepicker)
	{
		final Calendar c = Calendar.getInstance();
		end_hour=c.get(Calendar.HOUR_OF_DAY);
		end_minute=c.get(Calendar.MINUTE);
		
		TimePickerDialog.OnTimeSetListener mTimeSetListner = new TimePickerDialog.OnTimeSetListener() 
		{
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				end_time.setText(hourOfDay + ":" + minute);
				end_t=hourOfDay + ":" + minute + ":"+"00";
			}
		};
		TimePickerDialog t=new TimePickerDialog(getActivity(), mTimeSetListner, end_hour, end_minute,false);
		t.show();
		//     Date futureDate = new Date(new Date().getTime() + 86400000);
	}
	}
	
	
	
	
	
	
	
	
	

	public void create_event() {
	
		Boolean isvalid = true;
		EditText temp = null;
		if (TextUtils.isEmpty(Event_title.getText().toString())) {
			Event_title.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = Event_title;
		}
		if (TextUtils.isEmpty(Event_venue.getText().toString())) {
			Event_venue.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = Event_venue;
		}
		
		if (TextUtils.isEmpty(atvPlaces.getText().toString())) {
			atvPlaces.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = Street_address;
		}
		
		if (TextUtils.isEmpty(Event_description.getText().toString())) {
			Event_description.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = Event_description;
		}
		
	/*	if (TextUtils.isEmpty(start_date.getText().toString())) {
			start_date.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = start_date;
		}
		
		if (TextUtils.isEmpty(start_time.getText().toString())) {
			start_time.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = start_time;
		}
		
		if (TextUtils.isEmpty(end_date.getText().toString())) {
			end_date.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = end_date;
		}
		
		if (TextUtils.isEmpty(end_time.getText().toString())) {
			end_time.setError(getResources().getText(R.string.required));
			isvalid = false;
			temp = end_time;
		}  */
		
		// For Spinner... showToast(q.name + " unselected");
		
		if (isvalid)
		{
			
			createevent = new CreateEvent();
			createevent.execute((Void) null);
		}
		else {
			temp.setFocusable(true);
		}
		
		
		
		
		
		
	}
	
	class Organizer extends AsyncTask<Void, Void, Object>
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
			
			try 
			{
				HttpClient httpclient1 = new DefaultHttpClient();
				HttpPost httppost1 = new HttpPost(Constants.BASE_URL+ "mobile_logins/all_my_event");
				
				
				
				
				
				
				
				List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(1);
				nameValuePairs1.add(new BasicNameValuePair("id",Constants.id));  
			
				
				httppost1.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
				HttpResponse response1 = httpclient1.execute(httppost1);
				String _response1 = EntityUtils.toString(response1.getEntity());
				Log.e("test",_response1); 
				jsobj1 = new JSONObject(_response1);
				orr=jsobj1.getJSONArray("organizers");
				String orggg=orr.toString();
				Log.e("Organization",orggg);
				return jsobj1;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				Log.e("Fail 1", e.toString());
				return e;
			}
			
			
			
		
	}
		
		protected void onPostExecute( Object jsobj) 
		{
			
			pdia.dismiss();
			
			String post=jsobj.toString();
			Log.e("event_rese",post);
			
			int len=orr.length();
			
			Log.e("json_posexe",orr.toString());
			Log.e("Onpostexe_arrlength",String.valueOf(len));
			
			
			
			
			
			try {
				JSONArray JA=orr;
				JSONObject json1= null;
				final String[] host1 = new String[orr.length()]; 
				final String[] host_id=new String[orr.length()];
				
				for(int i=0; i<len;i++)
				{
				 json1=JA.getJSONObject(i);
				 host1[i]=json1.getString("name");
				 host_id[i]=json1.getString("id");
				
				}
				
				
				
				
				List<String> list=new ArrayList<String>();
				for(int i=0;i<host1.length;i++)
				{
					list.add(host1[i]+"/"+host_id[i]);
					
					
				}
				Log.e("onpostexe_list",list.toString());
				
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,list);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp.setAdapter(adapter);
				
				sp.setOnItemSelectedListener(new OnItemSelectedListener() 
				{
					
					public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long id) 
		    		{
		    			// TODO Auto-generated method stub
						
		    			organization=sp.getSelectedItem().toString();
		    			
		    			

		              
		    	
		    	
		    		
		    			
		    			
		    			
				int len=organization.length();
		    			int in=organization.indexOf("/");
		    			oon=organization.substring(0,in);
		    			Log.e("OOOOOO_NAME",oon);
		    			
		    			oo=organization.substring(in+1,len);
		    		
		    			Log.e("OO_id",oo);


		    			
		    		}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				
			}
				 catch(Exception e)
			        {
			        	Log.e("Fail 3", e.toString());
			        }
			
			
			 if (json instanceof JSONObject) 
			 {
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
		
		
		}
		
		
	
	}
	
	
	class CreateEvent extends AsyncTask<Void, Void, Object>
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
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/create_event");
			
		

			try 
			{
				
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			
				start_date_time=start_d+" "+start_t;
				end_date_time=end_d+" "+end_t;
			//	end_date_time=end_date.getText().toString() + end_time.getText().toString();
				nameValuePairs.add(new BasicNameValuePair("user_id",id));
				nameValuePairs.add(new BasicNameValuePair("event_title", Event_title.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("vanue_name", Event_venue.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("street_address", atvPlaces.getText().toString()));
				
				nameValuePairs.add(new BasicNameValuePair("organizer_id",oo));
				nameValuePairs.add(new BasicNameValuePair("event_detail", Event_description.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("organizer_host",oon));
				nameValuePairs.add(new BasicNameValuePair("host_description",host));
				nameValuePairs.add(new BasicNameValuePair("event_logo", img_name));
				nameValuePairs.add(new BasicNameValuePair("event_start_date_time",start_date_time));
				nameValuePairs.add(new BasicNameValuePair("event_end_date_time", end_date_time));  
				
			Log.e("test", nameValuePairs.toString());
				
	
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				String _response = EntityUtils.toString(response.getEntity());
				Log.e("Event_test",_response); 
				JSONObject jsobj = new JSONObject(_response);
			
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
			Log.e("event_onpostexecute","event_postexecute");
			String post=jsobj.toString();
			Log.e("event_rese",post);
			Log.e("event_onpostexecute1","event_postexecute");
			
			 if (jsobj instanceof JSONObject) 
			 {
				 
				 try {
						if(((JSONObject) jsobj).getString("status").equals("1"))
						{
							Log.e("success","success");
							 EventHome eve = (EventHome) getActivity();
								eve.mPlanetTitles = new String[] { "Home", "My Events","Profile","Create an Event","Logout" };
								ArrayList<String> lst = new ArrayList<String>();
								lst.addAll(Arrays.asList(eve.mPlanetTitles));
								eve.adapter.clear();
								eve.adapter.addAll(lst);
								eve.adapter.notifyDataSetChanged();
								eve.selectItem(1);
						}
				 }
				 catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
				
		
		}
		
	}
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.main, menu);
	    super.onCreateOptionsMenu(menu,inflater);
	}
	
   	
}

       
	
	
	
		

	
	
		
