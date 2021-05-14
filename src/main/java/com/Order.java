package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Order {
	
	private Connection connect() {
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rest_api?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			
		}catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String readOrder() {
		
		String output = ""; 
		 
		try   {    
			  
			  Connection con = connect(); 
		 
			   if (con == null)    {
				   return "Error while connecting to the database.."; 
				   
			   } 
			    
		   // Prepare the html table to be displayed    
		  output = "<table border='1'><tr>"
		  		+ "<th>Product Name</th>"
		  		+ "<th>Quantity</th>"
		  		+ "<th>Customer Name</th>"
		  		+ "<th>Customer Address</th>"
		  		+ "<th>Contact No</th>"
		  		+ "<th>Update</th>"
		  		+ "<th>Remove</th></tr>"; 
		 
		   String query = "select * from order_management";    
		   Statement stmt = con.createStatement();    
		   ResultSet rs = stmt.executeQuery(query); 
		 
		   // iterate through the rows in the result set    
		   while (rs.next()) {     
			   String oId = Integer.toString(rs.getInt("order_id"));     
			   String pname = rs.getString("product_name"); 
			   String qty = rs.getString("quantity"); 
			   String cname = rs.getString("customer_name"); 
			   String cadd= rs.getString("customer_address");
			   String cno = rs.getString("contact_no");     
		 
		    // Add into the html table
			   output += "<tr><td><input id='hiddenIDUpdate' name='hiddenIDUpdate' type='hidden' value='" + oId + "'>"+ pname + "</td>"; 
			   output += "<td>" + qty + "</td>"; 
			   output += "<td>" + cname + "</td>"; 
			   output += "<td>" + cadd + "</td>";    
			   output += "<td>" + cno + "</td>"; 
		 
		    // buttons     
			   output += "<td><input name='btnUpdate' type='button'"
			   		+ "value='Update' class='btnUpdate btn btn-secondary'></td>" 
			   		+ "<td><input name='btnRemove' type='button' value='Remove'"
			   		+ "class='btnRemove btn btn-danger' data-oid='"      
			   		+ oId +"'></td></tr>";    
		   } 
		 
		   con.close(); 
		 
		   // Complete the html table    
		   output += "</table>";   
		   
		}catch (Exception e)   {    
			output = "Error while reading the Users.";    
			System.err.println(e.getMessage());   
		} 
		 
		  return output; 
		  
	}
	
	public String insertOrder(String product_name, String quantity, String customer_name, String customer_address, String contact_no) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				return "Error while conncting to the database for inserting..";
				
			}
			
			String query = "INSERT INTO order_management(`product_name`, `quantity`, `customer_name`, `customer_address`, `contact_no`) VALUES (?,?,?,?,?)";
			
			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, product_name);
			ps.setString(2, quantity);
			ps.setString(3, customer_name);
			ps.setString(4, customer_address);
			ps.setString(5, contact_no);
			
			ps.executeUpdate();
			ps.close();
			
			String newOrder = readOrder();
			output = "{\"status\":\"success\", \"data\": \""+newOrder+"\"}";
		//	output = "Insert Successfully";
			
		}catch (Exception e) {
			// TODO: handle exception
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the users.\"}";
			System.out.println(e);
		}
		
		return output;
		
	}
	
	public String updateOrder(String ID, String product_name, String quantity, String customer_name, String customer_address, String contact_no)  {   
		
		String output = ""; 
	 
		try   {    
			Connection con = connect(); 
	 
	   if (con == null)    {
		   return "Error while connecting to the database for updating."; 
	   } 
	 
	   // create a prepared statement    
	   String query = "UPDATE order_management SET product_name=?, quantity=?, customer_name=?, customer_address=?, contact_no=?"
	   		+ "WHERE order_id=?"; 
	 
	   PreparedStatement ps = con.prepareStatement(query); 
	 
	   // binding values    
	    ps.setString(1, product_name);
		ps.setString(2, quantity);
		ps.setString(3, customer_name);
		ps.setString(4, customer_address);
		ps.setString(5, contact_no);       
	   ps.setInt(6, Integer.parseInt(ID)); 
	 
	   // execute the statement    
	   ps.execute();    
	   con.close(); 
	 
	   String newOrder = readOrder();
	   output = "{\"status\":\"success\", \"data\": \""+newOrder+"\"}";
	//   output = "Updated successfully";   
	   
		}catch (Exception e)   {    
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}";    
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	  
	}
	
	public String deleteOrder(String oId)  {   
		
		String output = ""; 
	 
	  try   {    
		  Connection con = connect(); 
	 
	   if (con == null)    {
		   return "Error while connecting to the database for deleting."; 
	   } 
	 
	   // create a prepared statement    
	   String query = "delete from order_management where order_id=?"; 
	 
	   PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	   // binding values    
	   preparedStmt.setInt(1, Integer.parseInt(oId)); 
	 
	   // execute the statement    
	   preparedStmt.execute();    
	   con.close(); 
	 
	   String newOrder = readOrder();
	   output = "{\"status\":\"success\", \"data\": \""+newOrder+"\"}";  
	   
	  }catch (Exception e)   {    
		  output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";    
		  System.err.println(e.getMessage());   
	  } 
	 
	  return output;  
	  
	}

}
