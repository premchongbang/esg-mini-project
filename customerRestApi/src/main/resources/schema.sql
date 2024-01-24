
CREATE TABLE customer_detail
(
    id              int auto_increment PRIMARY KEY,
    customer_ref    varchar(200) NOT NULL,
    customer_name   varchar(200),
    address_line1  varchar(200),
    address_line2 varchar(200),
    town            varchar(200),
    county          varchar(200),
    country         varchar(100),
    postcode        varchar(50)
);