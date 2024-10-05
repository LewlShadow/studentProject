create database fullstackproject;
use fullstackproject;
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL unique,
    email_address VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);
SELECT * FROM `user` u ;


CREATE TABLE article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(100),
    title VARCHAR(255),
    date DATE,
    content TEXT CHARACTER SET utf8mb4
);
SELECT * FROM article a ;

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    article_id BIGINT NOT NULL,
    username VARCHAR(255) NOT NULL,
    text TEXT CHARACTER SET utf8mb4 NOT NULL,
    date TIMESTAMP NOT NULL,
    level INT NOT NULL,
    parent_comment_id BIGINT
);
SELECT * from comment c ;