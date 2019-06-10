package ca.myseneca.model;

import java.rmi.Remote;
import java.util.ArrayList;

public interface DBAccessHelper extends Remote {

	public int getEmployeeID(String user, String password) throws Exception;
		
	public ArrayList<Employee> getAllEmployees()throws Exception;
	public ArrayList<Employee> getEmployeesByDepartmentID(int depid)throws Exception;
	public Employee getEmployeeByID(int empid)throws Exception;
	
	public void addEmployee(Employee emp)throws Exception;
	public int updateEmployee(Employee emp)throws Exception;
	public int deleteEmployeeByID(int empid)throws Exception;
	public boolean batchUpdate(String[] SQLs)throws Exception;
	
}
