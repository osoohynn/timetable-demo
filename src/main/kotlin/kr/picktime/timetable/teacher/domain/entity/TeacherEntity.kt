package kr.picktime.timetable.teacher.domain.entity

import kr.picktime.timetable.teacher.domain.enum.TeacherType
import org.springframework.data.relational.core.mapping.Table

@Table("teachers")
data class TeacherEntity(
    val id: Long? = null,
    val schoolId: Long,
    val name: String,
    val type: TeacherType = TeacherType.REGULAR,
)
