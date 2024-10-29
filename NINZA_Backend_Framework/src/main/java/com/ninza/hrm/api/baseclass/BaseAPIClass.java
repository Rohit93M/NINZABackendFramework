package com.ninza.hrm.api.baseclass;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.hrm.genericutility.DatabaseUtility;
import com.ninza.hrm.genericutility.JavaUtility;
import com.ninza.hrm.genericutility.PropertyFileUtility;

import java.io.IOException;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class BaseAPIClass {
	
	public JavaUtility jUtils = new JavaUtility();
	public PropertyFileUtility fileUtils = new PropertyFileUtility();
	public DatabaseUtility dbUtils = new DatabaseUtility();
	public static RequestSpecification reqSpec;
	public static ResponseSpecification resSpec;
	//These two references are being used by every API test cases, so we've to make them static, or else
	//you won't be able to use them in every test case
	
	@BeforeSuite
	public void configBS() throws IOException {
		
		System.out.println("Establishing connection with Database");
		dbUtils.getDatabaseConnection();
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setContentType(ContentType.JSON);
		//reqBuilder.setAuth(basic("username", "password"));
		//reqBuilder.addHeader("", "");
		reqBuilder.setBaseUri(fileUtils.getDataFromPropertyFile("BASEUri"));
		reqSpec = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectContentType(ContentType.JSON);
		resSpec = resBuilder.build();
		
	}
	
	@AfterSuite
	public void configAS() {
		
		System.out.println("Closing connection with Database");
		dbUtils.closeDatabaseConnection();
	}
}
