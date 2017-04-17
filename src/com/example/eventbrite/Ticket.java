package com.example.eventbrite;

import java.io.Serializable;

public class Ticket implements Serializable {
	private static final long serialVersionUID = -5435670920302756945L;
	
	private String name = "";
	

	public Ticket(String name) {
		this.setName(name);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}