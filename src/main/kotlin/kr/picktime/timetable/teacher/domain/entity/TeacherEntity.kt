package kr.picktime.timetable.teacher.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("teachers")
data class TeacherEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val isActive: Boolean,
    val schoolId: Long,
)
