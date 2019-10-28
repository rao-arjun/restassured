package dummyapiexample.restassured.methods;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.hamcrest.Matchers.*;

public class RestHttpMethods {
	private final String apiUrl="http://dummy.restapiexample.com/api/v1";
	private final String resourcePathGetEmployees ="/employees";
	private final String resourcePathCreateEmployees ="/create";
	private final String resourcePathGetEmployeeId ="/employee/{id}";
	private final String resourcePathUpdateEmployeeId ="/update/{id}";
	private final String resourcePathDeleteEmployeeId ="/delete/{id}";
	
	private RequestSpecification requestSpec;
	private ResponseSpecification responseSpec;
	
	private static ValidatableResponse vResponse;
	private static Response response;
	private static int statusCode;
	private static String responseHeader;
	private static int responseCount;
	
	public RestHttpMethods(){
		requestSpec = new RequestSpecBuilder().setBaseUri(apiUrl).setAccept(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
	}

	public int returnStatusCode(){
		return statusCode;
	}
	
	public String returnResponseAsString(){
		return response.asString();
	}
	public String returnResponseHeaders(){
		return responseHeader;
	}
	
	public int lengthOfResponse(){
		return responseCount;
	}
	
	//GET /employees response
	public void getAllEmployeesRecords(){
		vResponse = given().log().all().spec(requestSpec).
				when().get(resourcePathGetEmployees).
				then();
		responseCount = vResponse.extract().jsonPath().getList("$").size();
		response = vResponse.extract().response();
		statusCode = response.statusCode();
		responseHeader = response.headers().toString();
	}
	
	//GET /employees/{id} response
	public void getSpecificEmployeeIdRecord(String generatedEmployeeId){
		response =  given().log().all().spec(requestSpec).pathParam("id",generatedEmployeeId).
		when().get(resourcePathGetEmployeeId).
		then().extract().response();
		statusCode = response.statusCode();
	}
	
	// POST /create response
	public void createEmployee(String name, String salary, int age){
		Employee employee = new Employee(name, salary, age);
		response = given().log().all().spec(requestSpec).contentType("application/json").body(employee).
				when().post(resourcePathCreateEmployees).
				then().extract().response();
		statusCode = response.statusCode();
		responseHeader = response.headers().toString();		
	}
	
	//DELETE /delete/{id} response
	public void deleteEmployeeRecord(String employeeId){
		response = given().log().all().spec(requestSpec).pathParam("id",employeeId).
		when().delete(resourcePathDeleteEmployeeId).
		then().extract().response();
		statusCode = response.statusCode();
	}

	//PUT /update/{id} response
	public void updateEmployeeRecord(String employeeId, String name, String salary, int age){
		Employee employee = new Employee(name, salary, age);
		response = given().log().all().spec(requestSpec).pathParam("employeeId",employeeId).contentType("application/json").body(employee).
		when().put(resourcePathUpdateEmployeeId).
		then().extract().response();
		statusCode = response.statusCode();
	}
	
	public boolean verifyValueOfAFieldForEmployeeId(String employeeId, String fieldName, String fieldValue){
		return given().log().all().spec(requestSpec).pathParam("id",employeeId).
		when().get(resourcePathGetEmployeeId).
		then().extract().response().jsonPath().getString(fieldName).equalsIgnoreCase(fieldValue);
	}
	
	public String getGeneratedEmployeeIdOfLatestRecord(){
		List<Object> jsonList = response.jsonPath().getList("$");
		HashMap<?, ?> hMap = (HashMap<?, ?>) jsonList.get(responseCount-1);
		System.out.println(hMap.entrySet());
		return (String) hMap.get("id");
	}
}
