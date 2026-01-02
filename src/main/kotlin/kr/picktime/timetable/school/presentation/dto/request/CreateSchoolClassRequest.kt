package kr.picktime.timetable.school.presentation.dto.request

data class CreateSchoolClassRequest(
    val classes: List<SchoolClassRequest>
) {
    data class SchoolClassRequest(
        val grade: Long,
        val classCount: Long,
    )
}
