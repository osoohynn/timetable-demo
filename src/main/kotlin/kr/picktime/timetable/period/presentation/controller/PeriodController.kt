package kr.picktime.timetable.period.presentation.controller

import jakarta.validation.Valid
import kr.picktime.timetable.period.application.service.PeriodService
import kr.picktime.timetable.period.presentation.dto.CreatePeriodRequest
import kr.picktime.timetable.period.presentation.dto.PeriodResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/schools/{schoolId}/periods")
class PeriodController(
    private val periodService: PeriodService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createPeriod(
        @Valid
        @PathVariable schoolId: Long,
        @RequestBody request: CreatePeriodRequest
    ): List<PeriodResponse> =
        periodService.createOrUpdatePeriod(schoolId, request)
}