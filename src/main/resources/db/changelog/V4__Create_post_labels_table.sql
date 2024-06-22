-- Миграция для создания таблицы post_labels
CREATE TABLE post_labels
(
    post_id  INT NOT NULL,
    label_id INT NOT NULL,
    CONSTRAINT fk_post_labels_post FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
    CONSTRAINT fk_post_labels_label FOREIGN KEY (label_id) REFERENCES label (id) ON DELETE CASCADE
);
