package expense;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayTableAttributes {
    public static void displayTableAttributes(String username) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "divyansh")) {

            // Construct the SELECT query based on the table name (assumed format: username)
            String selectQuery = "SELECT * FROM " + username;

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Print table headers
                System.out.printf("%-15s%-15s%-25s%-25s%-20s%-15s%n",
                        "transactionID", "Amount_spend", "Deposited_or_Credited", "Expense_details", "Created_at", "Amount_left");
                System.out.println("-----------------------------------------------------------------------------------------");

                // Iterate through the result set and print each attribute
                while (resultSet.next()) {
                    System.out.printf("%-15d%-15.2f%-25s%-25s%-20s%-15.2f%n",
                            resultSet.getInt("transactionID"),
                            resultSet.getFloat("Amount_spend"),
                            resultSet.getString("Deposited_or_Credited"),
                            resultSet.getString("expense_details"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getFloat("Amount_left"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
        }
    }
}