CREATE TABLE school_classes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id BIGINT NOT NULL,
    grade BIGINT NOT NULL,
    class_number BIGINT NOT NULL,
    CONSTRAINT fk_class_school FOREIGN KEY (school_id) REFERENCES schools(id) ON DELETE CASCADE,
    CONSTRAINT uk_school_grade_class UNIQUE (school_id, grade, class_number)
);