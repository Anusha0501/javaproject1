import java.sql.*;
import java.util.*;
import expense.*;

public class UpdateTable {

    public static void updateTable(String username) {
        try (Scanner scanner = new Scanner(System.in);
             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "divyansh")) {

            System.out.println("Choose a column to update:");
            System.out.println("1. Amount_spend");
            System.out.println("2. expense_details");

            System.out.print("Enter your choice (1, 2): ");
            int choice = scanner.nextInt();
            DisplayTableAttributes.displayTableAttributes(username);
            
            // Check if the choice is valid
            if (choice < 1 || choice > 2) {
                System.out.println("Invalid choice.");
                return;
            }

            String columnName = getColumnName(choice);

            System.out.print("Enter the new value: ");
            String newValue = scanner.next();

            if (columnName.equals("Amount_spend")) {
                updateAmountSpend(connection, username, newValue, scanner);
            }

            String updateQuery = "UPDATE " + username + " SET " + columnName + " = ? WHERE transactionID = ?";

            System.out.print("Enter the TransactionID of the record to update: ");
            int transactionID = scanner.nextInt();

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newValue);
                preparedStatement.setInt(2, transactionID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Table updated successfully.");
                } else {
                    System.out.println("No records updated. Please check the TransactionID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getColumnName(int choice) {
        switch (choice) {
            case 1:
                return "Amount_spend";
            case 2:
                return "expense_details";
            default:
                throw new IllegalArgumentException("Invalid choice.");
        }
    }

    private static void updateAmountSpend(Connection connection, String username, String newAmountSpend, Scanner scanner) throws SQLException {
        String selectQuery = "SELECT Amount_spend, Deposited_or_Credited, Amount_left FROM " + username + " WHERE transactionID = ?";
        String updateAmountLeftQuery = "UPDATE " + username + " SET Amount_left = ? WHERE transactionID = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement updateAmountLeftStatement = connection.prepareStatement(updateAmountLeftQuery)) {

            System.out.print("Enter the TransactionID of the record to update: ");
            int transactionID = scanner.nextInt();
            selectStatement.setInt(1, transactionID);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    float currentAmountSpend = resultSet.getFloat("Amount_spend");
                    String depositedOrCredited = resultSet.getString("Deposited_or_Credited");
                    float currentAmountLeft = resultSet.getFloat("Amount_left");

                    float updatedAmountSpend = Float.parseFloat(newAmountSpend);
                    float amountLeftChange = 0;

                    if (depositedOrCredited.equalsIgnoreCase("C")) {
                        amountLeftChange = currentAmountSpend - updatedAmountSpend;
                    } else if (depositedOrCredited.equalsIgnoreCase("D")) {
                        amountLeftChange = updatedAmountSpend - currentAmountSpend;
                    }

                    float newAmountLeft = currentAmountLeft + amountLeftChange;

                    updateAmountLeftStatement.setFloat(1, newAmountLeft);
                    updateAmountLeftStatement.setInt(2, transactionID);
                    updateAmountLeftStatement.executeUpdate();
                } else {
                    System.out.println("TransactionID not found. Please check the TransactionID.");
                }
            }
        }
    }
}
