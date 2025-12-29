package kr.picktime.timetable.school.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import kr.picktime.timetable.school.domain.enums.City

data class CreateSchoolRequest(
    @field:NotBlank(message = "학교 이름은 필수입니다.")
    val name: String,

    @field:NotNull(message = "도시는 필수입니다.")
    val city: City,

    @field:NotBlank(message = "학교 코드는 필수입니다.")
    val schoolCode: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    val password: String,

    val isActive: Boolean?,

    @field:Positive(message = "주간 수업 시수는 양수여야 합니다.")
    val weeklyClassHours: Long,
)