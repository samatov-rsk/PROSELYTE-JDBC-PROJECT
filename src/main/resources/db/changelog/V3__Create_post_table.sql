CREATE TABLE post
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    content   TEXT,
    created   TIMESTAMP,
    updated   TIMESTAMP,
    status    VARCHAR(255),
    writer_id INT,
    FOREIGN KEY (writer_id) REFERENCES writer (id)
);