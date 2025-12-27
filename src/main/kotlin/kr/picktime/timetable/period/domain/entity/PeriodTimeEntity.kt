package kr.picktime.timetable.period.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalTime

@Table("period_times")
data class PeriodTimeEntity(
    @Id
    val id: Long,
    val schoolId: Long,
    val period: Int,
    val startTime: LocalTime,
    val endTime: LocalTime,
)
