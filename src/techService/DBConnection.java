package techService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

/**
 * @author safwan
 *
 */
public class DBConnection {
	
	/**
	 * Connection Class to maintain main DB operations 
	 */
	private Connection dbConnection;
	
	/**
	 * @param serverUrl
	 * @param userName
	 * @param password
	 * Constructor initiate the connection to the database
	 */
	public DBConnection(String serverUrl,String userName,String password) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		dbConnection=DriverManager.getConnection(serverUrl,userName,password);	
	}
	
	
	/**
	 * @return connection to the database
	 */
	public Connection getDbConnection() {
		return dbConnection;
	}
	
	
	/**
	 * Close connection to the database
	 */
	public void closeConnection() throws SQLException{
		dbConnection.close();
	}
	
	/**
	 * @param sql query statement
	 * @return the execution of the SQL statement 
	 */
	public ResultSet execQuery(String sql) throws Exception {
		
		PreparedStatement sqlStat=(PreparedStatement) dbConnection.prepareStatement(sql);
		ResultSet result=sqlStat.executeQuery();
		return result;
	}
	
	/**
	 * @param sql insert/delete/update statement
	 * this method used to manipulate data in the database
	 */
	public void manipulateData(String sql) throws Exception{
		PreparedStatement sqlStat=(PreparedStatement) dbConnection.prepareStatement(sql);
		sqlStat.executeUpdate();
	}
}
