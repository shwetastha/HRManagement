CJV805 Assignment 1 – Summer 2019
Assignment Submission Form 
=============================================================================
 
I/we declare that the attached assignment is my/our own work in accordance with Seneca Academic Policy. No part of this assignment has been
copied manually or electronically from any other source (including web sites) or distributed to other students.

Name(s)  		Student ID(s)
Yonghao Chen    153623186
Shweta Shrestha 140687187
----------------------------------------------------------------------------------------------------------------------------------

Brief description:
This project includes four packages: 
-ca.myseneca.model
-ca.myseneca.rmi.client
-ca.myseneca.rmi.server
-ca.myseneca.test

ca.myseneca.model package consists of the following: 
1.DBUtil.java: Used for connection to database.
2.Employee.java: Employee java bean.
3.DBAccessHelper.java: Interface to help connect with the database.
4.DBAccessHelperImpl.java: Implements DBAccessHelper Interface, which provides data access management.

ca.myseneca.test package consists of the following: 
HRManagement.java: Used for testing functions of the model package class.

ca.myseneca.rmi.server package consists of the following:
Server.java: Provides service URL and server side support.

ca.myseneca.rmi.client package consists of the following:
Client.java: Connect to database, verify user access and manipulate data.

database.properties
This file consists of information required to connect to the database.

support folder consists of the following support documents:
SECURITY_TABLE.sql: Script that creates security table and inserts sample data.
SP_SECURITY.sql: Script for P_EMP_INFO stored procedure, F_SECURITY function and package.

Process to run the program:
1. First run rmiregistry in the bin/ca/myseneca/rmi/server folder.
2. Run Server.java class.
3. Run Client.java class.
4. Enter username and password to start the data manipulation.
5. The values for data manipulation are hardcoded for now. So all of the functions are tested.



