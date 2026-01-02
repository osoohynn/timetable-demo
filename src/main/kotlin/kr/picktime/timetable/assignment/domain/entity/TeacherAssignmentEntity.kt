package kr.picktime.timetable.assignment.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("teacher_assignments")
data class TeacherAssignmentEntity(
    @Id
    val id: Long? = null,
    val teacherId: Long,
    val classId: Long,
    val subjectId: Long,
    val hoursPerWeek: Long,
    val isBlock: Boolean = false,
    val blockSize: Long? = null,
    val blockCount: Long? = null,
)
