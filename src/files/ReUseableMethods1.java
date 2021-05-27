package files;

import io.restassured.path.json.JsonPath;

public class ReUseableMethods1 {
	public static JsonPath RawToJson(String response)
	{
		JsonPath js=new JsonPath(response);
		return js;
	}

}
