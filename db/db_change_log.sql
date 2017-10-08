---- CREATE USERS TABLE
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id          SERIAL   NOT NULL,
  first_name  TEXT NOT NULL,
  last_name   TEXT NOT NULL,
  email       TEXT UNIQUE NOT NULL,
  password    TEXT NOT NULL,
  avatar_url  TEXT,
  PRIMARY KEY (id)
);

-- create index on user's email
CREATE INDEX user_email_idx ON users (email);