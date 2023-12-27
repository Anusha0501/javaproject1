import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class CreateTableFunction {
    public static void createTable(String username) {
        Scanner scanner = new Scanner(System.in);
        String tableName = username;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "divyansh");
            Statement statement = connection.createStatement();

            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                         "transactionID INT AUTO_INCREMENT PRIMARY KEY, "+
                         "Amount_spend FLOAT, " +
                         "Deposited_or_Credited VARCHAR(1), " +
                         "expense_details VARCHAR(255), " +
                         "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " + // Added a comma here
                         "Amount_left FLOAT" + // Added a comma here
                         ")";


            statement.executeUpdate(createTableSQL);

            statement.close();
            connection.close();

            System.out.println("Table '" + tableName + "' created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}

