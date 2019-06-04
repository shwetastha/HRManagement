package ca.myseneca.model;


import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	private DBUtil() {}
	
	private static Connection con;
	private static String driver;
	private static String url;
	private static String user;
	private static String password;

	static {
		try {
			
			//load properties
			Properties props = new Properties();
			Reader is = new FileReader("database.properties");
			
			props.load(is);
			
			driver = props.getProperty("driver");
			url = props.getProperty("url");
			user = props.getProperty("user");
			password = props.getProperty("password");
			
		} catch (Exception e) {
			throw new RuntimeException(e + "connection failed"); 
		}

	}
	public static Connection getConnection() {
		try {
			// 1 register drive
			Class.forName("oracle.jdbc.OracleDriver");
			
			// 2 connection with oracle
			Connection con = DriverManager.getConnection(url, user, password);
			
			return con;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void close(Connection con,Statement stat) {
		if(stat!=null) {
			try {
				stat.close();
			}catch(SQLException ex) {}
		}
		if(con!=null) {
			try {
				con.close();
			}catch(SQLException ex) {}
		}
	}
	public static void close(Connection con,Statement stat, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			}catch(SQLException ex) {}
		}
		if(stat!=null) {
			try {
				stat.close();
			}catch(SQLException ex) {}
		}
		if(con!=null) {
			try {
				con.close();
			}catch(SQLException ex) {}
		}
	}

	

}
