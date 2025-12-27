package kr.picktime.timetable.global.exception

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val status: HttpStatus,
    override val message: String
) : ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")
}
