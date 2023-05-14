DROP TABLE IF EXISTS OPERATIONS;

CREATE TABLE OPERATIONS
(
    UUID        binary(16) PRIMARY KEY,
    ID          integer(10),
    CUSTOMER_ID integer(10),
    LOAD_AMOUNT bigint(255),
    TIME        datetime,
    ACCEPTED    boolean
);