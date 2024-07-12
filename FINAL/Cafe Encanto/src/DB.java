import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	public static void addProductToDB(String id, String detail, String comp, int quan) {
		if (id.length() > 20) {
			JOptionPane.showMessageDialog(null, "Product ID must not exceed 20 characters.");
			return; 
		}
	
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO stock VALUES ('" + id + "','" + detail + "','" + comp + "'," + quan + ");");
			JOptionPane.showMessageDialog(null, "Product added to database");
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	
	public static void updateProductToDB(String id, String detail, String comp, int quan) {
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			int status = statement.executeUpdate("UPDATE stock set Detail = '"+detail+"', Category = '"+comp+"', Quantity = "+quan+" WHERE ProductID = '"+id+"';");
			if(status == 1) {
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
            ResultSet rs = statement.executeQuery("SELECT * FROM stock WHERE ProductID = '" + id + "'");
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "No product found with this ID: " + id);
            } else {
                String productId = rs.getString("ProductID");
                String detail = rs.getString("Detail"); 
                int quantity = rs.getInt("Quantity");  
                StringBuilder message = new StringBuilder();
                message.append("Product ID: ").append(productId).append("\n");
                message.append("Product Detail: ").append(detail).append("\n");
                message.append("Quantity: ").append(quantity).append("\n");

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


	
	public static String searchPDetail(String id, int q) {
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
	
	public static ArrayList<String> searchP(String id) {
		Connection conn = DBConnection();
		ArrayList<String> data = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("Select * from stock WHERE ProductID = '"+id+"';");
			if (rs.next()) {
				data.add(rs.getString("Detail"));
				data.add(rs.getString("Category"));
				data.add(rs.getString("Quantity"));
			}
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return data;
	}
	
	public static void updateProduct(String id, int quan) {
		Connection conn = DBConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("Select * from stock WHERE ProductID = '"+id+"';");
			int q;
			if(rs.next()) {
				q = Integer.parseInt(rs.getString("Quantity")) + quan;
				statement.executeUpdate("UPDATE stock set Quantity = "+q+" WHERE ProductID = '"+id+"';");
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
