package ca.myseneca.rmi.server;

import ca.myseneca.model.DBAccessHelper;
import ca.myseneca.model.DBAccessHelperImpl;
import ca.myseneca.model.DBUtil;
import ca.myseneca.model.Employee;

import java.io.Serializable;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
public class Server  {

	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		DBAccessHelperImpl obj = new DBAccessHelperImpl();
		
		// registry RMI service in Java code
		LocateRegistry.createRegistry(5008);
		Naming.rebind("rmi://localhost:5008/HRManagementService", obj);
		
		System.out.println("Server started : ");
		
	}

}