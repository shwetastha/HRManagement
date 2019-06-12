package ca.myseneca.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

/**
 * DBAccessHelperImpl 
 * DBAccessHelperImpl class implements DBAccessHelper
 * interface. It implements methods that access Database.
 * 
 * @author Shweta Shrestha, Yonghao Chen
 * 
 */
public class DBAccessHelperImpl extends UnicastRemoteObject implements DBAccessHelper {

	public DBAccessHelperImpl() throws RemoteException {
		super();
	}

	/**
	 * This method updates the database with the values set in the Employee object.
	 * This method uses prepared statement to accomplish this task.
	 * 
	 * @param Employee This method takes Employee object as a parameter that needs
	 *                 to be updated in the database.
	 * @return int This method returns row count that has been affected by the
	 *         update.
	 */
	@Override
	public int updateEmployee(Employee emp) {

		// use oci connection
		Connection con = DBUtil.getOciConnection();
		// Connection con = DBUtil.getConnection();
		PreparedStatement pst = null;
		int i = 0;
		try {
			con.setAutoCommit(false);
			String sql = "update employees " + "set last_name = ?,email = ?,hire_date = ?,job_id =?"
					+ "where employee_id = ?";
			pst = con.prepareStatement(sql);

			pst.setObject(1, emp.getLast_name());
			pst.setObject(2, emp.getEmail());
			pst.setObject(3, emp.getHire_date());
			pst.setObject(4, emp.getJob_id());
			pst.setObject(5, emp.getEmployee_id());

			// Execute the update statement
			i = pst.executeUpdate();
			con.commit();
			System.out.println("\nupdateEmployee Information for id: "+emp.getEmployee_id());
			System.out.println("Rows updated:"+i);

		} catch (IllegalArgumentException se) {
			System.out.println("Exception :" + se.getMessage());
			se.printStackTrace();

		} catch (SQLException se) {
			System.out.println("Exception :" + se.getMessage());
			se.printStackTrace();

		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
					pst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}

		}
		return i;
	}

	/**
	 * This method inserts a new row in the database with the information provided
	 * by the Employee object. This method uses PreparedStatement to accomplish this
	 * task.
	 * 
	 * @return Nothing
	 * @param Employee This method takes in Employee object as a parameter which
	 *                 will be inserted to the database.
	 */
	@Override
	public void addEmployee(Employee emp) {
		Connection con = DBUtil.getOciConnection();
		// Connection con = DBUtil.getConnection();
		PreparedStatement pst = null;
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO employees (employee_id,last_name,email,hire_date,job_id) VALUES (?,?,?,?,?)";
			pst = con.prepareStatement(sql);

			pst.setObject(1, emp.getEmployee_id());
			pst.setObject(2, emp.getLast_name());
			pst.setObject(3, emp.getEmail());
			pst.setObject(4, emp.getHire_date());
			pst.setObject(5, emp.getJob_id());

			// Execute the update statement
			int i= pst.executeUpdate();
			con.commit();
			System.out.println("\naddEmployee for id: "+emp.getEmployee_id());
			System.out.println("Row inserted: "+i);

		} catch (IllegalArgumentException se) {
			System.out.println("Exception :" + se.getMessage());
			se.printStackTrace();

		} catch (SQLException se) {
			System.out.println("Exception :" + se.getMessage());
			se.printStackTrace();

		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
					pst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}

		}

	}

	/**
	 * This method uses the employee-id parameter to delete the row in the database.
	 * This method uses updatable resultset to accomplish this task.
	 * 
	 * @returns int This method returns integer value of the row that was affected
	 *          by the delete operation.
	 * @param int This method takes employee_id as a parameter.
	 */
	@Override
	public int deleteEmployeeByID(int empid) {

		Connection con = DBUtil.getOciConnection();
		// Connection con = DBUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet resultSet = null;
		int i = 0;
		try {

			con.setAutoCommit(false);

			String sql = "select employee_id from employees where employee_id = ?";

			// PreparedStatement to set parameter

			pst = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pst.setObject(1, empid);
			// Execute the update statement
			resultSet = pst.executeQuery();
			int rowCount = 0;
			while (resultSet.next()) {

				resultSet.deleteRow();
				rowCount++;
			}
			System.out.println("\ndeleteEmployeeByID for id: "+empid);
			System.out.println("Rows Deleted: " + rowCount);
			con.commit();

		} catch (SQLException se) {
			// Handle errors for JDBC
			System.out.println("Exception :" + se.getMessage());
			se.printStackTrace();

		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
					pst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		}
		return i;
	}

	/**
	 * This method verifies if the combination of username and password provvided is
	 * an active user in the database. This method calls storedProcedure with the
	 * use of CallableStatement in the database to accomplish this task.
	 * 
	 * @return int This method returns employee_id of the combination of the
	 *         username and password is verified. Otherwise, 0 will be returned.
	 * @param String user This method takes user name as the first parameter.
	 * @param String password This method takes password as the second parameter.
	 */
	@Override
	public int getEmployeeID(String user, String password) {
		Connection connection = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		int sec = 0;
		try {

			connection = DBUtil.getOciConnection();
			String fSecurity = "{? = call P_SECURITY.F_SECURITY(?,?)}";
			statement = connection.prepareCall(fSecurity);

			statement.registerOutParameter(1, java.sql.Types.NUMERIC);
			statement.setString(2, user);
			statement.setString(3, password);

			// execute getEMPLOYEEByEmployeeId store procedure
			statement.execute();

			sec = statement.getInt(1);
			
			System.out.println("\ngetEmployeeID for user: "+user);
			System.out.println("Employee Id retrieved: "+sec);

		} catch (SQLException e) {
			System.err.println("The error is:  " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, statement);
		}
		return sec;

	}

	/**
	 * This method returns all the employee rows from the database. This method uses
	 * Statement and static SQL statement to accomplish this task.
	 * 
	 * @return ArrayList<Employee> This method returns the list of employees.
	 */
	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DBUtil.getConnection();
			String query = "select * from employees";
			statement = connection.createStatement();

			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				Employee emp = new Employee();
				emp.setEmployee_id(resultSet.getInt("employee_id"));
				emp.setFirst_name(resultSet.getString("first_name"));
				emp.setLast_name(resultSet.getString("last_name"));
				emp.setEmail(resultSet.getString("email"));
				emp.setPhone_number(resultSet.getString("phone_number"));
				emp.setHire_date(resultSet.getDate("hire_date"));
				emp.setJob_id(resultSet.getString("job_id"));
				emp.setSalary(resultSet.getDouble("salary"));
				emp.setCommission_pct(resultSet.getDouble("commission_pct"));
				emp.setManager_id(resultSet.getInt("manager_id"));
				emp.setDepartment_id(resultSet.getInt("department_id"));

				employeeList.add(emp);
			}
			System.out.println("\ngetAllEmployees");
		} catch (SQLException e) {
			System.err.println("The error is:  " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, statement);
		}
		return employeeList;
	}

	/**
	 * This method returns list of employees that are associated with the
	 * department_id that is provided.
	 * 
	 * @return ArrayList<Employee> This method returns lst of employees who work in
	 *         the same deartment_id that was requested.
	 * @param int department_id This method takes in the department_id.
	 */
	@Override
	public ArrayList<Employee> getEmployeesByDepartmentID(int depid) {
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DBUtil.getConnection();
			String query = "select * from employees where department_id=?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, depid);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Employee emp = new Employee();
				emp.setEmployee_id(resultSet.getInt("employee_id"));
				emp.setFirst_name(resultSet.getString("first_name"));
				emp.setLast_name(resultSet.getString("last_name"));
				emp.setEmail(resultSet.getString("email"));
				emp.setPhone_number(resultSet.getString("phone_number"));
				emp.setHire_date(resultSet.getDate("hire_date"));
				emp.setJob_id(resultSet.getString("job_id"));
				emp.setSalary(resultSet.getDouble("salary"));
				emp.setCommission_pct(resultSet.getDouble("commission_pct"));
				emp.setManager_id(resultSet.getInt("manager_id"));
				emp.setDepartment_id(resultSet.getInt("department_id"));

				employeeList.add(emp);
			}
			System.out.println("\ngetEmployeesByDepartmentID for department id:"+depid);
			System.out.println("Rows selected: "+employeeList.size());
		} catch (SQLException e) {
			System.err.println("The error is:  " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, statement);
		}
		return employeeList;
	}

	/**
	 * This method provides the employee information with the employee_id provided.
	 * This method uses OracleCallableStatement which calls the StoredProcedure in
	 * the database to accomplish this task.
	 * 
	 * @return Employee This method returns the Employee object with the information
	 *         retrieved from the database.
	 * @param int employee_id This method takes in the employee_id to get the
	 *            information.
	 */
	@Override
	public Employee getEmployeeByID(int empid) {
		Employee emp = new Employee();
		Connection connection = null;
		OracleCallableStatement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DBUtil.getConnection();
			String P_EMP_INFO = "{ call P_SECURITY.P_EMP_INFO(?,?) }";
			statement = (OracleCallableStatement) connection.prepareCall(P_EMP_INFO);
			statement.setInt(1, empid);
			statement.registerOutParameter(2, OracleTypes.CURSOR);

			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()) {

				emp.setEmployee_id(resultSet.getInt("employee_id"));
				emp.setFirst_name(resultSet.getString("first_name"));
				emp.setLast_name(resultSet.getString("last_name"));
				emp.setEmail(resultSet.getString("email"));
				emp.setPhone_number(resultSet.getString("phone_number"));
				emp.setHire_date(resultSet.getDate("hire_date"));
				emp.setJob_id(resultSet.getString("job_id"));
				emp.setSalary(resultSet.getDouble("salary"));
				emp.setCommission_pct(resultSet.getDouble("commission_pct"));
				emp.setManager_id(resultSet.getInt("manager_id"));
				emp.setDepartment_id(resultSet.getInt("department_id"));

			}
			System.out.println("\ngetEmployeeByID for ID :"+empid);
			
		} catch (SQLException e) {
			System.err.println("The error is:  " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, statement);
		}
		return emp;
	}

	/**
	 * This method executes batch update with all the sql statements provided in the
	 * parameter.
	 * 
	 * @return boolean This method returns true if the batch update was successful
	 *         and false if it was unsuccessful.
	 * @param String[] SQL statements This method takes Strinf array with the sql
	 *                 statements that needs to be executed.
	 */
	@Override
	public boolean batchUpdate(String[] SQLs) {
		boolean flag = false;
		Connection con = DBUtil.getConnection();
		PreparedStatement pst = null;

		try {
			con.setAutoCommit(false);

			for (int i = 0; i < SQLs.length; i++) {
				String sql = SQLs[i];

				pst = con.prepareStatement(sql);
				pst.addBatch();
				pst.executeBatch();
			}

			con.commit();
			flag = true;
			// Execute the update statement
			System.out.println("\nSQL scripts executed: " + SQLs.length);
			

		} catch (SQLException se) {
			try {
				con.rollback();
				flag = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Rollback failed");
			}
			System.out.println("Exception :" + se.getMessage());
			System.out.println("Update failed, Rollback");
			se.printStackTrace();

		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
					pst.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}

		}

		return flag;
	}

}
