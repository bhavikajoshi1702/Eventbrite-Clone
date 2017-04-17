package com.example.eventbrite;

import java.util.List;

import android.app.Activity;
import android.content.Context;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class TicketAdapter extends ArrayAdapter<Ticket> {

	protected static final String LOG_TAG = TicketAdapter.class.getSimpleName();

	private List<Ticket> items;
	private int layoutResourceId;
	private Context context;
	
	static String ticket_name;
	
	

	public TicketAdapter(Context context, int layoutResourceId, List<Ticket> items) 
	{
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
	 TicketHolder holder = null;

		 LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		holder = new TicketHolder();
		holder.ticket = items.get(position);
		holder.removeTicketButton = (ImageButton)row.findViewById(R.id.ticket_remove);
		holder.removeTicketButton.setTag(holder.ticket);
		
		
		holder.settingTicketButton=(ImageButton)row.findViewById(R.id.ticket_setting);
		holder.settingTicketButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				
				Fragment freeFragment = new FreeFragment();
                if (freeFragment != null)
                
                    switchFragment(freeFragment);
            	Log.e("adapter_click_setting","adapter_click_setting");
			}
		});
		
		

		holder.name = (EditText)row.findViewById(R.id.ticket_name);
		
	
		row.setTag(holder);

		setupItem(holder);
		return row;
	}
	
	private void switchFragment(Fragment newFragment)
	{
		
        if (context == null)
        {
        	Log.e("null_context","nulllll");
             return;
        }
        if (context instanceof AddNewTicket) {
        	Log.e("meth_calling","method_calling");
             AddNewTicket feeds = (AddNewTicket) context;
             feeds.switchContent(newFragment);  
        }
   }

	private void setupItem(TicketHolder holder) {
		holder.name.setText(holder.ticket.getName());
		ticket_name=holder.name.getText().toString();

	}

	public static class TicketHolder 
	{
		Ticket ticket;
		EditText name;
		ImageButton settingTicketButton;
		ImageButton removeTicketButton;
	}
	
	private void setNameTextChangeListener(final TicketHolder holder) 
	{
		holder.name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				holder.ticket.setName(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void afterTextChanged(Editable s) { }
		});
	}




}