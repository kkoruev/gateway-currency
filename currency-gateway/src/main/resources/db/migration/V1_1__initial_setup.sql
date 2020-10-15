CREATE TABLE IF NOT EXISTS currency (
    id uuid,
    code text,
    PRIMARY KEY (id),
    UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS exchange_rate (
    id uuid,
    timestamp timestamp,
    base_currency_id uuid,
    currency_id uuid,
    rate numeric,
    PRIMARY KEY (id),
    CONSTRAINT fk_base_currency_id
        FOREIGN KEY (base_currency_id)
            REFERENCES currency (id),
    CONSTRAINT fk_currency_id
        FOREIGN KEY (currency_id)
            REFERENCES currency (id)
);
