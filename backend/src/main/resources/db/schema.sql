DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS seating_chart;

CREATE TABLE seating_chart (
  floor_seat_seq INT AUTO_INCREMENT PRIMARY KEY,
  floor_no INT NOT NULL,
  seat_no INT NOT NULL,
  emp_id VARCHAR(5) UNIQUE,
  CONSTRAINT fk_emp FOREIGN KEY (emp_id) REFERENCES employee(emp_id)
);

CREATE TABLE employee (
  emp_id VARCHAR(5) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  floor_seat_seq INT UNIQUE,
  CONSTRAINT fk_seat FOREIGN KEY (floor_seat_seq) REFERENCES seating_chart(floor_seat_seq)
);
