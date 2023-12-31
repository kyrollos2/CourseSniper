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
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    desired_section_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (desired_section_name) REFERENCES courses(section_name)
);
