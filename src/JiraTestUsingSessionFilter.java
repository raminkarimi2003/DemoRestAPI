import static io.restassured.RestAssured.*;
import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTestUsingSessionFilter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// login scenario to get the Session ID using session filter class
		// relaxhttpValidation is used when no certificate validation needed for rest assured
		SessionFilter session=new SessionFilter();
        RestAssured.baseURI="http://localhost:8080";
		String Response=given().relaxedHTTPSValidation().header("Content-Type", "application/json")
		.body("{ \r\n"
				+ " \"username\": \"mageedmax\",\r\n"
				+ " \"password\": \"Shabooly12\"\r\n"
				+ " }").log().all().filter(session).when()
		.post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		
		// Add comment 
		String comment="Hey!, I just modifid the comment";
		String AddCommentID=given().log().all().pathParam("key","10104") // pass the created id for the issue logged
		.header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \""+comment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all()
		.extract().response().asString();
		JsonPath js=new JsonPath(AddCommentID);
		String AddID=js.get("id");
		System.out.println("The ID for the comment just added is:"+AddID);
		// Add attachment
        given().log().all().header("X-Atlassian-Token","no-check").pathParam("key","10104")
        .header("Content-Type","multipart/form-data").multiPart("file",new File("jira.txt"))
        .filter(session).when().post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
        // Get Issue
		   String GETResponse=given().log().all()
				   .pathParam("key","10104").queryParam("fields","comment") // limiting the fields display to 'comments'
				   .filter(session).when().get("/rest/api/2/issue/{key}")
		   .then().log().all().extract().response().asString();
		   JsonPath js1=new JsonPath(GETResponse);
		   int fieldCommentSize=js1.getInt("fields.comment.comments.size()");
		   System.out.println(fieldCommentSize);
		   String ActualCommentID=null;
		   for(int i=0;i<fieldCommentSize;i++)
		   {
			   ActualCommentID=js1.get("fields.comment.comments["+i+"].id").toString();
			 
			  if(js1.get("fields.comment.comments["+i+"].id").toString().equalsIgnoreCase(AddID))
			  {
				   System.out.println(js1.get("fields.comment.comments["+i+"].body").toString());
				  
			  }
		   }
		   Assert.assertEquals(ActualCommentID, AddID);
	}
}


