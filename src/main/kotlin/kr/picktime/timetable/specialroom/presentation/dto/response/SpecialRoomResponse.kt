package kr.picktime.timetable.specialroom.presentation.dto.response

import kr.picktime.timetable.specialroom.domain.entity.SpecialRoomEntity

data class SpecialRoomResponse(
    val id: Long,
    val schoolId: Long,
    val name: String,
) {
    companion object {
        fun from(entity: SpecialRoomEntity): SpecialRoomResponse {
            return SpecialRoomResponse(
                id = entity.id!!,
                schoolId = entity.schoolId,
                name = entity.name,
            )
        }
    }
}
