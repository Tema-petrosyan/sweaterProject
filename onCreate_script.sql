CREATE DATABASE sweater;
\c sweater
CREATE TABLE message
(
	id SERIAL PRIMARY KEY NOT NULL,
	tags VARCHAR(255) NOT NULL,
	text VARCHAR(255) NOT NULL
);