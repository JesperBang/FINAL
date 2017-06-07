package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** @author Ronnie Dalsgaard */
public class Connector {
    private final String HOST     = "eu-cdbr-west-01.cleardb.com";
    private final int    PORT     = 3306;
    private final String DATABASE = "heroku_65cc93a9800caef";
    private final String USERNAME = "b9a98146a86690"; 
    private final String PASSWORD = "5a54aa78";
    private Connection connection;
    
    public Connector() {
        try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
			connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
    
    public Connection getConnection(){
    	return connection;
    }
    
    public ResultSet doQuery(String query) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(query);
        return res;
    }
    
    public void doUpdate(String query) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }
}
