package MySqlDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class DBConnection {
	
	private Connection dbConnection;
	
	public DBConnection(String serverUrl,String userName,String password) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		dbConnection=DriverManager.getConnection(serverUrl,userName,password);	
	}
	
	public Connection getDbConnection() {
		return dbConnection;
	}
	
	/*public void setDbConnection(String serverUrl,String userName,String password) throws Exception{
		
		Class.forName("com.mysql.jdbc.Driver");
		dbConnection=DriverManager.getConnection(serverUrl,userName,password);	
	}*/
	

	public ResultSet execQuery(String sql) throws Exception {
		
		PreparedStatement sqlStat=(PreparedStatement) dbConnection.prepareStatement(sql);
		ResultSet result=sqlStat.executeQuery();
		return result;
	}
	
	public void manipulateData(String sql) throws Exception{
		PreparedStatement sqlStat=(PreparedStatement) dbConnection.prepareStatement(sql);
		sqlStat.executeUpdate();
	}

	
	
	
	/*public static void main(String[] args) throws Exception
	{
		//Accessing driver from the JAR file
		//Class.forName("com.mysql.jdbc.Driver");
		
		//Create variable for connection
		//Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prescriptionsys","root","System");
		
		//create the query
		//PreparedStatement sqlStat=(PreparedStatement) con.prepareStatement("select * from formulary");
		//PreparedStatement sqlStat=(PreparedStatement) con.prepareStatement("insert into formulary (medicine_id,medicine_spec) values(6,'kkkkk') ");
		//sqlStat.executeUpdate();
		//create a variable to execute the query
		//ResultSet result=sqlStat.executeQuery();
		
		while(result.next())
		{
			System.out.println(result.getString(1)+" "+result.getString(2));
			
			
		}
	}*/

}
