DROP TABLE IF EXISTS users;
CREATE TABLE users (
   email PRIMARY KEY,
   password TEXT,
   real_name TEXT,
   is_admin BOOLEAN
);

