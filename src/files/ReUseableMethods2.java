package files;

import io.restassured.path.json.JsonPath;

public class ReUseableMethods2 {
	public static JsonPath RawToJson(String getResponse)
	{
		JsonPath jsGet=new JsonPath(getResponse);
		
		return jsGet;
		
	}

}
