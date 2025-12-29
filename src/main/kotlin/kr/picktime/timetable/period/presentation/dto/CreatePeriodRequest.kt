package kr.picktime.timetable.period.presentation.dto

import java.time.DayOfWeek
import java.time.LocalTime

data class CreatePeriodRequest(
    val schoolId: Long,
    val periods: Set<Period>
)

data class Period(
    val dayOfWeek: DayOfWeek,
    val periodNumber: Long,
    val startTime: LocalTime,
    val endTime: LocalTime,
)
