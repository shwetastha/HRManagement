package ca.myseneca.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBAccessHelperImpl implements DBAccessHelper {

	@Override
	public int getEmployeeID(String user, String password) {
		Connection connection = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        
        try {
        	
            connection = DBUtil.getConnection();
            String fSecurity = "{call F_SECURITY(?,?,?)}";
            statement = connection.prepareCall(fSecurity);

            statement.setString(1, user);
            statement.setString(2, password);
            statement.registerOutParameter(3, java.sql.Types.NUMERIC);
			
			// execute getEMPLOYEEByEmployeeId store procedure
            statement.executeUpdate();

            int sec = statement.getInt(3);
            
			System.out.println("Security Output : " + sec);
			return sec;
        }
        catch (SQLException e) {
            System.err.println("The error is:  " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close(); 
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("The error is:  " + e.getMessage());
            }
        } 
		return 0;
	}

	@Override
	public void addEmployee(Employee emp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Employee> getEmployeesByDepartmentID(int depid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployeeByID(int empid) {
		// TODO Auto-generated method stub
		return null;
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
