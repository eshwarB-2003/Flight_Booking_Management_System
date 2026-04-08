CREATE TABLE booking (
                         booking_id SERIAL PRIMARY KEY,
                         booking_reference VARCHAR(50),
                         user_id INT,
                         flight_id INT,
                         booking_status VARCHAR(20),
                         total_amount DECIMAL(10,2),
                         FOREIGN KEY (user_id) REFERENCES users(user_id),
                         FOREIGN KEY (flight_id) REFERENCES flight(flight_id)
);


CREATE TABLE seat_reservation (
                                  reservation_id SERIAL PRIMARY KEY,
                                  booking_id INT,
                                  seat_id INT,
                                  reservation_status VARCHAR(20),
                                  locked_at TIMESTAMP ,
                                  confirmed_at TIMESTAMP,
                                  released_at TIMESTAMP,
                                  FOREIGN KEY (booking_id) REFERENCES booking(booking_id),
                                  FOREIGN KEY (seat_id) REFERENCES seat(seat_id)
);


CREATE TABLE ancillary_item (
    ancillary_id SERIAL PRIMARY KEY,
    booking_id INT,
    service_name VARCHAR,
    item_id INT UNIQUE,
    price decimal(10,2),
    quantity INT,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id));

ALTER TABLE ancillary_item
ADD COLUMN service_description TEXT;

CREATE TABLE booking_ancillary (
    id SERIAL PRIMARY KEY,
    booking_id INT,
    item_id INT,
    quantity INT,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id),
    FOREIGN KEY (item_id) REFERENCES ancillary_item(item_id)
);



SELECT current_database();