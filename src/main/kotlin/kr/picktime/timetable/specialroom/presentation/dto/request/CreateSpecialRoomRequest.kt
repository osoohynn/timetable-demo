package kr.picktime.timetable.specialroom.presentation.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateSpecialRoomRequest(
    @field:NotBlank(message = "특별실 이름은 필수입니다.")
    val name: String,
)
