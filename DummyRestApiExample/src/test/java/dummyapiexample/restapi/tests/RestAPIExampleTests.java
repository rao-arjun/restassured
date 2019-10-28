
package dummyapiexample.restapi.tests;

import org.testng.annotations.Test;

import dummyapiexample.restassured.methods.RestHttpMethods;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class RestAPIExampleTests {
	
  private static String generatedEmployeeId;
  private RestHttpMethods restTester;
  
  @BeforeTest
  public void beforeTest() {
	  restTester = new RestHttpMethods();
  }

  
  @Test
  public void retrieveAllEmployeeRecords(){
	  restTester.getAllEmployeesRecords();
	  System.out.println(restTester.returnStatusCode());
	  System.out.println(restTester.returnResponseAsString());
	  //System.out.println(restTester.returnResponseHeaders());
	  System.out.println(restTester.lengthOfResponse());
	  assertEquals(restTester.returnStatusCode(),200);
  }
  
  @Test
  public void getSpecificEmployeeIdRecord(){
	  restTester.getSpecificEmployeeIdRecord("8724");
	  assertEquals(restTester.returnStatusCode(),200);
	  System.out.println(restTester.returnResponseAsString());
	  assertTrue(restTester.verifyValueOfAFieldForEmployeeId("8724", "employee_name", "AutoTest"));
  }
  
  
  @Test(dataProvider="employeeDataProvider")
  public void createValidateAndDeleteEmployeeRecord(String employeeName, String salary, int age){
	  //Create a new employee record
	  restTester.createEmployee(employeeName, salary, age);
	  //System.out.println(restTester.returnResponseHeaders());
	  assertEquals(restTester.returnStatusCode(),200);
	  System.out.println(restTester.returnResponseAsString());
	  
	  //Get all employee records and extract the total no of records - the posted record will be the last entry
	  restTester.getAllEmployeesRecords();
	  System.out.println(restTester.lengthOfResponse());
	  //Extract the generated Employee Id
	  generatedEmployeeId = restTester.getGeneratedEmployeeIdOfLatestRecord();
	  System.out.println(generatedEmployeeId);
	  
	  //Validate the contents created in the POST request in the response of GET /employee/{id}
	  assertTrue(restTester.verifyValueOfAFieldForEmployeeId(generatedEmployeeId, "employee_name", employeeName));
	  assertTrue(restTester.verifyValueOfAFieldForEmployeeId(generatedEmployeeId, "employee_salary", salary));
	  assertTrue(restTester.verifyValueOfAFieldForEmployeeId(generatedEmployeeId, "employee_age", Integer.toString(age)));
	  
	  //Delete the contents for the current record DELETE /delete/{id}
	  restTester.deleteEmployeeRecord(generatedEmployeeId);
	  assertEquals(restTester.returnStatusCode(),200);
	  System.out.println(restTester.returnResponseAsString());
	  assertTrue(restTester.returnResponseAsString().contains("deleted"));
	  
	  //Retrieve the same record it should not be present in the system (404) or body should be "false"
	  restTester.getSpecificEmployeeIdRecord(generatedEmployeeId);
	  System.out.println(restTester.returnResponseAsString());
	  assertTrue(restTester.returnResponseAsString().contains("false"));
	  
  }


  
  @DataProvider
  public Object[][] employeeDataProvider() {
    return new Object[][] {
      new Object[] { "Auto21", "4210", 38},
      new Object[] {  "Auto22", "5210", 33 },
      new Object[] { "Auto23", "6230", 42}
    };
  } 

  
  @AfterTest
  public void afterTest() {
  }

}
