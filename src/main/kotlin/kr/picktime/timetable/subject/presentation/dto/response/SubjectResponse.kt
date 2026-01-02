package kr.picktime.timetable.subject.presentation.dto.response

import kr.picktime.timetable.subject.domain.entity.SubjectEntity

data class SubjectResponse(
    val id: Long,
    val schoolId: Long,
    val name: String,
    val shortName: String,
) {
    companion object {
        fun from(entity: SubjectEntity): SubjectResponse {
            return SubjectResponse(
                id = entity.id!!,
                schoolId = entity.schoolId,
                name = entity.name,
                shortName = entity.shortName
            )
        }
    }
}