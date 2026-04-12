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



ALTER TABLE users
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

SELECT * FROM users;

INSERT INTO users (full_name, email, password, phone_number, role, account_status)
VALUES ('Vaitheeshwar', '25009907@studentmail.ul.ie', '123456', '0894666008', 'PASSENGER', 'ACTIVE');

SELECT * FROM users;
SELECT current_database();
SELECT user_id, full_name, email, password
FROM users
WHERE email = 'ziauddin@example.com';