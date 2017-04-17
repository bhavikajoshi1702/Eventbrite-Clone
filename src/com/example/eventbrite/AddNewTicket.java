package com.example.eventbrite;


import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class AddNewTicket extends FragmentActivity {

	private TicketAdapter adapter;
	static int ticket_type;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticket_listview);
		setupListViewAdapter();
		setupAddTicketButton();
	}
	
	public void removeTicketOnClickHandler(View v) 
	{
		Ticket itemToRemove = (Ticket)v.getTag();
		adapter.remove(itemToRemove);
	}
	
	
	

	private void setupListViewAdapter() 
	{
		adapter = new TicketAdapter(AddNewTicket.this, R.layout.ticket_list_item, new ArrayList<Ticket>());
		ListView TicketListView = (ListView)findViewById(R.id.ticketlist);
		TicketListView.setAdapter(adapter);
	}
	
	private void setupAddTicketButton() {
		findViewById(R.id.free).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				adapter.insert(new Ticket(""), 0);
				ticket_type=1;
			}
		});
		
findViewById(R.id.paid).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				adapter.insert(new Ticket(""), 0);
				ticket_type=2;
			}
		});

		findViewById(R.id.donation).setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) 
	{
		adapter.insert(new Ticket(""), 0);
		ticket_type=3;
	}
});
	}

	public void switchContent(android.support.v4.app.Fragment newFragment) {
		// TODO Auto-generated method stub
		getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, newFragment).commit();
		
	}
	



		
}