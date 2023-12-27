import java.sql.*;
import java.util.*;

public class addex {
    public static void addTransaction(String username) {
        try (Scanner scanner = new Scanner(System.in);
             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "divyansh")) {

            // Fetch the user's budget from the userinfo table
            float budget = fetchUserBudget(connection, username);

            if (budget < 0) {
                System.out.println("User not found or budget not set. Please check the userinfo table.");
                return;
            }

            System.out.println("Enter Transaction Details:");

            System.out.print("Amount_spend: ");
            float amountSpend = scanner.nextFloat();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Deposited (D) or Credited (C): ");
            String depositedOrCredited = scanner.next();

            scanner.nextLine(); // Consume the newline character

            System.out.print("Transaction Info: ");
            String expenseDetails = scanner.nextLine();

            // Calculate the new amountLeft based on the transaction type
            float newAmountLeft = calculateNewAmountLeft(depositedOrCredited, budget, amountSpend);

            // Get current timestamp for created_at
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());

            // Update the AmountLeft based on the transaction type
            adjustAmountLeft(connection, username, newAmountLeft);

            addTransactionToDatabase(connection, username, amountSpend, depositedOrCredited, expenseDetails, createdAt, newAmountLeft);

            System.out.println("Transaction added successfully.");
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
        } 
    }

    private static float fetchUserBudget(Connection connection, String username) throws SQLException {
        String selectQuery = "SELECT budget FROM userinfo WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getFloat("budget");
                }
            }
        }
        return -1; // Return a negative value if user not found or budget not set
    }

    private static void adjustAmountLeft(Connection connection, String username, float newAmountLeft) throws SQLException {
        // Update the AmountLeft in the userinfo table
        String updateQuery = "UPDATE userinfo SET budget = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setFloat(1, newAmountLeft);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        }
    }

    private static float calculateNewAmountLeft(String depositedOrCredited, float budget, float amountSpend) {
        if (depositedOrCredited.equalsIgnoreCase("C")) {
            return budget + amountSpend;
        } else if (depositedOrCredited.equalsIgnoreCase("D")) {
            // When amount is deposited, add it to the budget
            return budget - amountSpend;
        } else {
            // Invalid transaction type, return -1
            return -1;
        }
    }

    public static void addTransactionToDatabase(Connection connection, String username, float amountSpend, String depositedOrCredited, String expenseDetails, Timestamp createdAt, float newAmountLeft) {
        try {
            // SQL query to insert transaction information
            String insertQuery = "INSERT INTO " + username + " (Amount_spend, Deposited_or_Credited, expense_details, created_at, Amount_left) VALUES (?, ?, ?, ?, ?)";

            // Create a PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setFloat(1, amountSpend);
                preparedStatement.setString(2, depositedOrCredited);
                preparedStatement.setString(3, expenseDetails);
                preparedStatement.setTimestamp(4, createdAt);
                preparedStatement.setFloat(5, newAmountLeft);

                // Execute the insert query
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Transaction added to the database successfully.");
                } 
		else {
                    System.out.println("Transaction insertion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
        }
    }
}