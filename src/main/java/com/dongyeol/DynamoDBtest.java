package com.dongyeol;

import java.util.HashMap;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;

public class DynamoDBtest {

	public static void main(String[] args) {
		AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
				.withRegion("ap-southeast-2")
				.withCredentials(
						new AWSStaticCredentialsProvider(
								new BasicAWSCredentials("AKIA2MVM52HD6L36Z4X4", "YZUxJhbQBA+0O+R9ljkl8Wxw7JEpXbNeYxdfJDmQ")
								))
				.build(); // 이녀석 옛날이름이 AmazonDynamoDBClinet임!!!!!!!!!!!!!!!!!!!1

		PutItemRequest request = new PutItemRequest();
		
		request.setTableName("JOB");
		request.setReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL);
		
		HashMap<String, AttributeValue> map = new HashMap<String, AttributeValue>();
		map.put("CountryName", new AttributeValue("nepal"));
//		map.put("CountryId", new AttributeValue("18"));
        map.put("JobId", (new AttributeValue()).withS("3"));
        
//        map.put("CompanyName", new AttributeValue("Amazon"));
//        map.put("JobTitle", new AttributeValue("Software Engineer"));
//        map.put("Food", new AttributeValue("bobo"));
		
		request.setItem(map);
		

		try {
			PutItemResult result  = ddb.putItem(request);
			System.out.println(result);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} 
		
		
		
		

	}

}

