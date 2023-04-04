package com.dongyeol;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonParser;


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
		System.out.println(obj);
		System.out.println(obj.get("OutBlock_1"));
		JSONObject last = new JSONObject();
		System.out.println(last);
		
		
		
		
		
	}
}
