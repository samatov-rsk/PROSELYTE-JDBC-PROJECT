-- Миграция для создания таблицы label
CREATE TABLE label
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
