package com.dongyeol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

	public static void main(String[] args) throws Exception {
		String baseUrl = "http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd?basDd=day";
		String requestUrl  =  baseUrl.replace("day", "20230404");
	
		
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
		JSONObject obj = (JSONObject) parser.parse(response);
		JSONArray inputData = (JSONArray) obj.get("OutBlock_1");
		JSONObject reformedData = new JSONObject();
		reformedData.put("Day", "20230506");
		
		
		for (int i = 0; i < inputData.size(); i++) {
			JSONObject corp = (JSONObject) inputData.get(i);
			//키값용
			String corpCode = (String) corp.get("ISU_CD");
			
			//value 용
			String corpName = (String) corp.get("ISU_NM");
			String closePrice =(String) corp.get("TDD_CLSPRC");
			String openPrice =(String) corp.get("TDD_OPNPRC");
			String marketCap =(String) corp.get("MKTCAP");
			String totalShares =(String) corp.get("LIST_SHRS");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("corpName", corpName);
			map.put("closePrice", closePrice);
			map.put("openPrice", openPrice);
			map.put("marketCap", marketCap);
			map.put("totalShares", totalShares);
			
			JSONObject valJson = new JSONObject(map);
			reformedData.put(corpCode, valJson);
			
			
		}
		
		
		try{
			
			// 파일 객체 생성
			File file = new File("/");
			
			// true 지정시 파일의 기존 내용에 이어서 작성
			FileWriter fw = new FileWriter(file, true) ;
			
			// 파일안에 문자열 쓰기
			fw.write(reformedData.toString());
			fw.flush();

			// 객체 닫기
			fw.close(); 
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		


	}

}
