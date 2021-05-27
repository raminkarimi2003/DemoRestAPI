import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js=new JsonPath(payload.CoursePrice()); // call dummy API response from the 'payload class'
		// print the number of courses of API
		int count=js.getInt("courses.size()"); //get the size of courses array
		System.out.println(count);
		// Print purchase amount
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		// print title of the first course
		String courseTitle=js.get("courses[0].title");
        System.out.println(courseTitle);
        // print all the titles and respective prices
        for(int i=0;i<count;i++)
        {
        	String CoursesTitle=js.get("courses["+i+"].title");
        	int CoursesPrice=js.getInt("courses["+i+"].price");
        	System.out.println(CoursesTitle+" "+CoursesPrice);
        }
        // print number of courses sold by RPA course

        for(int i=0;i<count;i++)
        {
        	if(js.get("courses["+i+"].title").equals("RPA"))
        	{
        		System.out.println("Number if copies sold by RPM is "+js.getInt("courses["+i+"].copies"));
        		break;
        	}		
        }
        
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
