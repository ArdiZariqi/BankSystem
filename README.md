# Bank System Console Application

## Overview

This project is a console-based banking application developed using Spring Boot. It allows users to create banks, manage accounts, perform transactions, and view various details related to banks and accounts. The application interacts with a database to store and retrieve information.

## Features

1. **Create Bank**: Allows the creation of new banks with specified transaction fees.
2. **Create Account**: Facilitates the creation of accounts under specific banks.
3. **Perform Transaction**: Enables money transfer between accounts.
4. **Withdraw**: Allows withdrawal of money from an account.
5. **Deposit**: Enables depositing money into an account.
6. **List Transactions**: Displays transactions related to a specific account.
7. **Check Account Balance**: Shows the balance of a specified account.
8. **List Bank Accounts**: Lists all accounts under a specified bank.
9. **Check Bank Fees**: Displays the total transaction fees and total transfer amounts for a bank.

## Prerequisites

- Java Development Kit (JDK) 8 or later
- Maven
- MySQL Database

## Setup Instructions

### Step 1: Clone the Repository

```bash
git clone https://github.com/ArdiZariqi/BankSystem.git
cd BankSystem
```

### Step 2: Configure the Database

Create a MySQL database named `bank_system`.

```sql
CREATE DATABASE bank_system;
```

Update the `application.properties` file located in `src/main/resources` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_system
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```

### Step 3: Run the Liquibase Scripts

Liquibase is used for database versioning. The changesets are located in the `src/main/resources/db.changelog` directory. To run the Liquibase scripts, use the following command:

```bash
mvn liquibase:update
```

This command will create the required tables (`bank`, `account`, `transaction`) and insert sample data.

### Step 4: Build and Run the Application

Use Maven to build and run the application:

```bash
mvn clean install
mvn spring-boot:run
```

### Step 5: Interact with the Console Application

Once the application is running, you can interact with it through the console. You will be presented with a menu to perform various operations:

```
1. Create Bank
2. Create Account
3. Perform Transaction
4. Withdraw
5. Deposit
6. List Transactions
7. Check Account Balance
8. List Bank Accounts
9. Check Bank Fees
10. Exit
```

## Detailed Instructions

### Creating a Bank

1. Select `1` from the menu.
2. Enter the bank name.
3. Enter the fee type (`PERCENTAGE` or `FLATVALUE`).
4. Enter the fee amount.

### Creating an Account

1. Select `2` from the menu.
2. Enter the user name for the account.
3. Choose a bank from the displayed list by entering the bank ID.

### Performing a Transaction

1. Select `3` from the menu.
2. Enter the originating account ID.
3. Enter the resulting account ID.
4. Enter the transaction amount.
5. Enter the reason for the transaction.

### Withdrawing Money

1. Select `4` from the menu.
2. Enter the account ID.
3. Enter the amount to withdraw.

### Depositing Money

1. Select `5` from the menu.
2. Enter the account ID.
3. Enter the amount to deposit.

### Listing Transactions

1. Select `6` from the menu.
2. Enter the account ID.

### Checking Account Balance

1. Select `7` from the menu.
2. Enter the account ID.

### Listing Bank Accounts

1. Select `8` from the menu.
2. Enter the bank ID.

### Checking Bank Fees

1. Select `9` from the menu.
2. Enter the bank ID.

## Database Schema

### Bank Table

| Column                     | Type       |
|----------------------------|------------|
| id                         | INT        |
| name                       | VARCHAR(255)|
| total_transaction_fee_amount | DECIMAL(20,2)|
| total_transfer_amount      | DECIMAL(20,2)|
| transaction_fee_type       | VARCHAR(20)|
| transaction_fee_value      | DECIMAL(10,2)|

### Account Table

| Column    | Type       |
|-----------|------------|
| id        | INT        |
| user_name | VARCHAR(255)|
| balance   | DECIMAL(20,2)|
| bank_id   | INT        |

### Transaction Table

| Column                  | Type        |
|-------------------------|-------------|
| id                      | INT         |
| amount                  | DECIMAL(20,2)|
| originating_account_id  | INT         |
| resulting_account_id    | INT         |
| reason                  | VARCHAR(255)|
| fee                     | DECIMAL(20,2)|

## Example Data

Sample data is inserted through Liquibase changesets for testing purposes.

## Troubleshooting

- Ensure MySQL is running and the credentials in `application.properties` are correct.
- Check the Maven build logs for any issues during the build process.

---

By following these instructions, you should be able to set up and run the banking console application successfully. If you encounter any issues, please refer to the troubleshooting section or reach out for support.

## Build and Run the Application(RESTful)

1. **Build the Application:**
   ```bash
   mvn clean package
   ```

2. **Run the Application:**
   ```bash
   java -jar target/bank-system-1.0.jar
   ```

3. **Access the Application:**
   - Once the application is running, open a web browser or use a tool like Postman to access the RESTful endpoints. The application runs on `http://localhost:8080`.

## Using the Application

The Bank System Application provides the following functionalities:

1. **Create Bank:**
   - Endpoint: `POST /banks`
   - Create a new bank with a specified name, fee type (PERCENTAGE or FLATVALUE), and fee amount.

2. **Create Account:**
   - Endpoint: `POST /accounts`
   - Create a new account with a specified user name and associated bank ID.

3. **Perform Transaction:**
   - Endpoint: `POST /transactions`
   - Perform a transaction between two accounts with a specified amount and reason.

4. **Withdraw:**
   - Endpoint: `PUT /accounts/{accountId}/withdraw`
   - Withdraw a specified amount from the account with the given account ID.

5. **Deposit:**
   - Endpoint: `PUT /accounts/{accountId}/deposit`
   - Deposit a specified amount into the account with the given account ID.

6. **List Transactions:**
   - Endpoint: `GET /accounts/{accountId}/transactions`
   - List all transactions associated with the account ID.

7. **Check Account Balance:**
   - Endpoint: `GET /accounts/{accountId}/balance`
   - Check the balance of the account with the given account ID.

8. **List Bank Accounts:**
   - Endpoint: `GET /banks/{bankId}/accounts`
   - List all accounts associated with the bank ID.

9. **Check Bank Fees:**
   - Endpoint: `GET /banks/{bankId}/fees`
   - Check the total transaction fee amount and total transfer amount of the bank with the given bank ID.

---

By following these instructions, you should be able to set up and run the banking RESTful Api successfully. If you encounter any issues, please refer to the troubleshooting section or reach out for support.

Enjoy using the Bank System Application! If you encounter any issues or have suggestions, feel free to reach out for support.
