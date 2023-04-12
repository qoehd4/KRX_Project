package com.dongyeol.mongo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DbMaker {

	public static void main(String[] args) throws Exception {
		String strDate = "20230404";
		
		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(4, 6));
		int day = Integer.parseInt(strDate.substring(6, 8));
		
		LocalDate date = LocalDate.of(year, month, day);
		
		Document inputDocument = new Document().append("Day", date).append("Data", getDocumentList(strDate));
		
		ConnectToMongo conn = new ConnectToMongo(27017);
		MongoDatabase db = conn.getDatabase("StockKr");
		MongoCollection<Document> kospiCollenction = db.getCollection("kospi");
		
		kospiCollenction.insertOne(inputDocument);
		
		
		
		

	}
	
	public static List<Document>  getDocumentList(String day) throws Exception {
		String baseUrl = "http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd?basDd=day";
		String requestUrl  =  baseUrl.replace("day", day);
	
		
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("AUTH_KEY", "DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0");
		conn.setRequestProperty("Accept", "application/json");
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		String inputline = null;
		StringBuffer sb = new StringBuffer();
		
		while((inputline=bf.readLine())!= null) {
			sb.append(inputline);
		}
		
		bf.close();
		
		String response = sb.toString();		
		JSONParser parser = new JSONParser();
		JSONObject obj  = (JSONObject) parser.parse(response);
		JSONArray data = (JSONArray) obj.get("OutBlock_1");	
		
		List<Document> corpDocumentList = new ArrayList<Document>();
		
		for (int i = 0; i < data.size(); i++) {
			JSONObject corp = (JSONObject) data.get(i);
			
			String corpCode = (String) corp.get("ISU_CD");
			String corpName = (String) corp.get("ISU_NM");
			String closePrice = (String) corp.get("TDD_CLSPRC");
			String openPrice = (String) corp.get("TDD_OPNPRC");
			String volume = (String) corp.get("ACC_TRDVOL");
			String marketCap = (String) corp.get("MKTCAP");
			String shares = (String) corp.get("LIST_SHRS");
			
			Document corpDocument = new Document()
					.append("corpCode", corpCode)
					.append("corpName", corpName)
					.append("closePrice", closePrice)
					.append("openPrice", openPrice)
					.append("volume", volume)
					.append("marketCap", marketCap)
					.append("shares", shares);
			
			corpDocumentList.add(corpDocument);
			
		}
		
		
		
		return corpDocumentList;
	}

}
