package com.lbc.doodlelike.db;

public interface IMongoService {
	public void create(Object id, String event, String location, String[] Time);

	public void update();
}
