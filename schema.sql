CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    section_name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    available_seats INT,
    UNIQUE (section_name, title)
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    desired_section_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (desired_section_name) REFERENCES courses(section_name)
);
