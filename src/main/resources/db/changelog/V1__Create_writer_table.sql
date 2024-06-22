-- Миграция для создания таблицы writer
CREATE TABLE writer
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName  VARCHAR(255) NOT NULL
);
