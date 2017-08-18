SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS events (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  description VARCHAR,
  date DATE,
);

CREATE TABLE IF NOT EXISTS attendees (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  eventId int,
);