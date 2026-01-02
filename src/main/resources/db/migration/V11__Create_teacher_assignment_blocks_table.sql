CREATE TABLE teacher_assignment_blocks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assignment_id BIGINT NOT NULL,
    block_size BIGINT NOT NULL,
    block_order BIGINT NOT NULL,
    CONSTRAINT fk_block_assignment FOREIGN KEY (assignment_id) REFERENCES teacher_assignments(id) ON DELETE CASCADE
);
