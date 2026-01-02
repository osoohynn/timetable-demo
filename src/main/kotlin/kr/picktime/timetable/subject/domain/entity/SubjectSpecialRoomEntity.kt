package kr.picktime.timetable.subject.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("subject_special_rooms")
data class SubjectSpecialRoomEntity(
    @Id
    val id: Long? = null,
    val subjectId: Long,
    val specialRoomId: Long,
)
