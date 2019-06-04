package ca.myseneca.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ca.myseneca.model.DBAccessHelper;
import ca.myseneca.model.DBAccessHelperImpl;
import ca.myseneca.model.DBUtil;

public class HRManagement {

	public static void main(String[] args) throws Exception {
		System.out.println("Testing Connection: ");
		testGetConnection();
		System.out.println("*********************");
		System.out.println("Testing Security: ");
		

	}
	
	public static void testGetConnection() {
		Connection con = DBUtil.getConnection();

		ResultSet rs = null;
		PreparedStatement pst = null;
		try {

			String sql = "select first_name||' '||last_name \"FULL Name\",EMAIL,salary,employee_id from employees  where employee_id = '100'";

			// PreparedStatement to set parameter
			pst = con.prepareStatement(sql);

			rs = pst.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString(1) + "  " + rs.getString(3));
				System.out.println("Connection SuccessFul");
			}

		} catch (NullPointerException e) {
			System.out.println("Exception" + e.getMessage());

		} catch (SQLException se) {
			System.out.println("Exception" + se.getMessage());
			se.printStackTrace();

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());

		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public static void testGetEmployeeID() {
//		DBAccessHelper dbaccess = new DBAccessHelperImpl();
//		dbaccess.getEmployeeID(user, password)
	}
}
