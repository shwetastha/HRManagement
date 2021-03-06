package ca.myseneca.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import ca.myseneca.model.DBAccessHelper;
import ca.myseneca.model.DBAccessHelperImpl;
import ca.myseneca.model.DBUtil;
import ca.myseneca.model.Employee;

/**
 * HRManagement This class acts as a test class to test all the functionality of
 * the DBAccessHelperImpl.
 * 
 * @author Shweta Shrestha,  Yonghao Chen
 *
 */
public class HRManagement {
	static DBAccessHelper dbaccess;

	public static void main(String[] args) throws Exception {
		dbaccess = new DBAccessHelperImpl();
		Scanner reader = new Scanner(System.in);
		String exit = "no";

		while (exit.equalsIgnoreCase("no")) {
			System.out.println("Please Enter the Username:");
			String username = reader.nextLine();
			System.out.println("Please Enter the Password:");
			String password = reader.nextLine();
			System.out.println("Connecting to the Database...");
			int emp_id = dbaccess.getEmployeeID(username, password);
			if (emp_id != 0) {
				System.out.println("Connected to the database!");

				System.out.println("*********************");
				System.out.println("Employee Information: ");
				testGetEmployeeByID(emp_id);
				System.out.println("*********************");
				System.out.println("Testing addEmployee: ");
				testAddEmployees();
				System.out.println("*********************");
				System.out.println("Testing deleteEmployeeByID: ");
				testDeleteEmployees();
				System.out.println("*********************");
				System.out.println("Testing updateEmployeeByID: ");
				testUpdateEmployees();

				System.out.println("*********************");
				System.out.println("Testing batchUpdate: ");
				testbatchUpdate();

				System.out.println("*********************");
				System.out.println("Testing GetAllEmployees: ");
				testGetAllEmployees();
				System.out.println("*********************");
				System.out.println("Testing GetEmployeesByDepartmentID: ");
				testGetEmployeesByDepartmentID();

			} else {

				System.out.println("Invalid Username and Password.");

			}
			System.out.println("Do you want to exit? yes/no");
			exit = reader.nextLine();
		}

	}

	public static void testUpdateEmployees() throws Exception {
		Employee emp = new Employee();
		emp.setEmail("vvliuvvliu");
		emp.setLast_name("vvliu");
		emp.setJob_id("AC_ACCOUNT");
		emp.setHire_date(java.sql.Date.valueOf("2013-09-04"));
		emp.setEmployee_id(403);
		System.out.println(emp);

		dbaccess.updateEmployee(emp);
	}

	public static void testbatchUpdate() throws Exception {
		String[] sqls = new String[6];
		sqls[0] = "delete from employees where EMPLOYEE_ID = 554";
		sqls[1] = "delete from employees where EMPLOYEE_ID = 314";
		sqls[2] = "delete from employees where EMPLOYEE_ID = 544";
		sqls[3] = "INSERT INTO employees(employee_id,last_name,email,hire_date,job_id) VALUES (544,'c4','d2eeee',TO_DATE('07-JUN-1994', 'dd-MON-yyyy'),'AC_ACCOUNT')";
		sqls[4] = "INSERT INTO employees(employee_id,last_name,email,hire_date,job_id) VALUES (554,'c5','dee',TO_DATE('07-JUN-1994', 'dd-MON-yyyy'),'AC_ACCOUNT')";
		sqls[5] = "update employees  set last_name = 'c00',email = 'dc00d',hire_date = TO_DATE('07-JUN-1994', 'dd-MON-yyyy'),job_id ='AC_ACCOUNT'  where employee_id = 554";
		System.out.println(dbaccess.batchUpdate(sqls));

	}

	public static void testAddEmployees() throws Exception {
		Employee emp = new Employee();
		emp.setEmail("chenyong122");
		emp.setLast_name("chenaaa2");
		emp.setJob_id("AC_ACCOUNT");
		emp.setHire_date(java.sql.Date.valueOf("2013-09-04"));
		emp.setEmployee_id(404);
		System.out.println(emp);

		dbaccess.addEmployee(emp);
	}

	public static void testDeleteEmployees() throws Exception {

		dbaccess.deleteEmployeeByID(404);

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

	public static void testGetEmployeeID() throws Exception {

		String user = "hr";
		String password = "hr";
		System.out.println("User: " + user + ", Password: " + password);
		System.out.println("Security Output: " + dbaccess.getEmployeeID(user, password));

		user = "java";
		password = "java";
		System.out.println("User: " + user + ", Password: " + password);
		System.out.println("Security Output: " + dbaccess.getEmployeeID(user, password));

		user = "1234";
		password = "java";
		System.out.println("User: " + user + ", Password: " + password);
		System.out.println("Security Output: " + dbaccess.getEmployeeID(user, password));
	}

	public static void testGetAllEmployees() throws Exception {

		ArrayList<Employee> list = dbaccess.getAllEmployees();
		System.out.println("Total employee count: " + list.size());
	}

	public static void testGetEmployeesByDepartmentID() throws Exception {

		ArrayList<Employee> list = new ArrayList<Employee>();
		list = dbaccess.getEmployeesByDepartmentID(80);
		System.out.println("Total employee count for department 80: " + list.size());
		list = dbaccess.getEmployeesByDepartmentID(0);
		System.out.println("Total employee count for department 0: " + list.size());
	}

	public static void testGetEmployeeByID(int emp_id) throws Exception {
		Employee emp = dbaccess.getEmployeeByID(emp_id);
		System.out.println(emp.toString());
	}
}
