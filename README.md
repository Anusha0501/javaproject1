# XpenseX: A Non-GUI Java Expense Tracker

## Overview
XpenseX is a command-line-based expense tracking system built in Java, leveraging MySQL for data storage. The project provides a simple yet effective way to manage financial transactions by allowing users to record expenses, deposits, and track their balance dynamically.

## Features
- **Create User-Specific Tables**: Generates tables dynamically based on usernames to store transaction details.
- **Record Transactions**: Supports adding expenses and deposits to maintain a financial log.
- **View Transactions**: Displays all transactions in a formatted table with details such as amount spent, credited, balance, and timestamps.
- **Update Transactions**: Modify existing records to keep data accurate.
- **Delete Transactions**: Remove specific transactions when needed.

## Project Structure
- `DisplayTableAttributes.java` – Fetches and displays user-specific transaction records.
- `CreateTableFunction.java` – Handles table creation dynamically for new users.
- `DeleteTransaction.java` – Deletes transactions from the user's table.
- `Main.java` – The entry point of the application.
- `Menu.java` – Provides navigation for various operations.
- `UpdateTable.java` – Allows updating transaction details.
- `UserInfo.java` – Manages user authentication and data.
- `addex.java` – Handles adding new expense entries.

## Technologies Used
- **Java (JDBC)** – Core programming language with database connectivity.
- **MySQL** – Relational database to store and manage transaction records.

## How It Works
1. The system connects to a MySQL database via JDBC.
2. A unique table is created for each user.
3. Users can perform CRUD operations on their expense records.
4. Data is presented in a structured table format.

## Installation & Setup
1. Clone the repository.
2. Configure MySQL credentials inside the Java files.
3. Run `Main.java` to launch the expense tracker.

---
Designed & Developed for **SEM 3 JAVA PROGRAMMING PROJECT** 🎯

