package kr.picktime.timetable.subject.presentation.dto.request

data class CreateSubjectRequest(
    val name: String,
    val shortName: String,
    val specialRoomIds: List<Long>,
)
