package com.dongyeol;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;


public class KrxAPIkospi {
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd?basDd=20230328");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("AUTH_KEY", "DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0");
		conn.setRequestProperty("Accept", "application/json");
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		String inputline = null;
		StringBuffer sb = new StringBuffer();
		
		while((inputline=bf.readLine())!= null) {
			sb.append(inputline);
		}
		
		String response = sb.toString();
		
		JSONParser parser = new JSONParser();
		JSONObject   obj =  (JSONObject) parser.parse(response);
		JSONArray data = (JSONArray) obj.get("OutBlock_1");

		
		HashMap<String, String> input = new HashMap<String, String>();
		input.put("Day", "20230328");
		input.put("Data", data.toString());
		JSONObject price = new JSONObject(input);
		Item item =Item.fromJSON(price.toString());
		System.out.println(item);
		
		DynamoDbMaker dbMaker = new DynamoDbMaker("AKIA2MVM52HD6L36Z4X4", "YZUxJhbQBA+0O+R9ljkl8Wxw7JEpXbNeYxdfJDmQ");
		DynamoDB dynamoDB = dbMaker.getDdb();
		
		Table table = dynamoDB.getTable("Kospi");
		PutItemOutcome result = table.putItem(item);
		System.out.println(result);
		
		
		
		

		
		
		
		
		
	}
}
