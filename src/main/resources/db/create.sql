SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS events (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  description VARCHAR,
  date DATE,
);