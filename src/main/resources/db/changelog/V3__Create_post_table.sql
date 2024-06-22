-- Миграция для создания таблицы post
CREATE TABLE post
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    content   TEXT,
    created   TIMESTAMP    NOT NULL,
    updated   TIMESTAMP    NOT NULL,
    status    VARCHAR(255) NOT NULL
);


