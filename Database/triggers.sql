DELIMITER $$

CREATE TRIGGER alertAvailableSeats
AFTER UPDATE ON courses
FOR EACH ROW
BEGIN
    IF NEW.available_seats > 0 AND OLD.available_seats <= 0 THEN
        INSERT INTO alerts(course_id, student_id)
        SELECT NEW.id, tc.student_id
        FROM target_courses tc
        WHERE tc.course_id = NEW.id;
    END IF;
END$$

DELIMITER ;