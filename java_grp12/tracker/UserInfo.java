import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInfo {
    private int userid;
    private String name;
    private String username;
    private String password;
    private String email;
    private float budget;

    public UserInfo(String name, String username, String password, String email, float budget) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.budget = budget;
    }

    public static void userinfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter User Information:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Budget: ");
        float budget = scanner.nextFloat();

        addUserInfoToDatabase(name, username, password, email, budget);

        System.out.println("User information added successfully.");
    }

    public static void addUserInfoToDatabase(String name, String username, String password, String email, float budget) {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "divyansh");

            // SQL query to insert user information
            String insertQuery = "INSERT INTO userinfo (name, username, password, email, budget) VALUES (?, ?, ?, ?, ?)";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.setFloat(5, budget);

            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User added to the database successfully.");
            } else {
                System.out.println("User insertion failed.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}