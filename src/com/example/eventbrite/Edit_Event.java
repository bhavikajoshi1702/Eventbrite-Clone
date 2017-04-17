package com.example.eventbrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.json.JSONException;
import org.json.JSONObject;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class Edit_Event extends FragmentActivity
{
	//image upload
	protected static final int SELECT_PICTURE = 1;
	int serverResponseCode = 0;
    ProgressDialog dialog = null;
	private String selectedImagePath;
	
	AutoCompleteTextView atvPlaces;
	PlacesTask placesTask;
	ParserTask parserTask;
	private CreateEvent1 createevent1=null;
	JSONObject jsobj1;
	EditText Event_title;
	EditText Event_venue;
	EditText Street_address;
	EditText Event_description;
	String s_date, e_date, ss_date, ss_time, ee_date, ee_time;
	EditText eve_start_date, eve_start_time, eve_end_date, eve_end_time;
	Spinner sp;
	Button btn_save;
	private String id=Constants.id;
	boolean asyncrun;
	String e_id;
	ImageView event_logo;
	Button upload;
	 JSONObject jsobje;
	 String img_name;
	
	String start_d, start_t,end_d,end_t;
	
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		 Bundle b = getIntent().getExtras();
		 e_id =(String) b.get("event_id");
		 Log.e("my Event id",e_id);
		
		Log.e("Hiii form edit event..","edit event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);
		Log.e("edit_LAYout..","layout");
		PreUserTask preusertask = new PreUserTask();
		preusertask.execute((Void) null);
		 Event_title=(EditText)findViewById(R.id.event_title);
			Event_venue=(EditText)findViewById(R.id.event_venue);
		//	Street_address=(EditText)findViewById(R.id.street_address);
			event_logo=(ImageView)findViewById(R.id.imageView1);
			upload=(Button)findViewById(R.id.upload_button);
			
			atvPlaces = (AutoCompleteTextView) findViewById(R.id.atv_places);
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
			
			Event_description=(EditText)findViewById(R.id.event_description);
			eve_start_date=(EditText)findViewById(R.id.start_date);
			eve_start_time=(EditText)findViewById(R.id.start_time);
			eve_end_date=(EditText)findViewById(R.id.end_date);
			eve_end_time=(EditText)findViewById(R.id.end_time); 
			sp=(Spinner)findViewById(R.id.spinner1);
			btn_save=(Button)findViewById(R.id.button_save);
			
			
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
		            	
		            	
		                dialog = ProgressDialog.show(Edit_Event.this, "", "Uploading file...", true);
		                
		                new Thread(new Runnable() {
		                        public void run() {
		                             runOnUiThread(new Runnable() {
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
		     
			
			
			btn_save.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Log.e("Method...","meth executing...");
					// TODO Auto-generated method stub
					createevent1 = new CreateEvent1();
					createevent1.execute((Void) null);
					
					
				}

			
			});
			
			
	}
	
	 public void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
	    	
	    	Log.e("0000_OnActivity","888888888");
	    	
	        if (resultCode == RESULT_OK) {
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
	        Cursor cursor = managedQuery(uri, projection, null, null, null);
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
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
	    	   jsobje = new JSONObject(response1);
		    	  img_name= jsobje.getString("name_old");
	    	   
	    	   Log.e("777_seccesss","77777_seccesss");
	    	   dialog.dismiss();
	    	   
	    	    
		
	       } 
	       
	       catch (UnsupportedEncodingException e) 
	       {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    }
	
	
	/** A method to download json data from url for autocomplete textview */
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
    
 // Fetches all places from GooglePlaces AutoComplete Web Service
 	private class PlacesTask extends AsyncTask<String, Void, String>{

 		@Override
 		protected String doInBackground(String... place) {
 			// For storing data from web service
 			String data = "";
 			
 			// Obtain browser key from https://code.google.com/apis/console
 		
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
 				SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);				
 				
 				// Setting the adapter
 				atvPlaces.setAdapter(adapter);
 		}			
     }    
     
     @Override
 	public boolean onCreateOptionsMenu(Menu menu) {
 		// Inflate the menu; this adds items to the action bar if it is present.
 		getMenuInflater().inflate(R.menu.main, menu);
 		return true;
 	}
     
	        
	        
	        
	        class PreUserTask extends AsyncTask<Void, Void, Object>

			{

				
				private ProgressDialog pdia;
				protected void onPreExecute()
				{
						super.onPreExecute();
						pdia = new ProgressDialog(Edit_Event.this);
						pdia.setMessage("Loading...");
						pdia.show();
					
				}
				
				@Override
				protected Object doInBackground(Void... params) 
				{
					// TODO Auto-generated method stub
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/event_data");

					try 
					{
						
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
						nameValuePairs.add(new BasicNameValuePair("id",e_id));
						Log.e("namevaluepairs",nameValuePairs.toString());
						//nameValuePairs.add(new BasicNameValuePair("imei", Constants.IMEI));
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						HttpResponse response = httpclient.execute(httppost);
						String _response = EntityUtils.toString(response.getEntity());
						Log.e("test",_response); 
						jsobj1 = new JSONObject(_response);
						
						return jsobj1;
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
						 
						
							 Log.e("Freeee","Freeee");
							 Event_title.setText(jsobj1.getJSONObject("event_info").get("event_title").toString().replace("null", " "));
							 Event_venue.setText(jsobj1.getJSONObject("event_info").get("vanue_name").toString().replace("null", " "));
							
							 atvPlaces.setText(jsobj1.getJSONObject("event_info").get("street_address").toString().replace("null"," "));
						  s_date=jsobj1.getJSONObject("event_info").get("event_start_date_time").toString().replace("null"," ");
						  e_date=jsobj1.getJSONObject("event_info").get("event_end_date_time").toString().replace("null"," ");
						  ss_date=Constants.formatteddate1(s_date);
						  ss_time=Constants.formatteddate2(s_date);
						 
						  ee_date=Constants.formatteddate1(e_date);
						  ee_time=Constants.formatteddate2(e_date);
						 
						 eve_start_date.setText(ss_date);
						 eve_start_time.setText(ss_time);
						 eve_end_date.setText(ee_date);
						 eve_end_time.setText(ee_time);
						 
						 String image_url="http://rails2.swapclone.com/eventdemo/public/data/thumb/event/";
						String image_name=jsobj1.getJSONObject("event_info").get("event_logo").toString();
						String image=image_url+image_name;
						UrlImageViewHelper.setUrlDrawable(event_logo,image);

							
						 
								
						 }
					

							
							catch (JSONException e) 
							{
								e.printStackTrace();
							}     
							
					}
					else 
					{
							ExecptionHandler.register(Edit_Event.this, (Exception) json);
					}	
				}
			}


				
				
				class CreateEvent1 extends AsyncTask<Void, Void, Object>
				{
					
					private ProgressDialog pdia;
					protected void onPreExecute() 
					{
							super.onPreExecute();
							pdia = new ProgressDialog(Edit_Event.this);
							pdia.setMessage("Loading...");
							pdia.show();
						
					}

					// needed for 273
					//needed for 273
					//needed for 273
					// needed for 273
					//needed for 273
					//needed for 273
					// needed for 273
					//needed for 273
					//needed for 273

					@Override
					protected Object doInBackground(Void... params) 
					{
						// TODO Auto-generated method stub
					
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/edit_event");
						

						try 
						{
							
						
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
						start_d=eve_start_date.getText().toString();
						start_t=eve_start_time.getText().toString();
						end_d=eve_end_date.getText().toString();
						end_t=eve_end_time.getText().toString();
						
						String event_start=start_d+" "+start_t;
						String event_end=end_d+" "+end_t;
						Log.e("CHECKKKKKKKKKKKKKKKKKKKKKKKK","CHECKKKKKKKKKKKKKKKK");
						Log.e("EVent start date from ",event_start);
						Log.e("EVENT end date from",event_end);
						
						//	start_date_time=start_d+" "+start_t;
						//	end_date_time=end_d+" "+end_t;
						//	end_date_time=end_date.getText().toString() + end_time.getText().toString();
							nameValuePairs.add(new BasicNameValuePair("user_id",id));
							nameValuePairs.add(new BasicNameValuePair("id", e_id));
							nameValuePairs.add(new BasicNameValuePair("event_title", Event_title.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("vanue_name", Event_venue.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("street_address", atvPlaces.getText().toString()));
							
							nameValuePairs.add(new BasicNameValuePair("organizer_id","103"));
							nameValuePairs.add(new BasicNameValuePair("event_detail", Event_description.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("organizer_host","ROCKERSINFO"));
							nameValuePairs.add(new BasicNameValuePair("host_description","not needed"));
							nameValuePairs.add(new BasicNameValuePair("event_logo",img_name));
							nameValuePairs.add(new BasicNameValuePair("event_start_date_time","2014-04-27 00:00:00"));
							nameValuePairs.add(new BasicNameValuePair("event_end_date_time", "2016-04-27 00:00:00"));
							Log.e("test", nameValuePairs.toString());
						//	Log.e("Event_Start_Date",start_date_time);
						//	Log.e("Event_End_Date",end_date_time);

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
										Intent intent=new Intent(Edit_Event.this,EventHome.class);
										startActivity(intent);
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
							 			ExecptionHandler.register(Edit_Event.this, (Exception) jsobj);
							 		}	
							 }
							
					
					}
				}
}
					
			