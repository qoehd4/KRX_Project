package com.dongyeol.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConnectToMongo {
	
	private MongoClient mongo;
	private MongoDatabase database;
	
	public ConnectToMongo(int port) {
		mongo = new MongoClient("localhost",port);
	}
	
	public MongoDatabase getDatabase(String dbName) {
		database = mongo.getDatabase(dbName);
		return database;
	}
	
	


}
