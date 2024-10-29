package com.ninza.hrm.genericutility;

import com.jayway.jsonpath.JsonPath;
import com.sun.tools.javac.util.List;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import io.restassured.response.Response;

public class JsonUtility {
	
	public static PropertyFileUtility fileUtils = new PropertyFileUtility();

	public String getDataOnJsonPath(Response response, String jsonXpath) {
		List<String> list = JsonPath.read(response.asString(), jsonXpath);
		return list.get(0).toString();
	}

	public String getDataOnXmlPath(Response response, String xmlXpath) {
		return response.xmlPath().get(xmlXpath);
	}
	
	public boolean verifyDataOnJSonPath(Response response, String jsonXpath, String expectedData) {
		List<String> list = JsonPath.read(response.asString(), jsonXpath);
		boolean flag = false;
		for(String str : list) {
			if(str.equals(expectedData)) {
				flag = true;
			}
		}
		return flag;
	}
	
	public String getAccessToken() throws IOException {
	Response response = given()
		.formParam("client_id", fileUtils.getDataFromPropertyFile("ClientID"))
		.formParam("client_secret", fileUtils.getDataFromPropertyFile("ClientSecret"))
		.formParam("grant_type", "client_credentials")
		.when()
		.post("http://49.249.28.218:8180/auth/realms/ninza/protocol/openid-connect/token");
		response.then()
		.log().all();
		
		//Capture data from the  response
		String token = response.jsonPath().get("access_token");
		return token;	
	}
	
	
	
}
