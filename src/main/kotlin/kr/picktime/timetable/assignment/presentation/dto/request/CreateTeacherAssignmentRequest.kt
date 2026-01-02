package kr.picktime.timetable.assignment.presentation.dto.request

data class CreateTeacherAssignmentRequest(
    val teacherId: Long,
    val assignments: List<AssignmentRequest>
) {
    data class AssignmentRequest(
        val classId: Long,
        val subjectId: Long,
        val hoursPerWeek: Long,
        val isBlock: Boolean = false,
        val blockSizes: List<Long>? = null, // 블록 크기 리스트 (예: [3, 3] 또는 [2, 4] 또는 [1, 2, 3])
    )
}
