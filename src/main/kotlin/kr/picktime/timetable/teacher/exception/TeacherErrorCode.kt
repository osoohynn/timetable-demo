package kr.picktime.timetable.teacher.exception

import kr.picktime.timetable.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class TeacherErrorCode(
    override val status: HttpStatus,
    override val message: String
) : ErrorCode {
    TEACHER_NOT_FOUND(HttpStatus.NOT_FOUND, "Teacher not found"),
    TEACHER_NOT_BELONGS_TO_SCHOOL(HttpStatus.FORBIDDEN, "Teacher does not belong to this school")
}