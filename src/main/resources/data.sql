INSERT INTO customer (first_name, last_name, email)
VALUES ('Ashish', 'Sinha', 'ashish099003@gmail.com');

INSERT INTO customer (first_name, last_name, email)
VALUES ('vedika', 'gupta', 'vedika@gmail.com');

--INSERT INTO account (account_number, balance, customer)
--VALUES ('12345', 1000.00, 1);
--
--INSERT INTO account (account_number, balance, customer)
--VALUES ('45678', 2000.00, 2);

-- Insert transactions
--INSERT INTO transactions (from_account_id, to_account_id, amount, timestamp, description, type) VALUES
--(1, 2, 250.00, CURRENT_TIMESTAMP, 'Payment for groceries', 'DEBIT'),
--(2, 2, 1, 150.00, CURRENT_TIMESTAMP, 'Refund from Vedika', 'CREDIT');