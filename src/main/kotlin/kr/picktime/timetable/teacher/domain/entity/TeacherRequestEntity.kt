package kr.picktime.timetable.teacher.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.DayOfWeek

@Table("teacher_requests")
data class TeacherRequestEntity(
    @Id
    val id: Long? = null,
    val teacherId: Long,
    val dayOfWeek: DayOfWeek? = null,
    val periodNumber: Long? = null,
    val description: String,
)