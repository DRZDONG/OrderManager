import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * This program tests the version of the publication database tables for Assignment 5 
 * that uses relation tables for the PublishedBy and PublishedIn relations. The sample
 * data is stored in a tab-separated data file The columns of the data file are:
 * pubName, pubCity, jnlName, jnlISSN, artTitle, artDOI, auFamiily, auGiven, auORCID
 * 
 * @author philip gust
 */
public class TestOrderManager {
	
	public static void main(String[] args) {
	    // the default framework is embedded
	    String protocol = "jdbc:derby:";
	    String dbName = "ordermanager";
		String connStr = protocol + dbName+ ";create=true";

	    // tables tested by this program
		String dbTables[] = {
			"Product", "InventoryRecord", "Customer", "OrderTable", "OrderRecord"		
    	    };

		// name of data file
		String[] fileName = {"product.txt","customer.txt", "order.txt", "inventory.txt"};

		Properties props = new Properties(); // connection properties
        // providing a user name and password is optional in the embedded
        // and derbyclient frameworks
        props.put("user", "user1");
        props.put("password", "user1");
    

		try (
			
			// open product file
			BufferedReader brProduct = new BufferedReader(new FileReader(new File(fileName[0])));		
			// open customer file
			BufferedReader brCustomer = new BufferedReader(new FileReader(new File(fileName[1])));	
			// open order file
			BufferedReader brOrder1 = new BufferedReader(new FileReader(new File(fileName[2])));	
			// open order file
			BufferedReader brOrder2 = new BufferedReader(new FileReader(new File(fileName[2])));		
			// open inventory file
			BufferedReader brInventory = new BufferedReader(new FileReader(new File(fileName[3])));	
				
			// connect to database
			Connection  conn = DriverManager.getConnection(connStr, props);
			Statement stmt = conn.createStatement();
				
			// insert prepared statements
				PreparedStatement insertRow_Product = conn.prepareStatement(
						"insert into Product values(?, ?, ?)");	
				PreparedStatement insertRow_Customer = conn.prepareStatement(
						"insert into Customer values(?, ?, ?, ?, ?, ?, parseNum(?))");
				PreparedStatement insertRow_InventoryRecord = conn.prepareStatement(
						"insert into InventoryRecord values(?, parseNum(?), parsePrice(?))");	
				PreparedStatement insertRow_OrderTable = conn.prepareStatement(
						"insert into OrderTable values(?, ?, ?)");
				PreparedStatement insertRow_OrderRecord = conn.prepareStatement(
						"insert into OrderRecord values(?, ?, parseNum(?), parsePrice(?), ?)");
				
		) {
			// connect to the database using URL
            System.out.println("Connected to database " + dbName);
            
            // clear data from tables
            for (String tbl : dbTables) {
	            try {
	            		stmt.executeUpdate("delete from " + tbl);
//	            		System.out.println("Truncated table " + tbl);
	            } catch (SQLException ex) {
//	            		System.out.println("Did not truncate table " + tbl);
	            }
            }
            
        	
			String line;
			// read from product
			while ((line = brProduct.readLine()) != null) {
				
				// split input line into fields at tab delimiter
				String[] data1 = line.split("\\|");
				// data1 is an array, the max length of the array is 3
				if (data1.length != 3) continue;
			
				// get fields from input data
				String ProductName = data1[0];
				String ProductDescription = data1[1];
				String ProductSKU = data1[2];
				
				
				// add Product if does not exist
				try {
					insertRow_Product.setString(1, ProductName);
					insertRow_Product.setString(2, ProductDescription);
					insertRow_Product.setString(3, ProductSKU);
					insertRow_Product.execute();
				} catch (SQLException ex) {
					
				}
			}
			

			
			// read from customer
			while ((line = brCustomer.readLine()) != null) {
				String[] data = line.split("\\|");	
				
				// get fields from input data
				String customerName = data[0];
				String customerID = data[1];
				String address = data[2];
				String city = data[3];
				String state = data[4];
				String country = data[5];
				String postalCode = data[6];
				
				// add customer if does not exist
				try {
					insertRow_Customer.setString(1, customerID);
					insertRow_Customer.setString(2, customerName);
					insertRow_Customer.setString(3, address);
					insertRow_Customer.setString(4, city);
					insertRow_Customer.setString(5, state);
					insertRow_Customer.setString(6, country);
					insertRow_Customer.setString(7, postalCode);
					insertRow_Customer.execute();
				} catch (SQLException ex) {
					// already exists
					// System.err.printf("Already inserted Customer %s\n", customerID);
				}
			}
			
			
			
			//read from inventory
			while ((line = brInventory.readLine()) != null) {
				String[] data = line.split("\\|");	
				
				// get fields from input data
				String productSKU = data[0];
				String productUnit = data[1];
				String productPrice = data[2];
				
				
				// add inventory if does not exist
				try {
					insertRow_InventoryRecord.setString(1, productSKU);
					insertRow_InventoryRecord.setString(2, productUnit);
					insertRow_InventoryRecord.setString(3, productPrice);
					insertRow_InventoryRecord.execute();
			
				} catch (SQLException ex) {
					System.err.println(ex);
				}
			}
			 
			//read from order
			while ((line = brOrder1.readLine()) != null) {
				String[] data = line.split("\\|");	
				String customerID = data[0];
				String orderID = data[1];
				String orderDate = data[2];

				
				try {
					insertRow_OrderTable.setString(1,customerID);
					insertRow_OrderTable.setString(2,orderID);
					insertRow_OrderTable.setString(3,orderDate);
					insertRow_OrderTable.execute();

				}catch (SQLException ex) {
					// already exists
					 System.err.printf("Already inserted order %s\n", orderID);
				}
			}
			
			//read from order
			
			while ((line = brOrder2.readLine()) != null) {
				String[] data = line.split("\\|");
				String orderID = data[1];
				String orderItems = data[3];
				
				String[] items = orderItems.split("\\s");
				String[] itemSKU = new String[items.length / 2];
				String[] itemNum = new String[items.length / 2];
				int idx1 = 0, idx2 = 0;
				for(int i = 0; i < items.length; i++) {
					if(i % 2 == 0) itemSKU[idx1++] = items[i];
					else itemNum[idx2++] = items[i];
				}

				try {
					for(int j = 0; j < items.length / 2; j++) {
						insertRow_OrderRecord.setString(1, orderID);
						insertRow_OrderRecord.setString(2, itemSKU[j]);
						insertRow_OrderRecord.setString(3, itemNum[j]);
						insertRow_OrderRecord.setString(4, "0");
						insertRow_OrderRecord.setString(5, "");
						insertRow_OrderRecord.execute();
					}
				}catch (SQLException ex) {
					// already exists
					 System.err.println(ex);
				}
			}
 
			
		    // print number of rows in tables
			
			PubUtil.printProduct(conn);
			PubUtil.printCustomer(conn);
			PubUtil.printOrderTable(conn);
			PubUtil.printOrderRecord(conn);
			PubUtil.printInventoryRecord(conn);

			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}

