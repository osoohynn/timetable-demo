package kr.picktime.timetable.teacher.presentation.dto.response

import kr.picktime.timetable.teacher.domain.entity.TeacherEntity
import kr.picktime.timetable.teacher.domain.enum.TeacherType

data class TeacherResponse(
    val id: Long,
    val schoolId: Long,
    val name: String,
    val type: TeacherType
) {
    companion object {
        fun from(entity: TeacherEntity): TeacherResponse {
            return TeacherResponse(
                id = entity.id!!,
                schoolId = entity.schoolId,
                name = entity.name,
                type = entity.type
            )
        }
    }
}
