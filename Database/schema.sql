CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    section_name VARCHAR(100) NOT NULL,
    title TEXT NOT NULL,
    start_date DATE,
    faculty VARCHAR(100) NOT NULL,
    available_seats INT,
    INDEX idx_courses_available_seats (available_seats),
    INDEX idx_courses_section_name (section_name),
    UNIQUE KEY unique_section_date (section_name, start_date) 
);


CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    desired_section_name VARCHAR(100),
    FOREIGN KEY (desired_section_name) REFERENCES courses(section_name)
);

CREATE TABLE target_courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    INDEX idx_target_courses_student_id (student_id),
    INDEX idx_target_courses_course_id (course_id)
);
