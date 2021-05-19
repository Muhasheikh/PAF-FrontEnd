	package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	// database connection

	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payment", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	/// inserting to database

	public String insertItem(String username, String usermobile, String cardno, String amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " insert into paymentdetail(`paymentID`,`userName`,`userMobile`,`cardNo`,`amount`) values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, username);
			preparedStmt.setInt(3, Integer.parseInt(usermobile));
			preparedStmt.setInt(4, Integer.parseInt(cardno));
			preparedStmt.setDouble(5, Double.parseDouble(amount));
			
			// execute the statement
			preparedStmt.execute();
			
			

			con.close();
			
			String newItems = readItems();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}"; 
			 
//			output = "Inserted successfully";
		} catch (Exception e) {
//			output = "Error while inserting";
//			System.err.println(e.getMessage());
			
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
					 System.err.println(e.getMessage());
		}
		return output;
	}

	// data retrieval from database

	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed/
			output = "<table border='1'><tr><th>Name</th>" + "<th >Mobile Number</th><th>CreditCard Number</th>"
					+ "<th>Amount</th>"
					+ "<th>Update</th><th>Remove</th> </tr>";
			
			String query = "select * from paymentdetail";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String payID = Integer.toString(rs.getInt("paymentID"));
				String userM = rs.getString("userMobile");
				String card = rs.getString("cardNo");
				String amount = Double.toString(rs.getDouble("amount"));
				String userN = rs.getString("userName");
				// Add a row into the html table
				
				
				output += "<tr><td><input id='hidItemIDUpdate' type='hidden' name='hidItemIDUpdate' value=''"+payID+"\">"					
					+ userN + "</td>";
				
				output += "<td>" + userM + "</td>";
				output += "<td>" + card + "</td>";
				output += "<td>" + amount + "</td>";
				// buttons
				
			
				
				
				output += "<td><input name='btnUpdate'          "
						+ "type='button' value='Update'         "
						+ "class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove'         "
						+ "type='button' value='Remove'         "
						+ "class='btnRemove btn btn-danger'        "
						+ "data-paymentid='"       
						+ payID + "'>" + "</td></tr>";    
						
					
						
			
			}
			con.close();
			// Complete the html table//
			output += "</table>"; 
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// delete values from database

	public String deleteItem(String payID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from paymentdetail where paymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(payID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();    
			output = "{\"status\":\"success\", \"data\": \"" +  newItems + "\"}";   
			
//			output = "Deleted successfully";
		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\":         "
					+ "\"Error while deleting the item.\"}";    
			System.err.println(e.getMessage());   
			
//			output = "Error while deleting the item.";
//			System.err.println(e.getMessage());
		}
		return output;
	}

	/// update record-----------------
	public String updateItem(String payID, String userN, String userM, String cardNo, String Amount) {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			String query = "UPDATE paymentdetail SET userName=?,userMobile=?,cardNo=?,amount=? WHERE paymentID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			
			preparedStmt.setString(1, userN);
			preparedStmt.setInt(2, Integer.parseInt(userM));
			preparedStmt.setInt(3, Integer.parseInt(cardNo));
			preparedStmt.setDouble(4, Double.parseDouble(Amount));
			preparedStmt.setInt(5, Integer.parseInt(payID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}";
			 
//			output = "Updated successfully";
		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\":"
					 +"\"Error while updating the item.\"}";
					 System.err.println(e.getMessage());
//			output = "Error while updating the item.";
//			System.err.println(e.getMessage());
		}
		return output;
	}

}
