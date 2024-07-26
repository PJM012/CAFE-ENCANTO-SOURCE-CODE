import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class DB {
	
	public static Connection DBConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/cafeencanto","root", "");
			System.out.print("Database is connected !");
		} catch(ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Do not connect to DB - Error:"+e);
		}
		return conn;
	}
	
	public static void addProductToDB(String ProductID, String detail, String comp, int quan, String expiration_date) {
		// Validate input length for Product ID
		if (ProductID.length() > 20) {
			JOptionPane.showMessageDialog(null, "Product ID must not exceed 20 characters.");
			return; 
		}
	
		// Validate expiration date format
		if (!expiration_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			JOptionPane.showMessageDialog(null, "Invalid expiration date format!");
			return;
		}
	
		Connection conn = DBConnection();
		try {
			// Create SQL statement with expiration date
			String sql = "INSERT INTO stock (ProductID, Detail, Category, Quantity, expiration_date) VALUES (?, ?, ?, ?, ?)";
			
			// Use PreparedStatement to avoid SQL injection
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ProductID);
			pstmt.setString(2, detail);
			pstmt.setString(3, comp);
			pstmt.setInt(4, quan);
			pstmt.setString(5, expiration_date);
	
			// Execute the update
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Product added to database");
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	

	
	public static void updateProductToDB(String ProductID, String detail, String comp, int quan, String expiration_date) {
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			// Update query to include the expiration date
			String query = "UPDATE stock SET Detail = '" + detail + "', Category = '" + comp + "', Quantity = " + quan + ", expiration_date = '" + expiration_date + "' WHERE ProductID = '" + ProductID + "';";
			int status = statement.executeUpdate(query);
			if (status == 1) {
				JOptionPane.showMessageDialog(null, "Product updated!");
			} else {
				JOptionPane.showMessageDialog(null, "Product ID not found!");
			}
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	
	public static void deleteProductToDB(String id) {
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			int status = statement.executeUpdate("DELETE from stock WHERE ProductID = '"+id+"';");
		    if(status == 1) {
		    	JOptionPane.showMessageDialog(null, "Product deleted");
		    } else {
		    	JOptionPane.showMessageDialog(null, "ProductID not found!");
		    }
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public static void searchProduct(String id) {
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			// Update the SQL query to select expiration_date as well
			ResultSet rs = statement.executeQuery("SELECT * FROM stock WHERE ProductID = '" + id + "'");
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "No product found with this ID: " + id);
			} else {
				String productId = rs.getString("ProductID");
				String detail = rs.getString("Detail");
				int quantity = rs.getInt("Quantity");
				Date expirationDate = rs.getDate("expiration_date"); // Fetch expiration_date
				StringBuilder message = new StringBuilder();
				message.append("Product ID: ").append(productId).append("\n");
				message.append("Product Detail: ").append(detail).append("\n");
				message.append("Quantity: ").append(quantity).append("\n");
				if (expirationDate != null) {
					message.append("Expiration Date: ").append(expirationDate.toString()).append("\n");
				} else {
					message.append("Expiration Date: Not Available\n");
				}
	
				JOptionPane.showMessageDialog(null, message.toString(), "Product Details", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
	}
	

	public static boolean verifyLogin(String username, String pass) {
		boolean login = false;
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("Select * from users WHERE Username = '" + username + "' and Password = '" + pass + "';");
			login = rs.next();
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return login;
	}
	
	public static void addAccount(String user, String pass, String pin) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String query = "INSERT INTO users (Username, Password, PIN) VALUES ('" + user + "','" + pass + "','" + pin + "')";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Account added to database");
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

	public static boolean usernameExists(String username) {
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT COUNT(*) FROM users WHERE Username = '" + username + "'";
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			int count = resultSet.getInt(1);
			conn.close();
			return count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; 
		}
	}
	

    public static void deleteAccount(String user, String pass, String pin) {
        Connection conn = DBConnection();
        try {
            if (user.isEmpty() || pass.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required");
                return; 
            }
            if (user.equals("admin")) {
                JOptionPane.showMessageDialog(null, "Cannot delete admin account.");
                return; 
            }

            String query = "DELETE FROM users WHERE Username = ? AND Password = ? AND PIN = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            pstmt.setString(3, pin);

            int status = pstmt.executeUpdate();
            if (status == 1) {
                JOptionPane.showMessageDialog(null, "Account deleted successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Account not found or Account Credential/s are incorrect.");
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


	
	public static String searchPDetail(String ProductID, int q) {
		Connection conn = DBConnection();
		String rt = "";
		try {
			int quan;
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("Select * from stock WHERE ProductID = '"+ProductID+"';");
			if (!rs.next()) {
				rt = "nill";
			} else {
				quan = Integer.parseInt(rs.getString("Quantity")) - q;
				if(quan < 0) {
					rt = "item is out of stock";
				} else {
					rt = rs.getString("Detail") + "%" + rs.getString("Category");
					statement.executeUpdate("UPDATE stock set Quantity = "+quan+" WHERE ProductID = '"+ProductID+"';");
				}
			}
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return rt;
	}
	
	public static ArrayList<String> showStock(String comp) {
		String q;
		ArrayList<String> r = new ArrayList<>();
		if(comp.equals("All")) {	
			q = "Select * from stock;";
		} else {
			q = "Select * from stock WHERE Category = '"+comp+"';";
		}
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(q);
			while(rs.next()) {
				r.add(rs.getString("ProductID"));
				r.add(rs.getString("Detail"));
				r.add(rs.getString("Category"));
				r.add(rs.getString("Quantity"));
				r.add(rs.getString("expiration_date"));
			}
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return r;
	}
	
	public static String getPDetail(String id, int q) {
		Connection conn = DBConnection();
		String rt = "";
		try {
			int quan;
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("Select * from stock WHERE ProductID = '"+id+"';");
			if (!rs.next()) {
				rt = "nill";
			} else {
				quan = Integer.parseInt(rs.getString("Quantity")) - q;
				if(quan < 0) {
					rt = "item is out of stock";
				} else {
					rt = rs.getString("Detail") + "%" + rs.getString("Category");
					statement.executeUpdate("UPDATE stock set Quantity = "+quan+" WHERE ProductID = '"+id+"';");
				}
			}
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return rt;
	}
	
	public static ArrayList<String> searchP(String ProductID) {
		Connection conn = DBConnection();
		ArrayList<String> data = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("Select * from stock WHERE ProductID = '"+ProductID+"';");
			if (rs.next()) {
				data.add(rs.getString("Detail"));
				data.add(rs.getString("Category"));
				data.add(rs.getString("Quantity"));
				data.add(rs.getString("expiration_date"));
			}
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return data;
	}
	
	public static void updateProduct(String ProductID, int quan) {
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("Select * from stock WHERE ProductID = '"+ProductID+"';");
			int q;
			if(rs.next()) {
				q = Integer.parseInt(rs.getString("Quantity")) + quan;
				statement.executeUpdate("UPDATE stock set Quantity = "+q+" WHERE ProductID = '"+ProductID+"';");
			}
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public static void main(String args[]) {
	}
	public static void searchAccountByPIN(String pin) {
        Connection conn = DBConnection();
        try {
            String query = "SELECT * FROM users WHERE PIN = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, pin);
            
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null,"No account found with this PIN!");
            } else {
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                JOptionPane.showMessageDialog(null, "Username: " + username + "\nPassword: " + password);
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

	
	public static String getAdminPin() {
		String adminPin = "";
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT pin FROM users LIMIT 1;");
			if (rs.next()) {
				adminPin = rs.getString("pin");
			} else {
				JOptionPane.showMessageDialog(null, "Admin pin not found!");
			}
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return adminPin;
	}
}
