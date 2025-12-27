package kr.picktime.timetable.period.exception

import kr.picktime.timetable.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class PeriodErrorCode(
    override val status: HttpStatus,
    override val message: String
) : ErrorCode {
    // PeriodSetting
    PERIOD_SETTING_NOT_FOUND(HttpStatus.NOT_FOUND, "Period setting not found"),
    PERIOD_SETTING_ALREADY_EXISTS(HttpStatus.CONFLICT, "Period setting already exists"),
    INVALID_MAX_PERIOD(HttpStatus.BAD_REQUEST, "Max period must be greater than 0"),

    // PeriodTime
    PERIOD_TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "Period time not found"),
    PERIOD_TIME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Period time already exists"),
    INVALID_PERIOD(HttpStatus.BAD_REQUEST, "Period must be greater than 0"),
    INVALID_TIME_RANGE(HttpStatus.BAD_REQUEST, "Start time must be before end time")
}
