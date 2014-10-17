package com.lbc.doodlelike.service;

public interface IDLService {

	public void createEvent(Object id, String event, String location,
			String[] time);

	public void updateEvent();
}
