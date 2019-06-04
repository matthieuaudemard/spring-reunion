DROP TABLE IF EXISTS USER;

CREATE TABLE IF NOT EXISTS USER
(
    id        BIGINT IDENTITY PRIMARY KEY,
    login     VARCHAR(50) NOT NULL UNIQUE,
    firstname VARCHAR(50) NOT NULL,
    lastname  VARCHAR(50) NOT NULL,
    password  VARCHAR(60) NOT NULL,
    about     varchar(250)
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

CREATE TABLE IF NOT EXISTS COMMENT
(
    id           BIGINT IDENTITY PRIMARY KEY,
    comment_date DATETIME NOT NULL,
    sender_id    BIGINT   NOT NULL,
    message_id   BIGINT   NOT NULL,
    body         VARCHAR(1000),
    FOREIGN KEY (message_id) REFERENCES MESSAGE (id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES USER (id) ON DELETE SET NULL,
);