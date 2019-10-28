package dummyapiexample.restassured.methods;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//Serializable class to json body

@JsonPropertyOrder({"name","salary","age"})
public class Employee {
	private String name;
	private String salary;
	private String age;
	
	public Employee(String sName, String sSalary, String sAge){
		this.name = sName;
		this.salary = sSalary;
		this.age =sAge;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSalary(){
		return salary;
	}
	
	public String getAge(){
		return age;
	}
}
