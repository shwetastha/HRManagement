package ca.myseneca.rmi.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import ca.myseneca.model.DBAccessHelperImpl;

/**
 * Server This class is the server class.
 * 
 * @author  Yonghao Chen, Shweta Shrestha
 *
 */

public class Server {

	protected Server() throws RemoteException {
		super();
	}

	/**
	 * This method is the main method that starts the server and binds the url
	 * rmi://localhost:5008/HRManagementService.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		DBAccessHelperImpl obj = new DBAccessHelperImpl();

		// registry RMI service in Java code
		LocateRegistry.createRegistry(5008);
		Naming.rebind("rmi://localhost:5008/HRManagementService", obj);

		System.out.println("Server started : ");

	}

}