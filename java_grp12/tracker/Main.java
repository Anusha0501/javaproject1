import java.sql.*;
import java.util.*;

public class Main{

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "divyansh");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to the Expence manager");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            
            // Check if the user already exists
            if (userExists(connection, username)) {
                System.out.println("User already exists. Please log in.");
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                
                // Check if the entered password matches the one in the database
                if (checkPassword(connection, username, password)) {
                    System.out.println("Login successful.");
		    System.out.println("Hey "+username+" !!! welcome to your expense tracker...");
		    Menu.showMenu(connection, username);	    	
                } 
		else {
                    System.out.println("Login failed. Invalid username or password.");
                }
            } else {
                System.out.println("User does not exist.\n Want create a new account??\n yes/no:- ");
		String x = scanner.next();
		if (x.equalsIgnoreCase("yes")){
		   System.out.println("Thankyou for choosing us as your Expense manager");	
                   UserInfo.userinfo();
		   CreateTableFunction.createTable(username);
		}
		else if (x.equalsIgnoreCase("no")){
    			System.out.println("Have a nice day, thankyou");
  		}
		else{
			System.out.println("invalid input...");
		}
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean userExists(Connection connection, String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM userinfo WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }

    private static boolean checkPassword(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT password FROM userinfo WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    return password.equals(storedPassword);
                }
            }
        }
        return false;
    }

}
