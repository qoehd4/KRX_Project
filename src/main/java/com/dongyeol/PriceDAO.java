package com.dongyeol;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//.withProjectionExpression("MKTCAP,TDD_LWPRC");
public class PriceDAO {

	public static void main(String[] args) throws ParseException {
		DynamoDbMaker maker = new DynamoDbMaker("AKIA2MVM52HD6L36Z4X4", "YZUxJhbQBA+0O+R9ljkl8Wxw7JEpXbNeYxdfJDmQ");
		DynamoDB ddb = maker.getDdb();
		
		Table table = ddb.getTable("Kospi");
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("Day", "20230403");
				
		

		

	}

}
