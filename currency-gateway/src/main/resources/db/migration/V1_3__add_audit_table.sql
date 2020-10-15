CREATE TABLE IF NOT EXISTS request_audit (
    id uuid,
    service_name text,
    request_id uuid,
    time timestamp,
    client text,

    PRIMARY KEY (id),
    CONSTRAINT fk_request_id
       FOREIGN KEY (request_id)
           REFERENCES request (id)
);
