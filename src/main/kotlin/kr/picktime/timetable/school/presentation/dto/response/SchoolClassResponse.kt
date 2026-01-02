package kr.picktime.timetable.school.presentation.dto.response

import kr.picktime.timetable.school.domain.entity.SchoolClassEntity

data class SchoolClassResponse(
    val id: Long,
    val schoolId: Long,
    val grade: Long,
    val classNumber: Long,
) {
    companion object {
        fun from(entity: SchoolClassEntity): SchoolClassResponse {
            return SchoolClassResponse(
                id = entity.id!!,
                schoolId = entity.schoolId,
                grade = entity.grade,
                classNumber = entity.classNumber,
            )
        }
    }
}