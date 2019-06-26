
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * This program creates a OrderManager database for the ER data model
 * There are entity tables for Product, Customer and OrderTable 
 * relationship tables for OrderRecord, InventoryRecord in the ER model. 
 * 
 * @author 
 */
public class OrderManager {

	public static void main(String[] args) {
	    // the default framework is embedded
	    String protocol = "jdbc:derby:";
	    String dbName = "ordermanager";
		String connStr = protocol + dbName+ ";create=true";

	    // tables created by this program
        String dbTables[] = {
        	 "OrderRecord", "OrderTable", "InventoryRecord", "Customer", "Product"
        };

        // triggers created by this program
        String dbTriggers[] = {
        		"UpdateOrderRecord", "UpdateInventoryRecord", "DeleteOrderTable", "UpdateShipDate"
        };
        
        // stored functions created by this program		
		String storedFunctions[] = {
				"parseNum", "parsePrice", "isSKU", "isValidState"
		};
		
		Properties props = new Properties(); // connection properties
        // providing a user name and password is optional in the embedded
        // and derbyclient frameworks
        props.put("user", "user1");
        props.put("password", "user1");

		try (
	        // connect to the database using URL
			Connection conn = DriverManager.getConnection(connStr, props);
				
	        // statement is channel for sending commands thru connection 
	        Statement stmt = conn.createStatement();
		){
	        System.out.println("Connected to and created database " + dbName);
            
	        
            // drop the database triggers and recreate them below
            for (String tgr : dbTriggers) {
	            try {
	            		stmt.executeUpdate("drop trigger " + tgr);
	            		System.out.println("Dropped trigger " + tgr);
	            } catch (SQLException ex) {
	            		System.out.println("Did not drop trigger " + tgr);
	            }
            }

            // drop the database tables and recreate them below
            for (String tbl : dbTables) {
	            try {
	            		stmt.executeUpdate("drop table " + tbl);
	            		System.out.println("Dropped table " + tbl);
	            } catch (SQLException ex) {
	            		System.out.println("Did not drop table " + tbl);
	            }
            }
            

            // drop the storedFunctions and recreate them below
            for (String func : storedFunctions) {
  	            try {
  	            		stmt.executeUpdate("drop function " + func);
  	            		System.out.println("Dropped function " + func);
  	            } catch (SQLException ex) {
  	            		System.out.println("Did not drop function " + func);
  	            }
            }          
                              
			
            // create stored function for parseNum
            String createFunction_parseNum =
            		  "CREATE FUNCTION parseNum(" 
            		+ " 	 Units VARCHAR(12)"
            		+ "	)  RETURNS INT"
            		+ " PARAMETER STYLE JAVA"
            		+ " LANGUAGE JAVA" 
            		+ " DETERMINISTIC"
            		+ " NO SQL"
            		+ " EXTERNAL NAME"
            		+ "		'Biblio.parseNum'";
            stmt.executeUpdate(createFunction_parseNum);
            System.out.println("Created stored function parseNum");   
            
            // create stored function for parsePrice
            String createFunction_parsePrice =
            		  "CREATE FUNCTION parsePrice(" 
            		+ " 	 Price VARCHAR(32)"
            		+ "	)  RETURNS DECIMAL(18,2)"
            		+ " PARAMETER STYLE JAVA"
            		+ " LANGUAGE JAVA" 
            		+ " DETERMINISTIC"
            		+ " NO SQL"
            		+ " EXTERNAL NAME"
            		+ "		'Biblio.parsePrice'";
            stmt.executeUpdate(createFunction_parsePrice);
            System.out.println("Created stored function parsePrice");            
            
            // create stored function for isSKU
            String createFunction_isSKU =
            		  "CREATE FUNCTION isSKU(" 
            		+ " 	 SKU VARCHAR(12)" 
            		+ "	)  RETURNS BOOLEAN"
            		+ " PARAMETER STYLE JAVA"
            		+ " LANGUAGE JAVA" 
            		+ " DETERMINISTIC"
            		+ " NO SQL"
            		+ " EXTERNAL NAME"
            		+ "		'Biblio.isSKU'";
            stmt.executeUpdate(createFunction_isSKU);
            System.out.println("Created stored function isSKU");
            
            // create stored function for isValidState
            String createFunction_isValidState =
          		  "CREATE FUNCTION isValidState(" 
          		+ " 	 COUNTRY VARCHAR(32), STATE VARCHAR(32)" 
          		+ "	)  RETURNS BOOLEAN"
          		+ " PARAMETER STYLE JAVA"
          		+ " LANGUAGE JAVA" 
          		+ " DETERMINISTIC"
          		+ " NO SQL"
          		+ " EXTERNAL NAME"
          		+ "		'Biblio.isValidState'";
          stmt.executeUpdate(createFunction_isValidState);
          System.out.println("Created stored function isValidState");            
            
			// create Product
			  String createTable_Product =
        		  "create table Product("
        		+ "  Name varchar(32) not null,"
        		+ "  Description varchar(256) not null,"
        		+ "	 SKU varchar(12) not null,"
        		+ "	 primary key (SKU),"
        		+ "  check (isSKU(SKU))"
        		+ ")";
          	  stmt.executeUpdate(createTable_Product);
          	  System.out.println("Created table Product");	
          	  
	          // create Customer table
			  String createTable_Customer =
	        		  "create table Customer ("
					+ "	 CustomerID varchar(6) not null," 
	        		+ "  Name varchar(32) not null,"
	        	    + "	 Address varchar(64) not null,"
	        		+ "  City varchar(32) not null,"
	        	    + "	 State varchar(32) not null,"
	        		+ "	 Country varchar(32) not null,"
	        	    + "	 PostalCode int not null,"
	        		+ "	 primary key (CustomerID),"
	        		+ "  check (isValidState(Country, State))"
	        		+ ")";
	          stmt.executeUpdate(createTable_Customer);
	          System.out.println("Created table Customer");	
			
          	  // create InventoryRecord table
			  String createTable_InventoryRecord =
	        		  "create table InventoryRecord("
					+ "	 ProductID varchar(12) not null,"
	        		+ "  Units int not null,"
	        		+ "  Price Decimal(18,2) not null,"
	        		+ "  primary key (ProductID),"
	        		+ "  foreign key (ProductID) references Product(SKU) on delete cascade"
	        		+ ")";
	          stmt.executeUpdate(createTable_InventoryRecord);
	          System.out.println("Created table InventoryRecord");	
	      	
	          	
	          // create Order table 
			  String createTable_OrderTable =
	        		  "create table OrderTable ("
	        		+ "  Cust_ID varchar(6) not null,"
	        	    + "	 OrderID varchar(6) not null,"
	        		+ "  OrderDate varchar(8) not null,"
	        		+ "	 primary key (OrderID),"
	        	    + "	 foreign key (Cust_ID) references Customer(CustomerID) on delete cascade"
	        		+ ")";
	          stmt.executeUpdate(createTable_OrderTable);
	          System.out.println("Created table OrderTable");	   
	          

	           // create OrderRecord table
	          String createTable_OrderRecord =
	                  "create table OrderRecord ("
	                + "  OrderID varchar(6) not null,"
	                + "  ProductID varchar(12) not null,"
	                + "  Units int not null,"
	                + "  Price decimal(18, 2) not null,"
	                + "  ShipDate varchar(8),"
	                + "  primary key (OrderID, ProductID),"
	                + "  foreign key (OrderID) references OrderTable (OrderID) on delete cascade,"
	                + "  foreign key (ProductID) references InventoryRecord(ProductID) on delete cascade"
	                + ")";
	          
	          stmt.executeUpdate(createTable_OrderRecord);
	          System.out.println("Created table OrderRecord");	
	          
		      // create trigger for updating OrderRecord after inserting an order record  	          
		      String createTrigger_UpdateOrderRecord = 
		    		  "create trigger UpdateOrderRecord"
		    		 + " after insert on OrderRecord"
		             + " for each row"
		    		 + "  update OrderRecord"
		             + "  set Price = "
	          		 + "  (select Price from InventoryRecord"
	          		 + "  where InventoryRecord.ProductID = OrderRecord.ProductID),"
	          		 + "  ShipDate =  "
	          		 + "  (select OrderDate from OrderTable"
	          		 + "  where OrderTable.OrderId = OrderRecord.OrderID) ";
	          stmt.executeUpdate(createTrigger_UpdateOrderRecord);
	          System.out.println("Created trigger for UpdateOrderRecord");	
	          
	          // create trigger for updating ShipDate in OrderRecord to null if the units in inventory record is 
	          // smaller than the order record for specific product
	          String createTrigger_UpdateShipDate =
	                  "create trigger UpdateShipDate"
	                + " after insert on OrderRecord"
	                + " referencing new as insertedOrderRecord"
	                + " for each row" 
	                + " when (insertedOrderRecord.Units > "
	                + " (select InventoryRecord.Units from InventoryRecord where "
	                + " ProductID = insertedOrderRecord.ProductID))"
	                + " update OrderRecord"
	                + " set ShipDate = null";
	          stmt.executeUpdate(createTrigger_UpdateShipDate);
	          System.out.println("Created trigger for UpdateShipDate");		          
	          
	          
	          // create trigger for updating InventoryRecord after inserting an order record 
	          String createTrigger_UpdateInventoryRecord = 
		    		  "create trigger UpdateInventoryRecord"
		    		 + " after insert on OrderRecord"
		    		 + " referencing new as newRecord"
		             + " for each row"
		    		 + "  update InventoryRecord"
		             + "  set Units = Units -"
		    		 + "  newRecord.Units"
		             + "  where newRecord.ProductID = InventoryRecord.ProductID and"
		             + "  newRecord.Units < InventoryRecord.Units";
	          stmt.executeUpdate(createTrigger_UpdateInventoryRecord);
	          System.out.println("Created trigger for UpdateInventoryRecord");	
	          
	            // create trigger for deleting an order record that also deletes 
	            // orders which have no other order record
	            String createTrigger_DeleteOrderTable =
		          		  "create trigger DeleteOrderTable"
		          		+ " after delete on OrderRecord"
						+ " for each row"
		          		+ "   delete from OrderTable where OrderID not in"
		          		+ "     (select OrderID from OrderRecord)";
	          stmt.executeUpdate(createTrigger_DeleteOrderTable);
	          System.out.println("Created trigger for deleting OrderTable");
	          
	          
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
