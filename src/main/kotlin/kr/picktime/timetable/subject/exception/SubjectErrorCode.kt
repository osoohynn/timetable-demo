package kr.picktime.timetable.subject.exception

import kr.picktime.timetable.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class SubjectErrorCode(
    override val status: HttpStatus,
    override val message: String
) : ErrorCode {
    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "Subject not found"),
    SUBJECT_NOT_BELONGS_TO_SCHOOL(HttpStatus.FORBIDDEN, "Subject does not belong to this school")
}
