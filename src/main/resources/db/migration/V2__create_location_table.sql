CREATE TABLE locations (
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(255)  NOT NULL,
    author    INT,
    latitude  DECIMAL(6, 3) NOT NULL,
    longitude DECIMAL(6, 3) NOT NULL,
    CONSTRAINT fk_author
        FOREIGN KEY (author)
            REFERENCES users (id),
    CONSTRAINT uq_location UNIQUE (author, latitude, longitude)
);