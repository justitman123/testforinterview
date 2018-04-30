DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts
(
  id SERIAL PRIMARY KEY,
  name character varying(255) NOT NULL
);