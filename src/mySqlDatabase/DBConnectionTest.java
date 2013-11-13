package mySqlDatabase;

import java.sql.ResultSet;

public class DBConnectionTest {
	public static void main( String args[]) throws Exception{
		DBConnection dbcon=new DBConnection("jdbc:mysql://localhost:3306/prescriptionsys","root","System");
		
		ResultSet result=dbcon.execQuery("select * from formulary");
		while(result.next())
		{
			System.out.println(result.getString(1)+" "+result.getString(2));
			
			
		}
		dbcon.manipulateData("insert into formulary(medicine_id,medicine_spec) values(7,'qweqweqwqweqwe')");
	}
	
	

}
