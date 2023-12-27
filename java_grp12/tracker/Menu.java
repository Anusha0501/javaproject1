import expense.*;
import java.sql.*;
import java.util.*;

public class Menu {

    public static void showMenu(Connection connection, String username) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Expense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. List Expenses");
            System.out.println("3. update expenses information");
	    System.out.println("4. delete any expense information");
	    System.out.println("5. Exit application");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
	    scanner.nextLine();

            switch (choice) {
                case 1:
                    addex.addTransaction(username);
                    break;
                case 2:
                    // List Expenses logic
                    DisplayTableAttributes.displayTableAttributes(username);
                    break;
		case 3:
                    // List Expenses logic
                    UpdateTable.updateTable(username);
                    break;
		case 4:
		    DeleteTransaction.deleteTransaction (username);
		    break;              
	        case 5:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice > 5);

        scanner.close();
    }
}