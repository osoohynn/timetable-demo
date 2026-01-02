package kr.picktime.timetable.assignment.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("teacher_assignment_blocks")
data class TeacherAssignmentBlockEntity(
    @Id
    val id: Long? = null,
    val assignmentId: Long,
    val blockSize: Long,
    val blockOrder: Long,
)
