package ir.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectorFactory {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	private static ConnectorFactory instance = null;
	
	private ConnectorFactory(){
		
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ConnectorFactory instance(){
		if (instance == null){
			instance = new ConnectorFactory();
		}
		try {
			instance.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
	
	public void close() throws SQLException {

			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}

	}

	
	private void connect() throws SQLException, ClassNotFoundException{
		
			//if (connect != null){
			//	close();
			//}
			if (connect == null || connect.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				
				connect = DriverManager.getConnection("jdbc:mysql://localhost/ir?" +
													  "user=root&password=dma666");
			}
		
		
	}
	public void update(String query) throws SQLException{
		try{
			connect();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			close();
		}
		
	}
	
	
	public void setPrepereStatement(String sql){
		try {
			preparedStatement = connect.prepareStatement(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ResultSet  executePrep(long val) throws SQLException, ClassNotFoundException{		
		preparedStatement.setLong(1, val);
		
	//	System.gc();
		// Result set get the result of the SQL query
		resultSet = preparedStatement.executeQuery();
//		System.gc();
		//writeResultSet(resultSet);
				return resultSet;


}
	
	public ResultSet  execute(String query) throws SQLException, ClassNotFoundException{

			statement = connect.createStatement();
		//	System.gc();
			// Result set get the result of the SQL query
			resultSet = statement
					.executeQuery(query);
		//	System.gc();
			//writeResultSet(resultSet);
						return resultSet;
		

	}
}
