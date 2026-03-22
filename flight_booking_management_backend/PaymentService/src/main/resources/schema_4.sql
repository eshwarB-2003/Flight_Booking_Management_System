CREATE TABLE payment (
    payment_id SERIAL PRIMARY KEY,
    booking_id INT,
    transaction_id VARCHAR(20),
    amount DECIMAL(10,2),
    payment_method VARCHAR(10),
    payment_status VARCHAR(10),
    processed_at TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);