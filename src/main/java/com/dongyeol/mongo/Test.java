package com.dongyeol.mongo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Test {
	
	public static void main(String[] args) throws Exception {
		ConnectToMongo conn = new ConnectToMongo(27017);
		MongoDatabase db = conn.getDatabase("StockKr");
		MongoCollection<Document> kospiCollection =  db.getCollection("kospi");
		
		LocalDate target = LocalDate.of(2020, 04, 02);
		
		List<Document> arrayOfDocument =  new ArrayList<Document>();
		
		Document array1 = new Document().append("a", 1).append("b", 2);
		Document array2 = new Document().append("a", 3).append("b", 7).append("c", 15);
		
		arrayOfDocument.add(array1);
		arrayOfDocument.add(array2);
		
		Document input = new Document("Day", arrayOfDocument);
		

		
		
		
		kospiCollection.insertOne(input);
	}
}
