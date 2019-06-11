package ca.myseneca.rmi.client;

import java.util.*;

import ca.myseneca.model.DBAccessHelper;
import ca.myseneca.model.Employee;

import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class Client {

	public static void main(String[] args) throws Exception {

		DBAccessHelper obj = (DBAccessHelper) Naming.lookup("rmi://localhost:5008/HRManagementService");

		Scanner reader = new Scanner(System.in);
		String exit = "no";

		while (exit.equalsIgnoreCase("no")) {
			System.out.println("Please Enter the Username:");
			String username = reader.nextLine();
			System.out.println("Please Enter the Password:");
			String password = reader.nextLine();
			System.out.println("Connecting to the Database...");
			int emp_id = obj.getEmployeeID(username, password);
			if (emp_id != 0) {
				System.out.println("Connected to the database!");

				ArrayList<Employee> list = obj.getAllEmployees();
				System.out.println("Total number of all employees is:" + list.size());

				ArrayList<Employee> list2 = obj.getEmployeesByDepartmentID(80);
				System.out.println("The number of employees in 80 department is:" + list2.size());

				Employee emp = obj.getEmployeeByID(900);
				System.out.println(emp.toString());

			} else {

				System.out.println("Invalid Username and Password.");

			}
			System.out.println("Do you want to exit? yes/no");
			exit = reader.nextLine();
		}

	}

	public static void AddEmployees(DBAccessHelper obj) throws Exception {
		Employee emp = new Employee();
		emp.setEmail("90190");
		emp.setLast_name("90190");
		emp.setJob_id("AC_ACCOUNT");
		emp.setHire_date(java.sql.Date.valueOf("2013-09-04"));
		emp.setEmployee_id(911);
		System.out.println(emp);

		obj.addEmployee(emp);
	}

	public static void batchUpdate(DBAccessHelper obj) throws Exception {
		String[] sqls = new String[3];
		sqls[0] = "INSERT INTO employees(employee_id,last_name,email,hire_date,job_id)"
				+ " VALUES (300,'300','300',TO_DATE('07-JUN-1994', 'dd-MON-yyyy'),'AC_ACCOUNT')";
		sqls[1] = "delete from employees where EMPLOYEE_ID = 301";
		sqls[2] = "update employees  set last_name = '401',email = '401',hire_date = TO_DATE('07-JUN-1994', 'dd-MON-yyyy'),job_id ='AC_ACCOUNT'  "
				+ "where employee_id = 401";
		// sqls[1] = "delete from employees where EMPLOYEE_ID = 304";
		// sqls[3] = "INSERT INTO
		// employees(employee_id,last_name,email,hire_date,job_id) VALUES
		// (644,'c41','d2eeee',TO_DATE('07-JUN-1994', 'dd-MON-yyyy'),'AC_ACCOUNT')";

		obj.batchUpdate(sqls);

	}

	public static void UpdateEmployees(DBAccessHelper obj) throws Exception {
		Employee emp = new Employee();
		emp.setEmail("901901");
		emp.setLast_name("901901");
		emp.setJob_id("AC_ACCOUNT");
		emp.setHire_date(java.sql.Date.valueOf("2013-09-04"));
		emp.setEmployee_id(901);
		System.out.println(emp);
		obj.updateEmployee(emp);
	}

}