CREATE TABLE IF NOT EXISTS request (
    id uuid,
    timestamp timestamp,
    client text,
    currency_id uuid,
    PRIMARY KEY (id),
    CONSTRAINT fk_currency_id
        FOREIGN KEY (currency_id)
            REFERENCES currency (id)
);
