
package dummyapiexample.restapi.tests;

import org.testng.annotations.Test;

import dummyapiexample.restassured.methods.RestHttpMethods;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class RestAPIExampleTests {
	
  private static int employeeId;
  private RestHttpMethods restTester;
  
  @BeforeTest
  public void beforeTest() {
	  restTester = new RestHttpMethods();
  }

  @Test
  public void retrieveAllEmployeeRecords(){
	  System.out.println(restTester.getAllEmployeesRecords());
	  System.out.println(restTester.returnStatusCode());
	  //System.out.println(restTester.returnResponseHeaders());
	  System.out.println(restTester.lengthOfResponse());
	  assertEquals(restTester.returnStatusCode(),200);
  }

  @Test
  public void getSpecificEmployeeIdRecord(){
	  System.out.println(restTester.getSpecificEmployeeIdRecord("8612"));
	  System.out.println(restTester.returnStatusCode());
	  System.out.println(restTester.lengthOfResponse());
	  assertEquals(restTester.returnStatusCode(),200);
  }
  

 /*
  @Test(dataProvider = "dp")
  public void f(Integer n, String s) {
  }
  
  
  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "a" },
      new Object[] { 2, "b" },
    };
  } 
  */
  
  @AfterTest
  public void afterTest() {
  }

}
