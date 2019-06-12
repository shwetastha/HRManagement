package ca.myseneca.model;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * DBUtil This class is an utility class that holds functions that connects with
 * the Database.
 * 
 * @author Yonghao Chen
 *
 */
public class DBUtil {
	private DBUtil() {
	}

	private static Connection con;
	private static String driver;
	private static String url;
	private static String Ociurl;
	private static String user;
	private static String password;

	/**
	 * Static Block to get all the properties from the database.properties file.
	 */
	static {
		try {

			// load properties
			Properties props = new Properties();
			Reader is = new FileReader("database.properties");

			props.load(is);

			driver = props.getProperty("driver");
			url = props.getProperty("url");
			Ociurl = props.getProperty("Ociurl");
			user = props.getProperty("user");
			password = props.getProperty("password");

		} catch (Exception e) {
			throw new RuntimeException(e + "connection failed");
		}

	}

	/**
	 * getOciConnection connects to the Oracle database using the JDBC OCI.
	 * 
	 * @return Connection This returns connection object after a successful
	 *         connection with the database.
	 * 
	 */
	public static Connection getOciConnection() {
		try {
			// 1 register OCI drive
			Class.forName(driver);

			// 2 connection with oracle
			Connection con = DriverManager.getConnection(Ociurl, user, password);

			return con;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * getConnection connects to the Oracle database using the JDBC Thin.
	 * 
	 * @return Connection This returns connection object after a successful
	 *         connection with the database.
	 * 
	 */
	public static Connection getConnection() {
		try {
			// 1 register drive
			Class.forName(driver);

			// 2 connection with oracle
			Connection con = DriverManager.getConnection(url, user, password);

			return con;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method closes the statement and the connection object.
	 * 
	 * @param con  Connection This method takes connection object as the first
	 *             parameter.
	 * @param stat Statement This method takes Statement object as the second
	 *             parameter.
	 */
	public static void close(Connection con, Statement stat) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException ex) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
			}
		}
	}

	/**
	 * This method closes the statement, resultset and the connection object.
	 * 
	 * @param con  Connection This method takes connection object as the first
	 *             parameter.
	 * @param stat Statement This method takes Statement object as the second
	 *             parameter.
	 * @param rs   ResultSet This method takes ResultSet object as the third
	 *             parameter.
	 */
	public static void close(Connection con, Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException ex) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
			}
		}
	}

}
