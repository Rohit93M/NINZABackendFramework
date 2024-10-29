package com.ninza.hrm.api.projecttest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.pojoclass_utility.ProjectPojo;
import com.ninza.hrm.constants.IEndPoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.sql.SQLException;

public class ProjectTest extends BaseAPIClass {

	ProjectPojo projectPojoObj;
	
	@Test
	public void addSingleProjectWithCreatedStatusTest() throws SQLException, IOException {
		

		// Step-1: Create pre-requisites

		String expSuccessMsg = "Successfully Added";
		
		String projectName = "FB_" + jUtils.getRandomNumber();

		// Create an object of Pojo class
		projectPojoObj = new ProjectPojo(projectName, "Created", "Rohit", 0);

		// Verify the projectName in API layer

		Response response = given()
				.spec(reqSpec)
				// Whatever common configuration is required before sending the request, it is available in reqSpec

				.body(projectPojoObj)
				// Automatically RestAssured will take care of Serialization, that means,
				// automatically it will convert java into json file before sending the request, 
				// because of Jackson

				.when() // Step-2: Perform action
				.post(IEndPoints.ADD_PROJ);
		// All http methods are going to return the object of the response

		response.then() // Step-3: Validation
				.assertThat().statusCode(201)
				.spec(resSpec)
				// Whatever common validation is required after receiving the response, we are going to put in resSpec
				//.assertThat().time(Matchers.lessThan(3000L))
				.log().all();

		// Capture data from the response
		String actProjId = response.jsonPath().get("projectId");
		String actualSuccessMsg = response.jsonPath().get("msg");
		Assert.assertEquals(actualSuccessMsg, expSuccessMsg);

		// For every API request not just validate the expected result only in the response,
		// put a validation for database also

		// Verify the projectName in database layer
		
		boolean flag = dbUtils.executeQueryVerifyAndGetData("Select project_name from project;", 4, projectName);
		Assert.assertTrue(flag, "Project in database is not verified");
		
	}

	@Test (dependsOnMethods = "addSingleProjectWithCreatedStatusTest")
	public void createDuplicateProjectTest() throws IOException {

		Response response = given()
				.spec(reqSpec)
				.body(projectPojoObj)

				.when() // Step-2: Perform action
				.post(IEndPoints.ADD_PROJ);
		
		        response.then() // Step-3: Validation
		        .assertThat().statusCode(409)
		        .spec(resSpec)
		        .log().all();        
	}

}
