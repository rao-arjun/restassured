package dummyapiexample.restassured.methods;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//Serializable class to json body

@JsonPropertyOrder({"name","salary","age"})
public class Employee {
	private String name;
	private String salary;
	private int age;
	
	public Employee(String sName, String sSalary, int  age){
		this.name = sName;
		this.salary = sSalary;
		this.age = age;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSalary(){
		return salary;
	}
	
	public int getAge(){
		return age;
	}
}
