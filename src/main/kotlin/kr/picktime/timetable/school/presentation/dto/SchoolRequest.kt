package kr.picktime.timetable.school.presentation.dto

import kr.picktime.timetable.school.domain.enums.City

data class CreateSchoolRequest(
    val name: String,
    val city: City,
    val schoolCode: String,
    val password: String,
    val isActive: Boolean?,
    val weeklyClassHours: Long,
)