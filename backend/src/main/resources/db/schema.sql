SET FOREIGN_KEY_CHECKS = 0 // 
DROP TABLE IF EXISTS seating_chart // 
DROP TABLE IF EXISTS employee // 
SET FOREIGN_KEY_CHECKS = 1 //

CREATE TABLE employee (
  emp_id VARCHAR(5) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  floor_seat_seq INT UNIQUE
) // 

CREATE TABLE seating_chart (
  floor_seat_seq INT AUTO_INCREMENT PRIMARY KEY,
  floor_no INT NOT NULL,
  seat_no INT NOT NULL,
  emp_id VARCHAR(5) UNIQUE,
  UNIQUE KEY uq_floor_seat (floor_no, seat_no)
) // 

-- After two tables have been created, add the FK
ALTER TABLE employee
  ADD CONSTRAINT fk_employee_seat
  FOREIGN KEY (floor_seat_seq)
  REFERENCES seating_chart(floor_seat_seq)
  ON UPDATE RESTRICT
  ON DELETE SET NULL //

ALTER TABLE seating_chart
  ADD CONSTRAINT fk_seat_emp
  FOREIGN KEY (emp_id)
  REFERENCES employee(emp_id)
  ON UPDATE RESTRICT
  ON DELETE SET NULL //
