
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    term VARCHAR(100) NOT NULL,
    section_name VARCHAR(100) NOT NULL,
    title TEXT NOT NULL,
    start_date DATE NOT NULL,
    faculty VARCHAR(150),
    available_seats INT NOT NULL,
    INDEX idx_courses_available_seats (available_seats),
    INDEX idx_courses_term_section_name (term, section_name),
    UNIQUE KEY unique_term_section (term, section_name)
);


CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR (255) NOT NULL
);


CREATE TABLE target_courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    INDEX idx_target_courses_student_id (student_id),
    INDEX idx_target_courses_course_id (course_id),
    UNIQUE KEY unique_student_course (student_id, course_id)
);


CREATE TABLE alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    student_id INT NOT NULL,
    status ENUM('created', 'sent') NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sent TIMESTAMP NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    INDEX idx_alerts_status (status),  
    INDEX idx_alerts_course_id (course_id),
    INDEX idx_alerts_student_id (student_id)
);
