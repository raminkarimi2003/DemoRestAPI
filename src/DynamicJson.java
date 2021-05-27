import org.testng.annotations.Test;

import files.ReUseableMethods1;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
    @Test
	public void addBook()
	{
		String Response=RestAssured.baseURI="https://www.rahulshettyacademy.com";
		given().header("Content-Type","application/json").body(payload.AddBook())
		.when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js=ReUseableMethods1.RawToJson(Response);
		String id=js.get("ID");
		System.out.println(id);
		
	}
			
}
