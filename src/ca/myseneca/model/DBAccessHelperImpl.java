package ca.myseneca.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.internal.OracleTypes;

public class DBAccessHelperImpl implements DBAccessHelper {

	@Override
	public int getEmployeeID(String user, String password) {
		Connection connection = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		int sec = 0;
		try {

			connection = DBUtil.getConnection();
			String fSecurity = "{? = call P_SECURITY.F_SECURITY(?,?)}";
			statement = connection.prepareCall(fSecurity);

			statement.registerOutParameter(1, java.sql.Types.NUMERIC);
			statement.setString(2, user);
			statement.setString(3, password);

			// execute getEMPLOYEEByEmployeeId store procedure
			statement.execute();

			sec = statement.getInt(1);

		} catch (SQLException e) {
			System.err.println("The error is:  " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, statement);
		}
		return sec;

	}

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
		} catch (SQLException e) {
			System.err.println("The error is:  " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, statement);
		}
		return employeeList;
	}

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
		} catch (SQLException e) {
			System.err.println("The error is:  " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, statement);
		}
		return employeeList;
	}

	@Override
	public Employee getEmployeeByID(int empid) {
		Employee emp = new Employee();
		Connection connection = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DBUtil.getConnection();
			String P_EMP_INFO = "{ call P_SECURITY.P_EMP_INFO(?,?) }";
			statement = connection.prepareCall(P_EMP_INFO);
			statement.setInt(1, empid);
			statement.registerOutParameter(2, OracleTypes.CURSOR);

			statement.execute();
			resultSet = (ResultSet)statement.getObject(2);
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
		} catch (SQLException e) {
			System.err.println("The error is:  " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.close(connection, statement);
		}
		return emp;
	}

	@Override
	public void addEmployee(Employee emp) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateEmployee(Employee emp) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEmployeeByID(int empid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean batchUpdate(String[] SQLs) {
		// TODO Auto-generated method stub
		return false;
	}

}
