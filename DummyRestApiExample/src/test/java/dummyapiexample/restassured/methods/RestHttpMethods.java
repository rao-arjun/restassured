package dummyapiexample.restassured.methods;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

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
	
	private static Response response;
	private static int statusCode;
	private static String responseHeader;
	private static int responseCount;
	
	public RestHttpMethods(){
		requestSpec = new RequestSpecBuilder().setBaseUri(apiUrl).setAccept(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
	}
	
	/*//GET /employees status code
	public int returnStatusCodeForGetAllEmployees(){
		return given().log().all().spec(requestSpec).
		when().get(resourcePathEmployees).
		then().extract().response().statusCode();
	}*/
	
	public int returnStatusCode(){
		return statusCode;
	}
	
	public String returnResponseHeaders(){
		return responseHeader;
	}
	
	public int lengthOfResponse(){
		return responseCount;
	}
	
	//GET /employees response
	public String getAllEmployeesRecords(){
		ValidatableResponse vResponse = given().log().all().spec(requestSpec).
				when().get(resourcePathGetEmployees).
				then();
		responseCount = vResponse.extract().jsonPath().getList("$").size();
		response = vResponse.extract().response();
		statusCode = response.statusCode();
		responseHeader = response.headers().toString();
		return response.asString();
	}
	
	//GET /employees/{id} response
	public String getSpecificEmployeeIdRecord(String employeeId){
		response =  given().log().all().spec(requestSpec).pathParam("id",employeeId).
		when().get(resourcePathGetEmployeeId).
		then().extract().response();
		statusCode = response.statusCode();
		return response.asString();
	}
	
	// POST /create response
	public String createEmployee(String name, String salary, String age){
		Employee employee = new Employee(name, salary, age);
		response = given().log().all().spec(requestSpec).contentType("application/json").body(employee).
				when().post(resourcePathCreateEmployees).
				then().extract().response();
		statusCode = response.statusCode();
		responseHeader = response.headers().toString();
		return response.asString();
		
	}
	
	//DELETE /delete/{id} response
	public String deleteEmployeeRecord(String employeeId){
		response = given().log().all().spec(requestSpec).pathParam("id",employeeId).
		when().delete(resourcePathDeleteEmployeeId).
		then().extract().response();
		statusCode = response.statusCode();
		return response.asString();
	}

	//PUT /update/{id} response
	public String updateEmployeeRecord(String employeeId, String name, String salary, String age){
		Employee employee = new Employee(name, salary, age);
		response = given().log().all().spec(requestSpec).pathParam("employeeId",employeeId).contentType("application/json").body(employee).
		when().put(resourcePathUpdateEmployeeId).
		then().extract().response();
		statusCode = response.statusCode();
		return response.asString();
	}
	
	public boolean verifyValueOfAFieldForEmployeeId(String employeeId, String fieldName, String fieldValue){
		return given().log().all().spec(requestSpec).pathParam("id",employeeId).
		when().get(resourcePathGetEmployeeId).
		then().extract().response().path(fieldName).equals(fieldValue);
	}
	
	
}
