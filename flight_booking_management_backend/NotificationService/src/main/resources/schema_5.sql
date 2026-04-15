CREATE TABLE notification (
    notification_id SERIAL PRIMARY KEY,
    booking_id INT,
    user_id INT,
    recipient VARCHAR(100),
    notifcation_type VARCHAR(20),
    status VARCHAR(20),
    created_at TIMESTAMP,
    sent_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);