package com.lbc.doodlelike.db;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoDBTest {

	static DBCollection coll;
	static MongoClient mongoClient;
	static DB db;

	public static void main(String[] args) {
		String[] time = { "800-900", "900-1000", "1000-1100" };
		try {
			mongoClient = new MongoClient("54.81.51.121");
			db = mongoClient.getDB("mydb");
			coll = db.getCollection("blogs");
			// insertDoc("Sunday", "Home", time);
			// query();
//			getID();
			insert();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insert() {
		BasicDBObject dbo = new BasicDBObject("_id", new ObjectId())
				.append("event", "testing").append("location", "sjsu")
				.append("time", "1654pm");
		coll.insert(dbo);
	}

	public static void getID() {
		System.out.println(new ObjectId().toString());
	}

	public static void insertDoc(String event, String location, String[] time) {
		coll = db.getCollection("scheduletmp");
		BasicDBObject dbo = new BasicDBObject("event", event).append(
				"location", location).append("time", time);
		coll.insert(dbo);
	}

	public static String query() {
		coll = db.getCollection("scheduletmp");
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(
				"543b116330043ab85d310e55"));
		DBCursor cursor = coll.find(query);
		System.out.println(cursor.next().toString());
		return cursor.toString();
	}
}
