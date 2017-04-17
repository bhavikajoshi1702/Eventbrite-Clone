package com.example.eventbrite;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.example.eventbrite.Create_Organizer.CreateOrganizer;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class Edit_Organizer extends Activity
{
	
	private EditText Organizer_name;
	private EditText Organizer_description;
	private Button Save, Upload;
	private EditOrganizer editorganizer;
	private ImageView Logo;
	private ProgressDialog loading;
	private String logo ="logo_c781a17592_penguinssample.jpg";
	private PreOrganizer preorganizer;
	JSONObject json1;
	String o_id;
	
	protected static final int SELECT_PICTURE = 1;
	  int serverResponseCode = 0;
	    ProgressDialog dialog = null;
	    private String selectedImagePath;
	    JSONObject jsobje;
	    String img_name;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		 Bundle b = getIntent().getExtras();
		 o_id =(String) b.get("org_id");
		 Log.e("my org id",o_id);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_organizer);
		Organizer_name=(EditText)findViewById(R.id.organizer_name);
		Organizer_description=(EditText)findViewById(R.id.organizer_description);
		Save=(Button)findViewById(R.id.button_create);
		Upload=(Button)findViewById(R.id.upload_button);
		Logo=(ImageView)findViewById(R.id.imageView1);
		
		Logo=(ImageView)findViewById(R.id.imageView1);
		preorganizer = new PreOrganizer();
		preorganizer.execute((Void) null);
		
		
		Save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				editorganizer = new EditOrganizer();
				editorganizer.execute((Void) null);
				
			}
		});	
		
Logo.setOnClickListener(new OnClickListener() {
			
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
		
Upload.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		Log.e("333","333_uploading");
		// TODO Auto-generated method stub
		 dialog = ProgressDialog.show(Edit_Organizer.this, "", "Uploading file...", true);
            
            new Thread(new Runnable() {
                    public void run() {
                         Edit_Organizer.this.runOnUiThread(new Runnable() {
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
	       
	    	HttpPost postRequest = new HttpPost("http://rails2.swapclone.com/eventdemo/upload_org.php");
	    	
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
	    	   
	    	   Log.e("777_seccesss","77777_seccesss");
			 jsobje = new JSONObject(response1);
		    	  img_name= jsobje.getString("name_old");
	    	   dialog.dismiss();
	    	  
	       	
		
	       } 
	       
	       catch (UnsupportedEncodingException e) 
	       {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    }
	
	public class PreOrganizer extends AsyncTask<Void, Void, Object>
	{
		
		
		protected void onPreExecute()
		{
			loading=new ProgressDialog(Edit_Organizer.this);
			loading.setMessage(getResources().getString(R.string.loading));
			loading.setIndeterminate(true);
			loading.setCancelable(true);
			loading.show();
		}

		@Override
		protected Object doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/org_data");
	
			
			try 
			{
				
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("id", o_id));
				
				Log.e("test", nameValuePairs.toString());
				
		
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse response = httpclient.execute(httppost);
				
				String response1 = EntityUtils.toString(response.getEntity());
				Log.e("test",response1);
				 json1=new JSONObject(response1);
				//return new JSONObject(response1);
				return json1;
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
						
						 Organizer_name.setText(json1.getJSONObject("org_info").get("name").toString().replace("null", " "));
						 Organizer_description.setText(json1.getJSONObject("org_info").get("description").toString().replace("null", " "));
								
							
						 String image_url="http://rails2.swapclone.com/eventdemo/public/data/thumb/org/";
							String image_name=json1.getJSONObject("org_info").get("org_logo").toString();
							String image=image_url+image_name;
							UrlImageViewHelper.setUrlDrawable(Logo,image);

						 
				
					}
					else
					{
						new AlertDialog.Builder(Edit_Organizer.this)
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
			/*else {
				ExecptionHandler.register(getActivity(), (Exception) json);
			}*/
			
		}
	
		
	}
	
	
	public class EditOrganizer extends AsyncTask<Void, Void, Object>
	{
		
		
		protected void onPreExecute()
		{
			loading=new ProgressDialog(Edit_Organizer.this);
			loading.setMessage(getResources().getString(R.string.login_loading));
			loading.setIndeterminate(true);
			loading.setCancelable(true);
			loading.show();
		}

		@Override
		protected Object doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constants.BASE_URL+ "mobile_logins/edit_organizer");
	
			
			try 
			{
				
				
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("user_id", Constants.id));
				nameValuePairs.add(new BasicNameValuePair("name", Organizer_name.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("description", Organizer_description.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("org_logo",img_name));
				nameValuePairs.add(new BasicNameValuePair("id", "136"));
				
				Log.e("test", nameValuePairs.toString());
				
		
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse response = httpclient.execute(httppost);
				
				String response1 = EntityUtils.toString(response.getEntity());
				Log.e("test",response1);
				JSONObject json=new JSONObject(response1);
				//return new JSONObject(response1);
				if (json.getString("status").equals("1")) 
				{
					
					
				
				
					Log.e("ID",Constants.id);
					
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
						
						
						 
				
					}
					else
					{
						new AlertDialog.Builder(Edit_Organizer.this)
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
			/*else {
				ExecptionHandler.register(getActivity(), (Exception) json);
			}*/
			
		}
	
		
	}
}
