package kr.picktime.timetable.period.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.period.application.service.PeriodService
import kr.picktime.timetable.period.presentation.dto.CreatePeriodRequest
import kr.picktime.timetable.period.presentation.dto.DayPeriodResponse
import kr.picktime.timetable.period.presentation.dto.PeriodResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek

@RestController
@RequestMapping("/api/periods")
class PeriodController(
    private val periodService: PeriodService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createPeriod(@Valid @RequestBody request: CreatePeriodRequest): List<PeriodResponse> =
        periodService.createPeriod(request)
}