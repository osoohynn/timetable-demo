package kr.picktime.timetable.teacher.presentation.dto.request

import java.time.DayOfWeek

data class CreateTeacherRequestRequest(
    val requests: List<RequestItem>
) {
    data class RequestItem(
        val dayOfWeek: DayOfWeek? = null,
        val periodNumber: Long? = null,
        val description: String,
    )
}
