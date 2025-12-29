CREATE TABLE teacher_availabilities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_period BIGINT NOT NULL,
    end_period BIGINT NOT NULL,
    CONSTRAINT fk_availability_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
    CONSTRAINT uk_teacher_day UNIQUE (teacher_id, day_of_week)
);