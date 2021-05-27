import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class Oauth2Test {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		// Get the Authorization code through Selenium from the browser via google( when some one login)
//		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver.exe");
//        WebDriver driver=new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php ");
//	    
//        Thread.sleep(2000);
	    String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g7T8dzCvGItnZMYNr4xOVHe-V-lqSLOWsQnFMKvp8flId7UaqaLrelpd945a00vVA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
	    String code=url.split("code=")[1]; // store array[1] in code variable
	    String accessCode=code.split("&scope")[0];
	    System.out.println(accessCode);
	    
	    // Post & receive access token by submitting code,client_id/client_secret,redirect_uri,grant_type
	    String Response=given().urlEncodingEnabled(false).queryParams("code", accessCode)
	    .queryParams("scope","https://www.googleapis.com/auth/userinfo.email")
	    .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj")
	    .queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
	    .queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
	    .queryParams("grant_type","authorization_code").when().log().all()
	    .post("https://www.googleapis.com/oauth2/v4/token").asString();
	    System.out.println(Response);
	   JsonPath js=new JsonPath(Response);
	   String token=js.get("access_token");
	   System.out.println(token);
	   
	   // Gain entry to the site by submitting access token received earlier by GET in the form of query 
	   String response=given().queryParam("access_token", token).when().log().all().get("https://rahulshettyacademy.com/getCourse.php")
	   .asString();
	   System.out.println(response);
	   
	}

}
