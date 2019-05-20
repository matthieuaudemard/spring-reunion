DROP TABLE IF EXISTS USER;

CREATE TABLE IF NOT EXISTS USER
(
    id        BIGINT IDENTITY PRIMARY KEY,
    login     VARCHAR(50),
    firstname VARCHAR(50) NOT NULL,
    lastname  VARCHAR(50) NOT NULL,
    password  VARCHAR(60) NOT NULL,
);

CREATE TABLE IF NOT EXISTS MESSAGE
(
    id               BIGINT IDENTITY PRIMARY KEY AUTO_INCREMENT,
    publication_date DATETIME NOT NULL,
    sender_id        BIGINT   NOT NULL,
    title            VARCHAR(100),
    body             VARCHAR(1000),
    FOREIGN KEY (sender_id) REFERENCES USER (id),
);