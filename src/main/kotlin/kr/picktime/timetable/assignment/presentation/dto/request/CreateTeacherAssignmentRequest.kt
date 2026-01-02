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
        val blockSize: Long? = null,
        val blockCount: Long? = null,
    )
}
