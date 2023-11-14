CREATE TABLE
    professional (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        role VARCHAR(20) NOT NULL,
        birth_date DATE NOT NULL,
        created_date DATE NOT NULL
    );

CREATE TABLE
    contact (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        contact VARCHAR(255) NOT NULL,
        created_date DATE NOT NULL,
        professional_id BIGINT REFERENCES professional (id) NOT NULL
    );