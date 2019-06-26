import java.sql.Connection;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SupportAPI {
	/**
	 * check the existence of the specific customer
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("resource")
	static boolean containsCustomerID(Connection conn) throws SQLException{
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select CustomerID from Customer");
			) {
				System.out.println("please input the customerID you would like to check:");
			 	Scanner sc = new Scanner(System.in); 
			 	String inputID = sc.nextLine(); 
				while (rs.next()) {
					  String customerID = rs.getString(1);
					  if(customerID.equals(inputID)) {
						  System.out.printf("DB has the input %s\n", inputID);
						  return true;
					  }
				}	
				System.out.printf("DB doesn't have the input %s\n", inputID);
				rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * check the existence of OrderID
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("resource")
	static boolean containsOrderID(Connection conn) throws SQLException{
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select OrderID from OrderTable");
			) {
				System.out.println("please input the orderID you would like to check:");
		 		Scanner sc = new Scanner(System.in); 
		 		String inputID = sc.nextLine(); 
				while (rs.next()) {
					  String orderID = rs.getString(1);
					  if(orderID.equals(inputID)) return true;
				}
				rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * check the existence of product SKU
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("resource")
	static boolean containsproductID(Connection conn) throws SQLException{
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select SKU from Product");
			) {
			 	System.out.println("please input the SKU you would like to check:");
			 	Scanner sc = new Scanner(System.in); 
			 	String inputSKU = sc.nextLine(); 
				while (rs.next()) {
					  String SKU = rs.getString(1);
					  if(SKU.equals(inputSKU)) return true;
				}
				rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * insert a customer
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("resource")
	static void insertCustomer(Connection conn) throws SQLException{
		try  {
				System.out.println("please input the customerID:");
				Scanner sc = new Scanner(System.in); 
			 	String inputCustomerID = sc.nextLine(); 
				Statement stmt = conn.createStatement();
				String sql= "insert into Customer(CustomerID) values('"+inputCustomerID+"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("please input the customer name:");
			 	String inputName = sc.nextLine(); 
				sql= "insert into Customer(Name) values('"+inputName+"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("please input the customer Address:");
			 	String inputAddress = sc.nextLine(); 
				sql= "insert into Customer(Address) values('"+inputAddress+"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("please input the customer City:");
			 	String inputCity = sc.nextLine(); 
				sql= "insert into Customer(City) values('"+inputCity+"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("please input the customer State :");
			 	String inputState  = sc.nextLine(); 
				sql= "insert into Customer(State) values('"+inputState+"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("please input the customer Country:");
			 	String inputCountry  = sc.nextLine(); 
				sql= "insert into Customer(Country) values('"+inputCountry+"')";
				stmt.executeUpdate(sql); 
				
				System.out.println("please input the customer PostalCode:");
			 	String inputPostalCode = sc.nextLine(); 
				sql= "insert into Customer(PostalCode) values('"+inputPostalCode+"')";
				stmt.executeUpdate(sql);    
				
				System.out.println("succuessfully insert 1 customer");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	static void insertOrder(Connection conn) throws SQLException{
		try  {
				System.out.println("please input the customerID:");
				Scanner sc = new Scanner(System.in); 
			 	String inputCustomerID = sc.nextLine(); 
				Statement stmt = conn.createStatement();
				String sql= "insert into OrderTable(CustomerID) values('"+inputCustomerID+"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("please input the OrderID :");
			 	String inputOrderID = sc.nextLine(); 
				sql= "insert into OrderTable(OrderID) values('"+inputOrderID +"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("please input the OrderDate:");
			 	String inputOrderDate = sc.nextLine(); 
				sql= "insert into OrderTable(OrderDate) values('"+inputOrderDate+"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("succuessfully insert 1 Order");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	static void insertProduct(Connection conn) throws SQLException{
		try  {
				System.out.println("please input the product Name:");
				Scanner sc = new Scanner(System.in); 
			 	String inputName = sc.nextLine(); 
				Statement stmt = conn.createStatement();
				String sql= "insert into Product(Name) values('"+inputName+"')";
				stmt.executeUpdate(sql);
				
				System.out.println("please input the Description:");
			 	String inputDescription= sc.nextLine(); 
				sql= "insert into Product(Description) values('"+inputDescription +"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("please input the SKU:");
			 	String inputSKU = sc.nextLine(); 
				sql= "insert into Product(SKU) values('"+inputSKU+"')";
				stmt.executeUpdate(sql);   
				
				System.out.println("succuessfully insert 1 product");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	static boolean deleteCustomer(Connection conn) throws SQLException{
		try  {
				System.out.println("please input the customerID:");
				Scanner sc = new Scanner(System.in); 
			 	String inputCustomerID = sc.nextLine(); 
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery("select CustomerID from Customer");
				
				while (rs1.next()) {
					String orID = rs1.getString(1);
					if(orID.equals(inputCustomerID)) {
						Statement stmt = conn.createStatement();
						String sql= "delete from Customer where CustomerID = '"+inputCustomerID+"'";
						stmt.executeUpdate(sql);   
						System.out.println("succuessfully delete 1 customer");
						return true;
					}
				}
				System.out.println("Do not has the customer");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("resource")
	static boolean deleteOrder(Connection conn) throws SQLException{
		try  {
				System.out.println("please input the OrderID:");
				Scanner sc = new Scanner(System.in); 
			 	String inputOrderID = sc.nextLine(); 
			 	
			 	
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery("select OrderID from OrderTable");
				
				while (rs1.next()) {
					String orID = rs1.getString(1);
					if(orID.equals(inputOrderID)) {
						Statement stmt = conn.createStatement();
						String sql= "delete from OrderTable where SKU = '"+inputOrderID+"'";
						stmt.executeUpdate(sql);   
						System.out.println("succuessfully delete 1 Order");
						return true;
					}
				}
				System.out.println("Do not has the order");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("resource")
	static boolean deleteProduct(Connection conn) throws SQLException{
		try  {
				System.out.println("please input the SKU:");
				Scanner sc = new Scanner(System.in); 
			 	String inputSKU = sc.nextLine();
			 	
				Statement stmt1 = conn.createStatement();
				ResultSet rs1 = stmt1.executeQuery("select SKU from Product");
				
				while (rs1.next()) {
					String proID = rs1.getString(1);
					if(proID.equals(inputSKU)) {
						Statement stmt = conn.createStatement();
						String sql= "delete from Product where SKU = '"+inputSKU+"'";
						stmt.executeUpdate(sql);   
						System.out.println("succuessfully delete 1 Order");
						return true;
					}
				}
				System.out.println("Do not has the product");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}