import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import files.ReUseableMethods1;
import files.ReUseableMethods2;

import  static io.restassured.RestAssured.*;
import  static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;
public class Basic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 3 Principles of Rest Assured API
		// given - all the inputs info.
		// when - Submit API( GET/PUT/POST/DELETE), resource and HTTP
        // then - Validation of response
		// Add place-> update place with new address-> Get place to validate if new address is present

		RestAssured.baseURI= "https://rahulshettyacademy.com"; //First thing
		String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
		        .body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		 // extract a portion of the response from POST
		 // json path get string and parse it as json
		JsonPath js=ReUseableMethods1.RawToJson(response);
		String PlaceID=js.getString("place_id");
		// using Update API using PUT method
		String NewAddress="70 Fall walk, USA";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+PlaceID+"\",\r\n"
				+ "\"address\":\""+NewAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		         .when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
		        .body("msg",equalTo("Address successfully updated"));
		// extract a portion of response from PUT
		//JsonPath jsu=new JsonPath(UpdateResponse);
		
		// Using GET API using GET method
        
		String getResponse=given().log().all().queryParams("key", "qaclick123").queryParams("place_id",PlaceID)
		.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath jsGET=ReUseableMethods2.RawToJson(getResponse); // return object as JsonPath
		String actualAddress=jsGET.get("address");
		System.out.println("The address returned by GET API is: "+actualAddress);
		Assert.assertEquals(actualAddress, NewAddress);
	}

}
	
