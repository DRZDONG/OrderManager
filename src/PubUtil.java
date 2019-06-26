import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains functions for printing Product 
 * InventoryRecord Customer OrderTable OrderRecord in the database.
 * 
 *
 */
public class PubUtil {
	/**
	 * Print Product table
	 * @param conn the connection
	 * @return the number of products
	 * @throws SQLException if a database operation fails
	 */
	public static int printProduct(Connection conn) throws SQLException {
		try (
			Statement stmt = conn.createStatement();
			// list authors and their ORCIDs
			ResultSet rs = stmt.executeQuery(
					"select Name, SKU, Description from Product order by SKU");
		) {
			System.out.println("Product:");
			int count = 0;
			while (rs.next()) {
				String name = rs.getString(1);
				String SKU = rs.getString(2);
				String des = rs.getString(3);
				System.out.printf("  %s		%s	   %s\n", SKU, name, des);
				count++;
			}
			System.out.printf("\n");
			return count;
		}
	}
	
	/**
	 * Print Customer table.
	 * @param conn the connection
	 * @return number of articles
	 * @throws SQLException if a database operation fails
	 */
	public static int printCustomer(Connection conn) throws SQLException {
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
				"select CustomerID, Name, Address, City, State, PostalCode, Country from Customer order by CustomerID");
		) {
			System.out.println("Customer:");
			int count = 0;
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String addr = rs.getString(3);
				String city = rs.getString(4);
				String state = rs.getString(5);
				int code = rs.getInt(6);
				String country = rs.getString(7);
				System.out.printf("  %s	 %s	 %s	 %s	 %s	 %d	 %s\n", id, name, addr, city, state, code, country);
				count++;
			}
			System.out.printf("\n");
			return count;
		}		
	}
	
	/**
	 * Print OrderTable table.
	 * @param conn the connection
	 * @return number of orders
	 * @throws SQLException if a database operation fails
	 */
	public static int printOrderTable(Connection conn) throws SQLException {
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
				"select Cust_ID, OrderID, OrderDate from OrderTable order by OrderID");
		) {
			System.out.println("OrderTable:");
			int count = 0;
			while (rs.next()) {
				String orderid = rs.getString(2);
				String  custid= rs.getString(1);
				String date = rs.getString(3);
				System.out.printf("  %s		%s		%s\n", orderid, custid, date);
				count++;
			}
			System.out.printf("\n");
			return count;
		}		
	}
	
	/**
	 * Print OrderRecord table.
	 * @param conn the connection
	 * @return number of order record
	 * @throws SQLException if a database operation fails
	 */
	public static int printOrderRecord(Connection conn) throws SQLException {
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select OrderID, ProductID, Units, Price, ShipDate from OrderRecord order by OrderID, ProductID");
		) {
			System.out.println("OrderRecord:");
			int count = 0;
			while (rs.next()) {
				String orderid = rs.getString(1);
				String sku = rs.getString(2);
				int units = rs.getInt(3);
				double price = rs.getBigDecimal(4).doubleValue();
				String date = rs.getString(5);
				System.out.printf("  %s	 %s   %d    %.2f  %s\n", orderid, sku, units, price, date);
				count++;
			}
			System.out.printf("\n");
			return count;
		}
	}
	
	/**
	 * Print InventoryRecord table
	 * @param conn
	 * @return number of inventory records
	 * @throws SQLException if a database operation fails
	 */
	static int printInventoryRecord(Connection conn) throws SQLException {
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select ProductID, Units, Price from InventoryRecord order by ProductID");
		) {
			System.out.println("InventoryRecord:");
			int count = 0;
			while (rs.next()) {
				String sku = rs.getString(1);
				int units = rs.getInt(2);
				double price = rs.getBigDecimal(3).doubleValue();
				System.out.printf("  %s	  %d    %.2f\n", sku, units, price);
				count++;
			}
			System.out.printf("\n");
			return count;
		}
	}	

}
