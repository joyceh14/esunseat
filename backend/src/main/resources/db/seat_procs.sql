DROP PROCEDURE IF EXISTS AssignSeat // 
DROP PROCEDURE IF EXISTS ReleaseSeat // 
DROP PROCEDURE IF EXISTS MoveSeat // 

/* Assign Seat */
CREATE PROCEDURE AssignSeat(
  IN p_emp_id VARCHAR(5),
  IN p_floor_no INT,
  IN p_seat_no INT
)
BEGIN
  DECLARE v_seat_seq INT;

  -- Find target seat
  SELECT floor_seat_seq INTO v_seat_seq
  FROM seating_chart
  WHERE floor_no = p_floor_no AND seat_no = p_seat_no;

  IF v_seat_seq IS NULL THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Seat not found';
  END IF;

  -- If the seat is already occupied
  IF EXISTS (SELECT 1 FROM seating_chart WHERE floor_seat_seq = v_seat_seq AND emp_id IS NOT NULL) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Seat already taken';
  END IF;

  -- If the employee already has a seat
  IF EXISTS (SELECT 1 FROM employee WHERE emp_id = p_emp_id AND floor_seat_seq IS NOT NULL) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Employee already has a seat';
  END IF;

  -- Update seat assignment
  UPDATE seating_chart SET emp_id = p_emp_id WHERE floor_seat_seq = v_seat_seq;
  UPDATE employee SET floor_seat_seq = v_seat_seq WHERE emp_id = p_emp_id;
END // 

/* Release Seat */
CREATE PROCEDURE ReleaseSeat(IN p_seq INT)
BEGIN
  -- Clear employee's seat reference
  UPDATE employee e
  JOIN seating_chart s ON e.floor_seat_seq = s.floor_seat_seq
  SET e.floor_seat_seq = NULL
  WHERE s.floor_seat_seq = p_seq;

  -- Mark seat as unassigned
  UPDATE seating_chart SET emp_id = NULL WHERE floor_seat_seq = p_seq;
END // 

/* Move Seat */
CREATE PROCEDURE MoveSeat(
  IN p_emp_id   VARCHAR(5),
  IN p_to_floor INT,
  IN p_to_seat  INT
)
BEGIN
  DECLARE v_old_seq INT;
  DECLARE v_new_seq INT;

  START TRANSACTION;

  -- Read employee's current seat (lock row to avoid concurrency issues)
  SELECT floor_seat_seq INTO v_old_seq
  FROM employee
  WHERE emp_id = p_emp_id
  FOR UPDATE;

  -- Find the new seat (and lock that row)
  SELECT floor_seat_seq INTO v_new_seq
  FROM seating_chart
  WHERE floor_no = p_to_floor AND seat_no = p_to_seat
  FOR UPDATE;

  IF v_new_seq IS NULL THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Target seat not found';
  END IF;

  -- If the target seat is occupied by someone else → reject
  IF EXISTS (
      SELECT 1 FROM seating_chart
      WHERE floor_seat_seq = v_new_seq AND emp_id IS NOT NULL AND emp_id <> p_emp_id
  ) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Target seat already taken';
  END IF;

  -- Release old seat (if any)
  IF v_old_seq IS NOT NULL THEN
    UPDATE seating_chart SET emp_id = NULL WHERE floor_seat_seq = v_old_seq;
  END IF;

  -- Assign new seat
  UPDATE seating_chart SET emp_id = p_emp_id WHERE floor_seat_seq = v_new_seq;
  UPDATE employee SET floor_seat_seq = v_new_seq WHERE emp_id = p_emp_id;

  COMMIT;

  -- Return latest seat status (for backend DTO use)
  SELECT sc.floor_seat_seq, sc.floor_no, sc.seat_no,
         e.emp_id, e.name, e.email
  FROM seating_chart sc
  LEFT JOIN employee e ON sc.emp_id = e.emp_id
  WHERE sc.floor_seat_seq = v_new_seq;
END // 
