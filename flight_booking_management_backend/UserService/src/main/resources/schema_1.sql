CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    full_name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255),
    phone_number VARCHAR(20),
    role VARCHAR(20),
    account_status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE audit_log (
    log_id SERIAL PRIMARY KEY,
    user_id INT,
    action VARCHAR(100),
    entity_type VARCHAR(50),
    entity_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

ALTER TABLE users
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

SELECT * FROM users;

INSERT INTO users (full_name, email, password, phone_number, role, account_status)
VALUES ('Vaitheeshwar', '25009907@studentmail.ul.ie', '123456', '0894666008', 'PASSENGER', 'ACTIVE');

SELECT * FROM users;
SELECT current_database();