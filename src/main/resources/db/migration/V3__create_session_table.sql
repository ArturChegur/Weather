CREATE TABLE sessions (
    id         VARCHAR(36) PRIMARY KEY,
    client     INT       NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_client
        FOREIGN KEY (client)
            REFERENCES users (id)
);