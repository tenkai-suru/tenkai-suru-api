CREATE TABLE IF NOT EXISTS dunder_mifflin_employees (
  id SERIAL PRIMARY KEY,

  first_name text,
  last_name  text,
  position   text,
  branch     text,
  start_date text,
  end_date   text
);
