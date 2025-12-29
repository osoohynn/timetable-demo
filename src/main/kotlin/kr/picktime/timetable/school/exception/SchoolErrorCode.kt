package kr.picktime.timetable.school.exception

import kr.picktime.timetable.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class SchoolErrorCode(
    override val status: HttpStatus,
    override val message: String
) : ErrorCode {
    SCHOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 학교입니다."),
    SCHOOL_CODE_ALREADY_EXISTS(HttpStatus.CONFLICT, "존재하는 학교 코드입니다."),
}
