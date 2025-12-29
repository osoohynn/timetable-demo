package kr.picktime.timetable.period.presentation.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.DayOfWeek
import java.time.LocalTime

data class CreatePeriodRequest(
    @field:NotEmpty(message = "교시 정보는 최소 1개 이상이어야 합니다.")
    @field:Valid
    val periods: Set<PeriodTimeRequest>
) {
    @get:AssertTrue(message = "같은 요일의 같은 교시 번호가 중복될 수 없습니다.")
    val isPeriodsUnique: Boolean
        get() {
            val uniqueKeys = periods.map { it.dayOfWeek to it.periodNumber }.toSet()
            return uniqueKeys.size == periods.size
        }

    data class PeriodTimeRequest(
        @field:NotNull(message = "요일은 필수입니다.")
        val dayOfWeek: DayOfWeek,

        @field:Positive(message = "교시 번호는 양수여야 합니다.")
        val periodNumber: Long,

        @field:NotNull(message = "시작 시간은 필수입니다.")
        val startTime: LocalTime,

        @field:NotNull(message = "종료 시간은 필수입니다.")
        val endTime: LocalTime,
    ) {
        @get:AssertTrue(message = "종료 시간은 시작 시간보다 커야 합니다.")
        val isTimeRangeValid: Boolean
            get() = startTime < endTime
    }
}
