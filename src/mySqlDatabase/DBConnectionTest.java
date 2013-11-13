package mySqlDatabase;

import java.sql.ResultSet;

import domain.Patient;

public class DBConnectionTest {
	public static void main( String args[]) throws Exception{
		DBConnection dbcon=new DBConnection("jdbc:mysql://mysql.cs.mun.ca:3306/cs6713","cs6713","3176sc!");
		
		ResultSet result=dbcon.execQuery("select * from patient");
		while(result.next())
		{
			System.out.println(result.getString(3));
			System.out.println(result.getRow());
			
		}
		//dbcon.manipulateData("insert into formulary(medicine_id,medicine_spec) values(7,'qweqweqwqweqwe')");
	}
	
	

}
