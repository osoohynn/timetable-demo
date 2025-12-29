package kr.picktime.timetable.teacher.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.DayOfWeek

@Table("teacher_availabilities")
data class TeacherAvailabilityEntity(
    @Id
    val id: Long? = null,
    val teacherId: Long,
    val dayOfWeek: DayOfWeek,
    val startPeriod: Long,
    val endPeriod: Long,
)