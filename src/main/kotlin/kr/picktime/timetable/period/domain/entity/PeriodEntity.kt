package kr.picktime.timetable.period.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.DayOfWeek
import java.time.LocalTime

@Table("periods")
data class PeriodEntity(
    @Id
    val id: Long? = null,
    val schoolId: Long,
    val period: Int,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val dayOfWeek: DayOfWeek,
)
