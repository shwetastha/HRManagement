package ca.myseneca.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ca.myseneca.model.DBUtil;


import java.sql.Statement;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.DriverManager;
import ca.myseneca.model.DBUtil;
import java.util.Properties;

public class HRManagement {

	public static void main(String[] args) throws Exception {

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
			}

		} catch (NullPointerException e) {
			System.out.println("Exception" + e.getMessage());

		} catch (SQLException se) {
			System.out.println("Exception" + se.getMessage());
			se.printStackTrace();

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());

		} finally {
			rs.close();
			pst.close();
			con.close();
		}

	}
}
