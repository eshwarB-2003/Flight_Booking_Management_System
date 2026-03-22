CREATE TABLE aircraft (
    aircraft_id SERIAL PRIMARY KEY,
    model VARCHAR(50),
    manufacturer VARCHAR(50),
    total_capacity INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE aircraft_class (
    class_id SERIAL PRIMARY KEY,
    aircraft_id VARCHAR,
    class_name VARCHAR(50),
    seat_count INT,
    base_price DECIMAL(10,2),
    status VARCHAR,
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id)
);

ALTER TABLE aircraft
ALTER COLUMN aircraft_id TYPE VARCHAR;


SELECT * FROM  aircraft;
Select * FROM aircraft_class;

INSERT INTO aircraft (aircraft_id, model, manufacturer, total_capacity)
VALUES
('ASD001', 'A320', 'Airbus', 180),
('ASD002', 'B737', 'Boeing', 160);

SELECT * from aircraft;

INSERT INTO aircraft_class (aircraft_id, class_name, seat_count, base_price, status)
VALUES
('ASD001', 'Economy', 120, 50000.00, 'ACTIVE'),
('ASD002', 'Business', 40, 80000.00, 'ACTIVE');

SELECT * from aircraft_class;

CREATE TABLE seat_map (
    seat_map_id SERIAL PRIMARY KEY,
    aircraft_id VARCHAR,
    rows INT,
    cols INT,
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id)
);

Select * from seat_map;

CREATE TABLE seat (
    seat_id SERIAL PRIMARY KEY,
    seat_number VARCHAR(10),
    seat_type VARCHAR(20),
    seat_status VARCHAR(20),
    seat_map_id INT,
    class_id INT,
    FOREIGN KEY (seat_map_id) REFERENCES seat_map(seat_map_id),
    FOREIGN KEY (class_id) REFERENCES aircraft_class(class_id)
);

CREATE TABLE flight (
    flight_id SERIAL PRIMARY KEY,
    flight_number VARCHAR(20) UNIQUE,
    airline VARCHAR(50),
    aircraft_id VARCHAR(20),
    origin_city VARCHAR(100),
    destination_city VARCHAR(100),
    departure_time TIMESTAMP,
    arrival_time TIMESTAMP,
    status VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id)
);

CREATE TABLE flight_schedule (
    schedule_id SERIAL PRIMARY KEY,
    flight_id INT,
    departure_date DATE,
    arrival_date DATE,
    departure_time TIMESTAMP,
    arrival_time TIMESTAMP,
    available_seats INT,
    status VARCHAR(20),

    FOREIGN KEY (flight_id) REFERENCES flight(flight_id)
);
SELECT current_database();
