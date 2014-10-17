package com.lbc.doodlelike.service;

import com.lbc.doodlelike.db.IMongoService;
import com.lbc.doodlelike.db.MongoServiceImpl;

/**
 * Implementation of all Doodlelike services
 * 
 * @author lbchen
 *
 */
public class DLServiceImpl implements IDLService {
	private IMongoService mongo;
	private String url = "54.81.51.121";
	private String dbname = "mydb";
	private String collname = "scheduletmp";

	public DLServiceImpl() {
		mongo = new MongoServiceImpl(url, dbname, collname);
	}

	/**
	 * Create an event.
	 */
	public void createEvent(Object id, String event, String location,
			String[] time) {
		mongo.create(id, event, location, time);
	}

	public void updateEvent() {

	}
}
