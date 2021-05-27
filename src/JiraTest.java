import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import files.ReUseableMethods1;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// login scenario to get the JSession ID from local Jira
        RestAssured.baseURI="http://localhost:8080";
		String Response=given().header("Content-Type", "application/json")
		.body("{ \r\n"
				+ " \"username\": \"mageedmax\",\r\n"
				+ " \"password\": \"Shabooly12\"\r\n"
				+ " }").log().all().when()
		.post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		JsonPath j=ReUseableMethods1.RawToJson(Response);
		String ID=j.getString("session.value");
        System.out.println("Extracted ID is:"+ID);
		// Add comment 
		given().log().all().pathParam("id","10104").header("Cookie", "JSESSIONID="+ID)
		.header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \"I just added a comment.\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").when().post("/rest/api/2/issue/{id}/comment").then().log().all();
		// Add attachment
		   given().log().all().header("X-Atlassian-Token","no-check").pathParam("id","10104").header("Cookie","JSESSIONID="+ID).multiPart("file",new File("jira.txt"))
		  .when().post("/rest/api/2/issue/{id}/attachments").then().log().all();
		 // Get Issue
		   String MessageResponse=given().log().all().pathParam("id","10104").header("Cookie","JSESSIONID="+ID).when().get("/rest/api/2/issue/{id}")
		   .then().log().all().extract().response().asString();

	}

}
