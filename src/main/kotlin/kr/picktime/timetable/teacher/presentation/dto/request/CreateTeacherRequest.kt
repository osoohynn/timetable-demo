package kr.picktime.timetable.teacher.presentation.dto.request

import kr.picktime.timetable.teacher.domain.enum.TeacherType
import java.time.DayOfWeek

data class CreateTeacherRequest(
    val name: String,
    val type: TeacherType,
    val availableTimes: List<CreateTeacherAvailabilityRequest>? = null
) {
    data class CreateTeacherAvailabilityRequest(
        val dayOfWeek: DayOfWeek,
        val startPeriod: Long,
        val endPeriod: Long,
    )
}
