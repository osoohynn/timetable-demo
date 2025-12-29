package kr.picktime.timetable.period.presentation.controller

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
    suspend fun createPeriod(@RequestBody request: CreatePeriodRequest): PeriodResponse =
        periodService.createPeriod(request)

    @GetMapping("/school/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getPeriodsBySchoolId(@PathVariable schoolId: Long): PeriodResponse =
        periodService.getPeriodsBySchoolId(schoolId)

    @GetMapping("/school/{schoolId}/day/{dayOfWeek}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getPeriodsBySchoolIdAndDayOfWeek(
        @PathVariable schoolId: Long,
        @PathVariable dayOfWeek: DayOfWeek
    ): DayPeriodResponse =
        periodService.getPeriodsBySchoolIdAndDayOfWeek(schoolId, dayOfWeek)

    @PutMapping("/school/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updatePeriod(
        @PathVariable schoolId: Long,
        @RequestBody request: CreatePeriodRequest
    ): PeriodResponse =
        periodService.updatePeriod(schoolId, request)
}