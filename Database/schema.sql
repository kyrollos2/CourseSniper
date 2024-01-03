CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    section_name VARCHAR(100) NOT NULL,
    title VARCHAR(100) NOT NULL,
    start_date DATE,
    available_seats INT,
    UNIQUE (section_name, title)
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    desired_section_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (desired_section_name) REFERENCES courses(section_name)
);

CREATE TABLE target_courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    target_courses VARCHAR(255 ) NOT NULL,
    FOREIGN KEY (target_courses) REFERENCES users(email, desired_section_name)
)
