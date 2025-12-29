package kr.picktime.timetable.period.presentation.dto

import kr.picktime.timetable.period.domain.entity.PeriodEntity
import java.time.DayOfWeek
import java.time.LocalTime

data class PeriodResponse(
    val id: Long,
    val schoolId: Long,
    val period: Long,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val dayOfWeek: DayOfWeek
) {
    companion object {
        fun from(entity: PeriodEntity): PeriodResponse {
            return PeriodResponse(
                id = entity.id!!,
                schoolId = entity.schoolId,
                period = entity.period,
                startTime = entity.startTime,
                endTime = entity.endTime,
                dayOfWeek = entity.dayOfWeek
            )
        }
    }
}