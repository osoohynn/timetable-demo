CREATE TABLE subject_special_rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id BIGINT NOT NULL,
    special_room_id BIGINT NOT NULL,
    CONSTRAINT fk_subject_special_room_subject FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    CONSTRAINT fk_subject_special_room_room FOREIGN KEY (special_room_id) REFERENCES special_rooms(id) ON DELETE CASCADE,
    CONSTRAINT uk_subject_room UNIQUE (subject_id, special_room_id)
);
