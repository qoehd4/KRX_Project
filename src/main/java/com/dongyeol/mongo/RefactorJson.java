package com.dongyeol.mongo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RefactorJson {

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
		JSONObject obj  = (JSONObject) parser.parse(response);
		JSONArray array = (JSONArray) obj.get("OutBlock_1");
		
		List<JSONObject> corps = new ArrayList<JSONObject>();
		
		for(int i=0; i<array.size();i++) {
			JSONObject corp = (JSONObject) array.get(i);
			
//			String corpCode = (String) corp.get("ISU_CD");
//			String corpName = (String) corp.get("ISU_NM");
//			String closePrice = (String) corp.get("TDD_CLSPRC");
//			String openPrice = (String) corp.get("TDD_OPNPRC");
//			String volume = (String) corp.get("ACC_TRDVOL");
//			String marketCap = (String) corp.get("MKTCAP");
//			String shares = (String) corp.get("LIST_SHRS");
			
			corp.remove("BAS_DD");
			corp.remove("MKT_NM");
			corp.remove("SECT_TP_NM");
			corp.remove("CMPPREVDD_PRC");
			corp.remove("FLUC_RT");
			corp.remove("TDD_HGPRC");
			corp.remove("TDD_LWPRC");
			corp.remove("ACC_TRDVAL");
			
			corps.add(corp);
			
			
		}
		

		
	}

}
