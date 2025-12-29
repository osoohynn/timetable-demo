package kr.picktime.timetable.school.presentation.dto.response

import kr.picktime.timetable.school.domain.entity.SchoolEntity
import kr.picktime.timetable.school.domain.enums.City

data class SchoolResponse(
    val id: Long,
    val name: String,
    val isActive: Boolean,
    val city: City,
    val schoolCode: String,
    val weeklyClassHours: Long
) {
    companion object {
        fun from(entity: SchoolEntity): SchoolResponse {
            return SchoolResponse(
                id = entity.id!!,
                name = entity.name,
                isActive = entity.isActive,
                city = entity.city,
                schoolCode = entity.schoolCode,
                weeklyClassHours = entity.weeklyClassHours
            )
        }
    }
}
