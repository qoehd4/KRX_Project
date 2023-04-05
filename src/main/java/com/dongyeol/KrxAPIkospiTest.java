package com.dongyeol;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
//DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0

public class KrxAPIkospiTest {
	
	public static void main(String[] args) throws Exception {
		DynamoDbMaker dbMaker = new DynamoDbMaker("AKIA2MVM52HD6L36Z4X4", "YZUxJhbQBA+0O+R9ljkl8Wxw7JEpXbNeYxdfJDmQ");
		DynamoDB dynamoDB = dbMaker.getDdb();
		
		Table table = dynamoDB.getTable("Kospi");
		
		List<String> daylist = makeDaylist();
		
		for(String day:daylist) {
			try {
				PutItemOutcome outcome = table.putItem(createItemKospi(day, "DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0"));
				PutItemResult result = outcome.getPutItemResult();
				System.out.println(result.getConsumedCapacity());
			} catch (Exception e) {
				String message = e.getMessage();
				System.out.println(message);
			}
			Thread.sleep(1000);
		}


		
		
		
	}
	
	public static Item createItemKospi(String day,String apiKey) throws Exception {
		String baseUrl = "http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd?basDd=day";
		String requestUrl  =  baseUrl.replace("day", day);
	
		
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("AUTH_KEY", apiKey);
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
		JSONObject   obj =  (JSONObject) parser.parse(response);
		JSONArray data = (JSONArray) obj.get("OutBlock_1");
		
		Item item = new Item().withPrimaryKey("Day", day).withList("Data", data);
		
		
		return item;
		
	}
	
	public static List<String> makeDaylist() throws Exception{
		List<String> daylist = new ArrayList<String>();
		XSSFWorkbook workbook = new XSSFWorkbook("./src/main/java/com/dongyeol/daylist.xlsx");
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		int rows = sheet.getPhysicalNumberOfRows();
		
		for(int row=1;row<rows;row++) {
			XSSFRow realRow = sheet.getRow(row);
			String day = realRow.getCell(0).toString();
			day=day.replace("/", "");
			daylist.add(day);
		}
		return daylist;
	}
}
