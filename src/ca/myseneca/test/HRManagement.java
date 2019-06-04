package ca.myseneca.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ca.myseneca.model.DBAccessHelper;
import ca.myseneca.model.DBAccessHelperImpl;
import ca.myseneca.model.DBUtil;
import ca.myseneca.model.Employee;

public class HRManagement {
	static DBAccessHelper dbaccess = new DBAccessHelperImpl();

	public static void main(String[] args) throws Exception {
		System.out.println("Testing Connection: ");
		testGetConnection();
		System.out.println("*********************");
		System.out.println("Testing Security: ");
		testGetEmployeeID();
		System.out.println("*********************");
		System.out.println("Testing GetAllEmployees: ");
		testGetAllEmployees();
		System.out.println("*********************");
		System.out.println("Testing GetEmployeesByDepartmentID: ");
		testGetEmployeesByDepartmentID();
		System.out.println("*********************");
		System.out.println("Testing GetEmployeeByID: ");
		testGetEmployeeByID();
		

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
		
		String user = "hr";
		String password = "hr";
		System.out.println("User: "+user+", Password: "+password);
		System.out.println("Security Output: "+dbaccess.getEmployeeID(user, password));
		
		user = "java";
		password = "java";
		System.out.println("User: "+user+", Password: "+password);
		System.out.println("Security Output: "+dbaccess.getEmployeeID(user, password));
		
		user = "1234";
		password = "java";
		System.out.println("User: "+user+", Password: "+password);
		System.out.println("Security Output: "+dbaccess.getEmployeeID(user, password));
	}
	
	public static void testGetAllEmployees() {
		
		ArrayList<Employee> list= dbaccess.getAllEmployees();
		System.out.println("Total employee count: "+list.size());
	}
	
	public static void testGetEmployeesByDepartmentID() {
		
		ArrayList<Employee> list = new ArrayList<Employee>();
		list= dbaccess.getEmployeesByDepartmentID(80);
		System.out.println("Total employee count for department 80: "+list.size());
		list= dbaccess.getEmployeesByDepartmentID(0);
		System.out.println("Total employee count for department 0: "+list.size());
	}
	
	public static void testGetEmployeeByID() {
		Employee emp = dbaccess.getEmployeeByID(164);
		System.out.println(emp.toString());
	}
}
