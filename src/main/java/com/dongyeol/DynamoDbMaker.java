package com.dongyeol;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDbMaker {
	private DynamoDB ddb;
	
	public DynamoDbMaker(String accesesKey, String secretKey) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withRegion("ap-southeast-2")
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesesKey, secretKey)))
				.build();
		ddb = new DynamoDB(client);
	}


	public DynamoDB getDdb() {
		return ddb;
	}


	
}
