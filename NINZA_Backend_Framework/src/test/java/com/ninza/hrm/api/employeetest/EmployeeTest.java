package com.ninza.hrm.api.employeetest;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.sql.SQLException;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.pojoclass_utility.EmployeePojo;
import com.ninza.hrm.api.pojoclass_utility.ProjectPojo;
import com.ninza.hrm.constants.IEndPoints;
import com.ninza.hrm.genericutility.DatabaseUtility;
import com.ninza.hrm.genericutility.JavaUtility;
import com.ninza.hrm.genericutility.PropertyFileUtility;

import io.restassured.http.ContentType;

public class EmployeeTest extends BaseAPIClass{
	
	@Test
	public void addEmployeeTest() throws SQLException, IOException {
	
		// Step-1: Create pre-requisites
		
                String baseURI = fileUtils.getDataFromPropertyFile("BASEUri");
				String projectName = "FB_" + jUtils.getRandomNumber();
				String employeeName = "user_"+ jUtils.getRandomNumber();

				// API-1 ==> Add a project inside the server
				ProjectPojo projectPojoObj = new ProjectPojo(projectName, "Created", "Rohit", 0);

				        given()
						.spec(reqSpec)
						.body(projectPojoObj)
			
						.when() // Step-2: Perform action
						.post(IEndPoints.ADD_PROJ)
				        .then()
						.log().all();
				         
				// API-2 ==> Add an Employee to same project
				       //  String designation, String dob, String email, String empName, double experience,
						//	String mobileNo, String project, String role, String username
				         
			    EmployeePojo empObj = new EmployeePojo("Architect", "24/04/1983", "rohit@gmail.com", employeeName, 18, "9876543210", projectName, "ROLE_EMPLOYEE", employeeName);
			    given()
			    .spec(reqSpec)
				.body(empObj)

				.when() // Step-2: Perform action
				.post(IEndPoints.ADD_EMP)
		        .then() // Step-3: Validation
	
				.assertThat().statusCode(201)
				.and()
				.time(Matchers.lessThan(3000L))
		        .spec(resSpec)
				.log().all();
			    
			    //Verify Employee Name in Database
			    
//			    boolean flag = dbUtils.executeQueryVerifyAndGetData("select * from employee", 5, employeeName);
//				Assert.assertTrue(flag, "Employee in database is not verified");
				
	}
	
	@Test
	public void addEmployeeWithoutEmailTest() throws SQLException, IOException {
	
		// Step-1: Create pre-requisites

				String baseURI=  fileUtils.getDataFromPropertyFile("BASEUri");
				String projectName = "FB_" +jUtils.getRandomNumber();
				String employeeName = "user_"+jUtils.getRandomNumber();

				// API-1 ==> Add a project inside the server
				ProjectPojo projectPojoObj = new ProjectPojo(projectName, "Created", "Rohit", 0);

				        given()
				        .spec(reqSpec)
						.body(projectPojoObj)
			
						.when() // Step-2: Perform action
						.post(IEndPoints.ADD_PROJ)
				        .then()
				        .spec(resSpec)
						.log().all();
				         
				// API-2 ==> Add an Employee to same project
				       //  String designation, String dob, String email, String empName, double experience,
						//	String mobileNo, String project, String role, String username
				         
			    EmployeePojo empObj = new EmployeePojo("Architect", "24/04/1983", "", employeeName, 18, "9876543210", projectName, "ROLE_EMPLOYEE", employeeName);
			    given()
			    .spec(reqSpec)
				.body(empObj)

				.when() // Step-2: Perform action
				.post(IEndPoints.ADD_EMP)
		        .then() // Step-3: Validation
				.assertThat().statusCode(500)
		        .spec(resSpec)
				.log().all();
	}

}
