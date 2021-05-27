import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	@Test
	public void Amount()
	{
		JsonPath js=new JsonPath(payload.CoursePrice()); // call dummy API response from the 'payload class'
		// print the number of courses of API
		int count=js.getInt("courses.size()"); //get the size of courses array
		// Print purchase amount
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		// verify if sum of all prices matches with total purchase amounts
        int PriceSum=0;
        for(int i=0;i<count;i++)
        {
        	PriceSum=js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies")+PriceSum;
        	
        }
        if (PriceSum==purchaseAmount)
        	System.out.println("Sum of all price purchased verified to be equal to purchase amounts for "+PriceSum);
	   Assert.assertEquals(PriceSum, purchaseAmount);
	}

}
