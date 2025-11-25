-- Refresh token table :
CREATE TABLE refresh_token(
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(100) NOT NULL UNIQUE,
    expiry_date DATE NOT NULL,
    user_id BIGINT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id)
);