CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE
);

CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(255) UNIQUE,
    balance DECIMAL(19,2),
    customer_id BIGINT,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_account_id BIGINT,
    to_account_id BIGINT,
    amount DECIMAL(19,2) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    description VARCHAR(500),
    type VARCHAR(50),
    CONSTRAINT fk_from_account FOREIGN KEY (from_account_id) REFERENCES account(id),
    CONSTRAINT fk_to_account FOREIGN KEY (to_account_id) REFERENCES account(id)
);

CREATE INDEX idx_transactions_from ON transactions(from_account_id);
CREATE INDEX idx_transactions_to ON transactions(to_account_id);
CREATE INDEX idx_transactions_time ON transactions(timestamp);
