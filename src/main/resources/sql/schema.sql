CREATE TABLE IF NOT EXISTS parking_slots (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users(
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    amount INT NOT NULL,
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING'
);

CREATE TABLE IF NOT EXISTS booking_slots (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    parking_slot_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (parking_slot_id) REFERENCES parking_slots(id),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    transaction_id VARCHAR(255),
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (transaction_id) REFERENCES transactions(id)
);



