package kr.picktime.timetable.school.presentation.controller.dto.request

import kr.picktime.timetable.school.domain.enums.City

data class UpdateSchoolRequest(
    val name: String? = null,
    val city: City? = null,
    val password: String? = null,
    val isActive: Boolean? = null
)
