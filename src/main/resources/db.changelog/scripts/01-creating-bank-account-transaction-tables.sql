-- liquibase formatted sql

-- changeset Ardi_Zariqi:01
CREATE TABLE IF NOT EXISTS bank (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    total_transaction_fee_amount DECIMAL(20, 2) DEFAULT 0.00,
    total_transfer_amount DECIMAL(20, 2) DEFAULT 0.00,
    transaction_flat_fee_amount DECIMAL(10, 2) DEFAULT 0.00,
    transaction_percent_fee_value DECIMAL(5, 2) DEFAULT 0.00
);

-- changeset Ardi_Zariqi:02
CREATE TABLE IF NOT EXISTS account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    balance DECIMAL(20, 2) DEFAULT 0.00,
    bank_id INT,
    FOREIGN KEY (bank_id) REFERENCES bank(id)
);

-- changeset Ardi_Zariqi:03
CREATE TABLE IF NOT EXISTS transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(20, 2) NOT NULL,
    originating_account_id INT,
    resulting_account_id INT,
    reason VARCHAR(255),
    fee DECIMAL(20, 2) DEFAULT 0.00,
    FOREIGN KEY (originating_account_id) REFERENCES account(id),
    FOREIGN KEY (resulting_account_id) REFERENCES account(id)
);

-- changeset Ardi_Zariqi:04
ALTER TABLE bank
DROP COLUMN transaction_flat_fee_amount,
DROP COLUMN transaction_percent_fee_value,
ADD COLUMN transaction_fee_type BIT DEFAULT 0,
ADD COLUMN transaction_fee_amount DECIMAL(10, 2);

-- changeset Ardi_Zariqi:05
ALTER TABLE bank
CHANGE transaction_fee_amount transaction_fee_value DECIMAL(10, 2),
MODIFY COLUMN transaction_fee_type varchar(20);