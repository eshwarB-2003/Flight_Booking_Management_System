CREATE TABLE booking (
    booking_id SERIAL PRIMARY KEY,
    booking_reference VARCHAR(20) UNIQUE NOT NULL,
    user_id INT NOT NULL,
    flight_id INT NOT NULL,
    booking_status VARCHAR(20) NOT NULL DEFAULT 'REQUESTED',
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_booking_status
    CHECK (booking_status IN ('REQUESTED','CONFIRMED','CANCELLED')),

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (flight_id) REFERENCES flight(flight_id)
);

CREATE TABLE seat_reservation (
    reservation_id SERIAL PRIMARY KEY,
    booking_id INT NOT NULL,
    seat_id INT NOT NULL,
    reservation_status VARCHAR(20) NOT NULL DEFAULT 'LOCKED',

    locked_at TIMESTAMP,
    confirmed_at TIMESTAMP,
    released_at TIMESTAMP,

    CONSTRAINT chk_reservation_status
    CHECK (reservation_status IN ('LOCKED','CONFIRMED','RELEASED')),

    FOREIGN KEY (booking_id) REFERENCES booking(booking_id),
    FOREIGN KEY (seat_id) REFERENCES seat(seat_id),

    UNIQUE (booking_id, seat_id)
);

CREATE TABLE ancillary_item (
    ancillary_id SERIAL PRIMARY KEY,
    item_id INT UNIQUE NOT NULL,
    service_name VARCHAR(100) NOT NULL,
    service_description TEXT,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE booking_ancillary (
    id SERIAL PRIMARY KEY,
    booking_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),

    FOREIGN KEY (booking_id) REFERENCES booking(booking_id),
    FOREIGN KEY (item_id) REFERENCES ancillary_item(item_id)
);