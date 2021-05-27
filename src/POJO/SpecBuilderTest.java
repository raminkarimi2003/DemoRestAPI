package POJO;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GoogleApi ga=new GoogleApi(); // create object for the parent class
		Location l=new Location();  // set a new object for the class  Location and access it here
		// first set the Lang and Lng variables
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ga.setLocation(l); // send the object l to setter method
		ga.setAccuracy(50);
		ga.setAddress("29, side layout, cohen 09");
		ga.setName("Frontline house");
		ga.setPhone_number("(+91) 983 893 3937");
		// create a list of string array
		List<String> tp=new ArrayList<String>(); 
		//  add the strings into the  Array list
		tp.add("shoe park");
		tp.add("shop");
		ga.setTypes( tp); // use the setter to set the array list
		ga.setWebsite("http://google.com");
		ga.setLanguage("French-IN");
		
		ResponseSpecification resp=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
		.setContentType(ContentType.JSON).build();
		
		RequestSpecification res=given().log().all().spec(req)
		.body(ga);
		String responseAsString=res.when().post("/maps/api/place/add/json").then().spec(resp).extract().response().asString();
       // String responseAsString=response.asString();
		System.out.println(responseAsString);
	}

}
