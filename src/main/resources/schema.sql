CREATE TABLE customer (
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE account (
    id BIGINT PRIMARY KEY,
    account_number VARCHAR(255),
    balance DECIMAL(19,2),
    customer BIGINT,
    CONSTRAINT fk_customer FOREIGN KEY (customer) REFERENCES customer(id)
);

CREATE TABLE transactions (
    id BIGINT PRIMARY KEY,
    from_account_id BIGINT,
    to_account_id BIGINT,
    amount DECIMAL(19,2) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    description VARCHAR(500),
    type VARCHAR(50),
    CONSTRAINT fk_from_account FOREIGN KEY (from_account_id) REFERENCES account(id),
    CONSTRAINT fk_to_account FOREIGN KEY (to_account_id) REFERENCES account(id)
);
