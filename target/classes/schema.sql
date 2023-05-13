DROP TABLE IF EXISTS operations;

CREATE TABLE operations
(
    uuid        varchar(255) PRIMARY KEY,
    id          integer(10),
    customer_id integer(10),
    load_amount bigint(255),
    time        datetime,
    accepted    boolean
);