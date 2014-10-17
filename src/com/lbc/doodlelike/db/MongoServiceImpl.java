package com.lbc.doodlelike.db;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoServiceImpl implements IMongoService {

	private MongoClient client;
	private DB db;
	private DBCollection coll;

	public MongoServiceImpl(String url, String dbname, String collname) {
		try {
			client = new MongoClient(url);
			db = client.getDB(dbname);
			coll = db.getCollection(collname);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create an mongodb event document with id, event, location, and time.
	 */
	public void create(Object id, String event, String location, String[] time) {
		BasicDBObject dbo = new BasicDBObject("_id", id).append("event", event)
				.append("location", location).append("time", time).append("attendees", new ArrayList<String>());
		coll.insert(dbo);
	}

	public void update() {

	}
}
