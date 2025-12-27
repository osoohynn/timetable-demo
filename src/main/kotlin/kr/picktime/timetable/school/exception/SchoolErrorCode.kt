package kr.picktime.timetable.school.exception

import kr.picktime.timetable.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class SchoolErrorCode(
    override val status: HttpStatus,
    override val message: String
) : ErrorCode {
    SCHOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "School not found"),
    SCHOOL_CODE_ALREADY_EXISTS(HttpStatus.CONFLICT, "School code already exists"),
    SCHOOL_CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "School class not found"),
    SCHOOL_CLASS_ALREADY_EXISTS(HttpStatus.CONFLICT, "School class already exists"),
    SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL(HttpStatus.FORBIDDEN, "School class does not belong to this school")
}
