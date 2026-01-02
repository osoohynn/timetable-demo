CREATE TABLE teacher_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    day_of_week VARCHAR(20),
    period_number BIGINT,
    description VARCHAR(200) NOT NULL,
    CONSTRAINT fk_request_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE
);