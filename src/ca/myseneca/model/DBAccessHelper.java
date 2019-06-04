package ca.myseneca.model;

import java.util.ArrayList;

public interface DBAccessHelper {

	public int getEmployeeID(String user, String password);
		
	public ArrayList<Employee> getAllEmployees();
	public ArrayList<Employee> getEmployeesByDepartmentID(int depid);
	public Employee getEmployeeByID(int empid);
	
	public void addEmployee(Employee emp);
	public int updateEmployee(Employee emp);
	public int deleteEmployeeByID(int empid);
	
	public boolean batchUpdate(String[] SQLs);
	
}
