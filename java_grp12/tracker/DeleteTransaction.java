import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import expense.*;

public class DeleteTransaction {
    public static void deleteTransaction(String username) {
        try (Scanner scanner = new Scanner(System.in);
             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "divyansh")) {

            // Display existing transactions for user to choose from
            DisplayTableAttributes.displayTableAttributes(username);

            // Prompt user for the TransactionID to delete
            System.out.print("Enter the TransactionID to delete: ");
            int transactionID = scanner.nextInt();

            // Construct the DELETE query
            String deleteQuery = "DELETE FROM " + username + " WHERE transactionID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, transactionID);

                // Execute the DELETE query
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Transaction deleted successfully.");
                } else {
                    System.out.println("No records deleted. Please check the TransactionID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
        }
    }
}